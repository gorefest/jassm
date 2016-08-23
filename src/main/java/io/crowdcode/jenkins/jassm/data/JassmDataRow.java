package io.crowdcode.jenkins.jassm.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by marcus on 26.07.2016.
 */
public class JassmDataRow implements Serializable{

    private String group;
    private String id;
    private Date date;
    private String columnValue1;
    private String columnValue2;
    private String columnValue3;
    private String columnValue4;

    public JassmDataRow() {
    }

    public JassmDataRow(String group, String id, Date date, String columnValue1, String columnValue2, String columnValue3, String columnValue4) {
        this.group = group;
        this.id = id;
        this.date = date != null ? new Date(date.getTime()) : null;
        this.columnValue1 = columnValue1;
        this.columnValue2 = columnValue2;
        this.columnValue3 = columnValue3;
        this.columnValue4 = columnValue4;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date != null ? new Date(date.getTime()): null;
    }

    public void setDate(Date date) {
        this.date = date != null ? new Date(date.getTime()) : null;
    }

    public String getColumnValue1() {
        return columnValue1;
    }

    public void setColumnValue1(String columnValue1) {
        this.columnValue1 = columnValue1;
    }

    public String getColumnValue2() {
        return columnValue2;
    }

    public void setColumnValue2(String columnValue2) {
        this.columnValue2 = columnValue2;
    }

    public String getColumnValue3() {
        return columnValue3;
    }

    public void setColumnValue3(String columnValue3) {
        this.columnValue3 = columnValue3;
    }

    public String getColumnValue4() {
        return columnValue4;
    }

    public void setColumnValue4(String columnValue4) {
        this.columnValue4 = columnValue4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JassmDataRow that = (JassmDataRow) o;

        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (!id.equals(that.id)) return false;
        if (columnValue1 != null ? !columnValue1.equals(that.columnValue1) : that.columnValue1 != null) return false;
        if (columnValue2 != null ? !columnValue2.equals(that.columnValue2) : that.columnValue2 != null) return false;
        if (columnValue3 != null ? !columnValue3.equals(that.columnValue3) : that.columnValue3 != null) return false;
        return columnValue4 != null ? columnValue4.equals(that.columnValue4) : that.columnValue4 == null;

    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + id.hashCode();
        result = 31 * result + (columnValue1 != null ? columnValue1.hashCode() : 0);
        result = 31 * result + (columnValue2 != null ? columnValue2.hashCode() : 0);
        result = 31 * result + (columnValue3 != null ? columnValue3.hashCode() : 0);
        result = 31 * result + (columnValue4 != null ? columnValue4.hashCode() : 0);
        return result;
    }
}
