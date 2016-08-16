package io.crowdcode.jenkins.jassm.dtoservice;

import io.crowdcode.jenkins.jassm.JassmBuilder;
import io.crowdcode.jenkins.jassm.data.JassmDataRow;
import io.crowdcode.jenkins.jassm.harness.DataHarness;
import org.apache.sshd.common.kex.DH;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marcus on 30.07.2016.
 */
public class JassmDataRowDtoServiceTest {

    JassmBuilder candidate;

    @Before
    public void setUp() throws Exception {
        candidate = DataHarness.c$testBuilder1();
    }

    @Test
    public void convert() throws Exception {
        JassmDataRow probe = JassmDataRowDtoService.convert(candidate);
        assertNotNull(probe);
        assertNotNull(probe.getDate());
        assertEquals(candidate.getId(), probe.getId());
        assertEquals(candidate.getGroup(), probe.getGroup());
        assertEquals(candidate.getColumnValue1(), probe.getColumnValue1());
        assertEquals(candidate.getColumnValue2(), probe.getColumnValue2());
        assertEquals(candidate.getColumnValue3(), probe.getColumnValue3());
        assertEquals(candidate.getColumnValue4(), probe.getColumnValue4());

    }

}