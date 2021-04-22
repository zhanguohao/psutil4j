package org.psutil4j.core.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author zhangguohao
 */
public class NativeProcessOperation {

    private static final InterfaceDelegate DELEGATE = Native.load((Platform.isWindows() ? "msvcrt" : "c"), InterfaceDelegate.class);

    /**
     * get current process pid
     *
     * @return pid
     */
    public static Integer getPid() {
        int pid = DELEGATE.getpid();
        if (pid > 0) {
            return pid;
        }
        return null;
    }

    interface InterfaceDelegate extends Library {

        /**
         * exit self
         *
         * @param status return status to parent
         */
        void exit(int status);

        /**
         * get current process pid
         *
         * @return pid
         */
        int getpid();

        /**
         * get process pgid
         *
         * @param pid pid
         * @return pgid
         */
        int getpgid(int pid);

        /**
         * get current process ppid
         *
         * @return ppid
         */
        int getppid();

        /**
         * get current process pgid
         *
         * @return pgid
         */
        int getpgrp();

        /**
         * set pid with pgid
         * <p>
         * pid pgid note
         * 0   0    use current pid and pgid
         * 1   0    use current pgid
         * 0   1    use current pid
         *
         * @param pid  pid
         * @param pgid pgid
         * @return @return 0 -> success; -1 -> error; reason in errno.
         */
        int setpgid(int pid, int pgid);

        /**
         * set pid with pgid, be equal to setpgid(0,0)
         *
         * @return 0 -> success; -1 -> error; reason in errno.
         */
        int setpgrp();

        /**
         * get priority
         * <p>
         * which           who   note
         * PRIO_PROCESS    pid   process priority
         * PRIO_PGRP       pgid  process group priority
         * PRIO_USER       uid   user priority
         *
         * @param which which
         * @param who   who
         * @return priority
         */
        int getpriority(int which, int who);

        /**
         * set priority
         * <p>
         * which           who   note
         * PRIO_PROCESS    pid   set process priority
         * PRIO_PGRP       pgid  set process group priority
         * PRIO_USER       uid   set user priority
         *
         * @param which which
         * @param who   who
         * @param prio  prio
         * @return priority
         */
        int setpriority(int which, int who, int prio);

        /**
         * lower , priority is higher , only root can use negative number
         *
         * @param inc inc
         * @return 0 -> success; -1 -> error; reason in errno.
         */
        int nice(int inc);

        /**
         * execute shell command
         *
         * @param command command
         * @return error code
         */
        int system(String command);

        /**
         * Stop the current process, until a signal comes or the child process ends
         *
         * @param status sub process exit code
         * @return sub process pid
         */
        public int wait(int[] status);

        /**
         * Stop the current process, until a signal comes or the child process ends
         * <p>
         * pid < -1  Wait for any child process whose process group ID is the absolute value of pid.
         * pid = -1  Waiting for any child process is equivalent to wait().
         * pid = 0   Wait for any child process with the same process group ID as the current process.
         * pid > 0   Wait for any child process whose child process ID is pid.
         *
         * @param pid     pid
         * @param status  status
         * @param options Options
         * @return sub process pid
         */
        public int waitpid(int pid, int[] status, int options);
    }
}
