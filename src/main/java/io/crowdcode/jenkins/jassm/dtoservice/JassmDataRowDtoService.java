package io.crowdcode.jenkins.jassm.dtoservice;

import io.crowdcode.jenkins.jassm.JassmBuilder;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;

import java.util.Date;

/**
 * Created by marcus on 30.07.2016.
 */
public class JassmDataRowDtoService {

    public static JassmDataRow convert(JassmBuilder builder, JassmBuilder.VariableResolver resolver) {
        JassmDataRow row = new JassmDataRow();
        row.setId(resolver.resolve(builder.getId()));
        row.setGroup(resolver.resolve(builder.getGroup()));
        row.setColumnValue1(resolver.resolve(builder.getColumnValue1()));
        row.setColumnValue2(resolver.resolve(builder.getColumnValue2()));
        row.setColumnValue3(resolver.resolve(builder.getColumnValue3()));
        row.setColumnValue4(resolver.resolve(builder.getColumnValue4()));
        row.setDate(new Date());
        return row;
    }
}
