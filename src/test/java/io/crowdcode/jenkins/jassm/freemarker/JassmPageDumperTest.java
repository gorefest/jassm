package io.crowdcode.jenkins.jassm.freemarker;

import io.crowdcode.jenkins.jassm.harness.DataHarness;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by marcus on 02.08.2016.
 */
public class JassmPageDumperTest {
    @Test
    public void initialize() throws Exception {
        assertNotNull(JassmPageDumper.TEMP_OUTPUT_DIRECTORY);
        assertNotNull(JassmPageDumper.TEMPLATE_FILE);
        assertTrue(JassmPageDumper.TEMPLATE_FILE.exists());
        assertTrue(JassmPageDumper.TEMP_OUTPUT_DIRECTORY.exists());
        assertTrue(JassmPageDumper.TEMP_OUTPUT_DIRECTORY.isDirectory());
    }

    @Test
    public void testDump() throws Exception {
        JassmPageDumper.writeOutputFile(DataHarness.c$testContainer3(), File.createTempFile("foo","bar").getParentFile(),"Test","Column1","Column2","Column3","Column4", null);
    }

}