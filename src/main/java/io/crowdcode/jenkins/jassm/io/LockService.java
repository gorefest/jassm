package io.crowdcode.jenkins.jassm.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by marcus on 02.08.2016.
 */
public class LockService {


    public static final class LockAquisitionFailed extends RuntimeException {
        public LockAquisitionFailed() {
            super("Aquiring the lockfile was impossible!");
        }

        public LockAquisitionFailed(String message) {
            super(message);
        }

        public LockAquisitionFailed(Throwable cause) {
            super("Aquiring the lockfile was impossible!", cause);
        }
    }

    public static final class LockReleaseFailed extends RuntimeException {
        public LockReleaseFailed() {
            super("Releasing the lock failed!");
        }

        public LockReleaseFailed(String message) {
            super(message);
        }

    }

    public static final String LOCK = ".lock";
    public static final int MAX_WAIT_MILLIS = 10000;
    public static final int SLEEP_MILLIS = 250;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value="SWL_SLEEP_WITH_LOCK_HELD",
            justification="The required lock is mandatory")
    public static synchronized void lock(File file) {
        File lockFile = getLockFile(file);
        Long begin = System.currentTimeMillis();

        while (lockFile.exists() && (System.currentTimeMillis() - begin < MAX_WAIT_MILLIS)) {
            try {
                Thread.sleep(SLEEP_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (lockFile.exists()) {
            throw new LockAquisitionFailed();
        } else {
            try {
                if (!lockFile.createNewFile()) {
                    throw new LockAquisitionFailed("Lockfile creation failed!");
                };
                lockFile.deleteOnExit();
            } catch (IOException e) {
                throw new LockAquisitionFailed(e);
            }
        }

    }

    private static File getLockFile(File file) {
        return new File(file.getAbsolutePath()+ LOCK);
    }

    public static void unlock(File file) {
        File lockFile = getLockFile(file);
        if (lockFile.exists()) {
            if (!lockFile.delete()) {
                throw new LockReleaseFailed();
            }
        } else {
            throw new LockReleaseFailed("Trying to unlock a nonexistent Lock!");
        }
    }

}
