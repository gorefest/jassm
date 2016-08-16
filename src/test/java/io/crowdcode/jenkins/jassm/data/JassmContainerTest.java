package io.crowdcode.jenkins.jassm.data;

import io.crowdcode.jenkins.jassm.harness.DataHarness;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by marcus on 02.08.2016.
 */
public class JassmContainerTest {

    /**
     * one container, one group, one row
     * @throws Exception
     */
    @Test
    public void toGroupMap1() throws Exception {
        JassmContainer container = DataHarness.c$testContainer1();
        Map<String, List<JassmDataRow>> map = container.toGroupMap();
        assertNotNull(map);
        assertEquals(1, map.keySet().size());
        Iterator<Map.Entry<String, List<JassmDataRow>>> iterator = map.entrySet().iterator();
        Map.Entry<String, List<JassmDataRow>> next = iterator.next();
        assertNotNull(next.getValue());
        assertEquals(1, next.getValue().size());
    }


    /**
     * one container, one group, two rows
     * @throws Exception
     */
    @Test
    public void toGroupMap2() throws Exception {
        JassmContainer container = DataHarness.c$testContainer2();
        Map<String, List<JassmDataRow>> map = container.toGroupMap();
        assertNotNull(map);
        assertEquals(1, map.keySet().size());
        Iterator<Map.Entry<String, List<JassmDataRow>>> iterator = map.entrySet().iterator();
        Map.Entry<String, List<JassmDataRow>> next = iterator.next();
        assertNotNull(next.getValue());
        assertEquals(2, next.getValue().size());
    }

    /**
     * one container, two group, two rows in group 1, one row in group 2
     * @throws Exception
     */
    @Test
    public void toGroupMap3() throws Exception {
        JassmContainer container = DataHarness.c$testContainer3();
        Map<String, List<JassmDataRow>> map = container.toGroupMap();
        assertNotNull(map);
        assertEquals(2, map.keySet().size());
        List<JassmDataRow> d1 = map.get(DataHarness.FOOGROUP);
        assertNotNull(d1);
        assertEquals(2, d1.size());
        List<JassmDataRow> d2 = map.get(JassmContainer.DEFAULT_GROUP);
        assertNotNull(d2);
        assertEquals(1, d2.size());


    }}