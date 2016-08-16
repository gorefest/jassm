package io.crowdcode.jenkins.jassm.harness;

import io.crowdcode.jenkins.jassm.JassmBuilder;
import io.crowdcode.jenkins.jassm.data.JassmContainer;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;

import java.util.Date;

/**
 * Created by marcus on 31.07.2016.
 */
public class DataHarness {

    public static final String FOOGROUP = "Foogroup";
    public static final String FOOGROUP_2 = "Foogroup2";
    public static final String FOO_ID = "FooId";
    public static final String FOO_ID_2 = "FooId2";
    public static final String FOO_ID_3 = "FooId3";
    public static final String VALUE_1 = "Value 1";
    public static final String VALUE_2 = "Value 2";
    public static final String VALUE_3 = "Value 3";
    public static final String VALUE_4 = "Value 4";

    public static final String VALUE_11 = "Value 11";
    public static final String VALUE_12 = "Value 12";
    public static final String VALUE_13 = "Value 13";
    public static final String VALUE_14 = "Value 14";

    public static JassmDataRow c$testRow1(){
        return new JassmDataRow(FOOGROUP, FOO_ID, new Date(), VALUE_1, VALUE_2, VALUE_3, VALUE_4);
    }

    public static JassmDataRow c$testRow2(){
        return new JassmDataRow(FOOGROUP, FOO_ID_2, new Date(), VALUE_11, VALUE_12, VALUE_13, VALUE_14);
    }

    public static JassmDataRow c$testRow3(){
        return new JassmDataRow(null, FOO_ID_3, new Date(), VALUE_11, VALUE_12, VALUE_13, VALUE_14);
    }

    public static JassmContainer c$testContainer1(){
        JassmContainer container = new JassmContainer();
        container.setJassmDataRows(new JassmDataRow[]{c$testRow1()});
        return container;
    }


    public static JassmContainer c$testContainer2(){
        JassmContainer container = new JassmContainer();
        container.setJassmDataRows(new JassmDataRow[]{c$testRow1(), c$testRow2()});
        return container;
    }

    public static JassmContainer c$testContainer3(){
        JassmContainer container = new JassmContainer();
        container.setJassmDataRows(new JassmDataRow[]{c$testRow1(), c$testRow2(), c$testRow3()});
        return container;
    }

    public static JassmBuilder c$testBuilder1(){
        return new JassmBuilder(FOOGROUP, FOO_ID, VALUE_1, VALUE_2,  VALUE_3, VALUE_4);
    }

}
