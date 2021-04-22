package org.psutil4j.core.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author zhangguohao
 */
public class NativeStdio {

    private static final InterfaceDelegate DELEGATE = Native.load((Platform.isWindows() ? "msvcrt" : "c"), InterfaceDelegate.class);

    interface InterfaceDelegate extends Library {

        /**
         * native printf func
         *
         * @param format format
         * @param args args
         */
        void printf(String format, Object... args);

    }
}
