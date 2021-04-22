package org.psutil4j.core.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author zhangguohao
 */
public class NativeSignalProcess {

    private static final InterfaceDelegate DELEGATE = Native.load((Platform.isWindows() ? "msvcrt" : "c"), InterfaceDelegate.class);

    public int kill(int pid, int signal) {
        return DELEGATE.kill(pid, signal);
    }

    interface InterfaceDelegate extends Library {
        /**
         * Send a signal to the specified process
         *
         * @param pid pid
         * @param signal signal
         * @return int
         */
        int kill(int pid, int signal);
    }
}
