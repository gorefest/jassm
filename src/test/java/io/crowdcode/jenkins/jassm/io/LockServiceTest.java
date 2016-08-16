package io.crowdcode.jenkins.jassm.io;

import io.crowdcode.jenkins.jassm.data.JassmContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by marcus on 02.08.2016.
 */
public class LockServiceTest {

    Log log = LogFactory.getLog(this.getClass());

    File tmpFile;

    @Before
    public void setup() throws Exception{
        tmpFile = File.createTempFile("sample",".xml");
        tmpFile.deleteOnExit();
    }

    @Before
    public void setUp() throws Exception {

    }

    /**
     * Concurrency test for lock file semaphore
     *
     * @throws InterruptedException
     */
    @Test
    public void testLockAndUnlock() throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                log.info("T1: Locking and sleep for 3 secs");
                LockService.lock(tmpFile);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("T1: Unlocking");
                LockService.unlock(tmpFile);
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                log.info("T2: Locking and sleep for 3 secs");
                LockService.lock(tmpFile);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("T2: Unlocking");
                LockService.unlock(tmpFile);
            }
        };

        t1.start();
        assertTrue(t1.isAlive());
        Thread.sleep(1000);

        t2.start();
        Thread.yield();
        assertTrue(t2.isAlive());

        t1.join();
        assertFalse(t1.isAlive());
        assertTrue(t2.isAlive());
        t2.join();
        assertFalse(t2.isAlive());
    }

}