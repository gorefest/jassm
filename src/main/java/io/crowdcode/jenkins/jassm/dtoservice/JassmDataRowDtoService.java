package io.crowdcode.jenkins.jassm.dtoservice;

import io.crowdcode.jenkins.jassm.JassmBuilder;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;

import java.util.Date;

/**
 * Created by marcus on 30.07.2016.
 */
public class JassmDataRowDtoService {

    public static JassmDataRow convert(JassmBuilder builder) {
        JassmDataRow row = new JassmDataRow();
        row.setId(builder.getId());
        row.setGroup(builder.getGroup());
        row.setColumnValue1(builder.getColumnValue1());
        row.setColumnValue2(builder.getColumnValue2());
        row.setColumnValue3(builder.getColumnValue3());
        row.setColumnValue4(builder.getColumnValue4());
        row.setDate(new Date());
        return row;
    }
}
