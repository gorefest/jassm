package io.crowdcode.jenkins.jassm.io;

import io.crowdcode.jenkins.jassm.data.JassmContainer;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;
import io.crowdcode.jenkins.jassm.harness.DataHarness;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.crowdcode.jenkins.jassm.harness.DataHarness.c$testContainer1;
import static io.crowdcode.jenkins.jassm.harness.DataHarness.c$testContainer2;
import static org.junit.Assert.*;

/**
 * Created by marcus on 31.07.2016.
 */
public class JassmStorageTest {

    JassmContainer container;
    File tmpFile;

    @Before
    public void setup() throws Exception{
        container = new JassmContainer();
        tmpFile = File.createTempFile("datastore",".xml");
        tmpFile.deleteOnExit();
    }

    /**
     * This testcase test the full update of a null container with an initial row
     * @throws Exception
     */
    @Test
    public void updateRow1() throws Exception {
        JassmDataRow row = DataHarness.c$testRow1();
        JassmStorage.updateRow(row, tmpFile);
        container = JassmStorage.loadDataStore(tmpFile);

        assertNotNull(container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(1, container.getJassmDataRows().length);

    }

    /**
     * This testcase test the full update of an empty container with an initial row
     * @throws Exception
     */
    @Test
    public void updateRow2() throws Exception {
        JassmStorage.writeContainer(tmpFile, new JassmContainer());
        container = JassmStorage.loadDataStore(tmpFile);
        assertNull(container.getJassmDataRows());

        JassmDataRow row = DataHarness.c$testRow1();
        JassmStorage.updateRow(row, tmpFile);
        container = JassmStorage.loadDataStore(tmpFile);

        assertNotNull(container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(1, container.getJassmDataRows().length);

    }


    /**
     * This testcase test the full update of an prefilled container with an new row
     * @throws Exception
     */
    @Test
    public void updateRow3() throws Exception {
        container = DataHarness.c$testContainer1();  // Container with 2 rows
        JassmStorage.writeContainer(tmpFile, container);
        assertEquals(1, container.getJassmDataRows().length);

        JassmDataRow row = DataHarness.c$testRow2();
        JassmStorage.updateRow(row, tmpFile);       // will add one row

        container = JassmStorage.loadDataStore(tmpFile);
        assertNotNull(container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(2, container.getJassmDataRows().length);
        assertEquals(DataHarness.VALUE_14, container.getJassmDataRows()[1].getColumnValue4());

        row = DataHarness.c$testRow2();
        row.setColumnValue4(DataHarness.VALUE_4);
        JassmStorage.updateRow(row, tmpFile);       // will update previously added row

        container = JassmStorage.loadDataStore(tmpFile);
        assertNotNull(container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(2, container.getJassmDataRows().length);
        assertEquals(DataHarness.VALUE_4, container.getJassmDataRows()[1].getColumnValue4());

    }


    /**
     * Tests the saving and loading of the container file
     *
     * @throws Exception
     */
    @Test
    public void writeLoadContainer() throws Exception {
        // Save Container 1
        container = c$testContainer1();
        JassmStorage.writeContainer(tmpFile, container);
        assertTrue(tmpFile.exists());

        // Load Container 1
        container = JassmStorage.loadDataStore(tmpFile);
        assertNotNull(container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(1, container.getJassmDataRows().length);

        // Save Container 2
        container = c$testContainer2();
        JassmStorage.writeContainer(tmpFile, container);
        assertTrue(tmpFile.exists());

        // Load Container 2
        container = JassmStorage.loadDataStore(tmpFile);
        assertNotNull(container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(2, container.getJassmDataRows().length);

        assertEquals(container.getJassmDataRows()[0], DataHarness.c$testRow1());
        assertEquals(container.getJassmDataRows()[1], DataHarness.c$testRow2());

    }

    /**
     * Verifies that a null or blank container is filled with data
     *
     * @throws Exception
     */
    @Test
    public void fillBlankContainer() throws Exception {
        JassmStorage.fillBlankContainer(DataHarness.c$testRow1(), container);
        assertNotNull(container.getJassmDataRows());
        assertEquals(1, container.getJassmDataRows().length);
        assertEquals(DataHarness.c$testRow1(), container.getJassmDataRows()[0]);
    }

    @Test
    public void updateContainer() throws Exception {

    }

    @Test(expected = JassmStorage.DataStoreOpeningFailedException.class)
    public void loadDataStore1() throws Exception {
        File dmy = new File(tmpFile.getAbsolutePath()+"4711");
        dmy.deleteOnExit();
        JassmStorage.loadDataStore(dmy);
    }



}