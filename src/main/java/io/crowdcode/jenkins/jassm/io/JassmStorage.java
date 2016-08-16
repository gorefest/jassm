package io.crowdcode.jenkins.jassm.io;

import io.crowdcode.jenkins.jassm.data.JassmContainer;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.crowdcode.jenkins.jassm.io.LockService.lock;
import static io.crowdcode.jenkins.jassm.io.LockService.unlock;

/**
 * Created by marcus on 26.07.2016.
 */
public class JassmStorage {

    public static final class DataStoreOpeningFailedException extends RuntimeException {
        public DataStoreOpeningFailedException(Throwable cause) {
            super(cause);
        }
    }

    public static synchronized void updateRow(JassmDataRow dataRow, File dataStore) {

        boolean unlock = false;

        try {
            lock(dataStore);
            unlock = true;

            JassmContainer container;
            if (dataStore.exists() && dataStore.length() > 0) {
                container = loadDataStore(dataStore);
                if (container.getJassmDataRows() != null && container.getJassmDataRows().length > 0) {
                    updateContainer(dataRow, container);
                } else {
                    fillBlankContainer(dataRow, container);
                }
            } else {
                container = new JassmContainer();
                fillBlankContainer(dataRow, container);
            }

            writeContainer(dataStore, container);
        } finally {
            if (unlock) {
                unlock(dataStore);
            }
        }

    }


    static void writeContainer(File dataStore, JassmContainer container) {
        try (FileOutputStream fos = new FileOutputStream(dataStore)) {
            JAXB.marshal(container, fos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void fillBlankContainer(JassmDataRow dataRow, JassmContainer container) {
        JassmDataRow[] rows = new JassmDataRow[] { dataRow };
        container.setJassmDataRows(rows);
    }

    static void updateContainer(JassmDataRow dataRow, JassmContainer container) {
        List<JassmDataRow> rows = Arrays.asList(container.getJassmDataRows());
        List<JassmDataRow> newRows = new ArrayList<>();
        for (JassmDataRow row : rows) {
            if (!row.getId().equals(dataRow.getId())) {
                newRows.add(row);
            }
        }
        newRows.add(dataRow);
        container.setJassmDataRows(newRows.toArray(new JassmDataRow[newRows.size()]));
    }

    public static JassmContainer loadDataStore(File dataStore) {
        try (FileInputStream fis = new FileInputStream(dataStore)) {
            JassmContainer container = JAXB.unmarshal(fis, JassmContainer.class);
            return container;
        } catch (IOException e) {
            throw new DataStoreOpeningFailedException(e);
        }
    }
}
