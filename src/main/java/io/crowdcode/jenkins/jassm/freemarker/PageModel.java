package io.crowdcode.jenkins.jassm.freemarker;

import freemarker.template.SimpleSequence;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;

import java.util.List;

/**
 * Created by marcus on 04.08.2016.
 */
public class PageModel {

    String headline;

    String columnCaption1;
    String columnCaption2;
    String columnCaption3;
    String columnCaption4;

    SimpleSequence jassmDataRows;

    public SimpleSequence getJassmDataRows() {
        return jassmDataRows;
    }

    public void setJassmDataRows(List<JassmDataRow> jassmDataRows) {
        this.jassmDataRows = new SimpleSequence(jassmDataRows);
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getColumnCaption1() {
        return columnCaption1;
    }

    public void setColumnCaption1(String columnCaption1) {
        this.columnCaption1 = columnCaption1;
    }

    public String getColumnCaption2() {
        return columnCaption2;
    }

    public void setColumnCaption2(String columnCaption2) {
        this.columnCaption2 = columnCaption2;
    }

    public String getColumnCaption3() {
        return columnCaption3;
    }

    public void setColumnCaption3(String columnCaption3) {
        this.columnCaption3 = columnCaption3;
    }

    public String getColumnCaption4() {
        return columnCaption4;
    }

    public void setColumnCaption4(String columnCaption4) {
        this.columnCaption4 = columnCaption4;
    }
}
