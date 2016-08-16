package io.crowdcode.jenkins.jassm.data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marcus on 26.07.2016.
 */
@XmlRootElement
public class JassmContainer implements Serializable{

    public static final String DEFAULT_GROUP = "DEFAULT";

    private JassmDataRow[] jassmDataRows;

    public JassmDataRow[] getJassmDataRows() {
        return jassmDataRows;
    }

    public void setJassmDataRows(JassmDataRow[] jassmDataRows) {
        this.jassmDataRows = jassmDataRows;
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
