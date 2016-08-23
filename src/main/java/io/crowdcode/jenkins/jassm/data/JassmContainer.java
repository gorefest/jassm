package io.crowdcode.jenkins.jassm.data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Created by marcus on 26.07.2016.
 */
@XmlRootElement
public class JassmContainer implements Serializable{

    public static final String DEFAULT_GROUP = "DEFAULT";

    private JassmDataRow[] jassmDataRows;

    public JassmDataRow[] getJassmDataRows() {
        return jassmDataRows != null ? Arrays.copyOf(jassmDataRows, jassmDataRows.length) : null;
    }

    public void setJassmDataRows(JassmDataRow[] jassmDataRows) {
        if (jassmDataRows != null) {
            this.jassmDataRows = Arrays.copyOf(jassmDataRows, jassmDataRows.length);
        }
    }

    public Map<String, List<JassmDataRow>> toGroupMap() {
        Map<String, List<JassmDataRow>> result = new HashMap<>();
        if (jassmDataRows != null && (jassmDataRows.length > 0)){
            for (int i = 0; i < jassmDataRows.length; i++) {
                JassmDataRow jassmDataRow = jassmDataRows[i];
                String groupKey = jassmDataRow.getGroup() != null ? jassmDataRow.getGroup() : DEFAULT_GROUP;
                List<JassmDataRow> destination = result.get(groupKey);

                if (destination == null) {
                    destination = new ArrayList<>();
                    result.put(groupKey, destination);
                }

                destination.add(jassmDataRow);

            }
        }
        return result;
    }


}
