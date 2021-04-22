package org.psutil4j.core.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author zhangguohao
 */
public class NativeErrno {

    private static final InterfaceDelegate DELEGATE = Native.load((Platform.isWindows() ? "msvcrt" : "c"), InterfaceDelegate.class);

    /**
     * The routine perror() produces a message on the standard error output,
     * describing the last error encountered during a call to a system or
     * library function. First (if s is not NULL and *s is not a null byte
     * ('\0')) the argument string s is printed, followed by a colon and a
     * blank. Then the message and a new-line.
     */
    public static int perror(String s) {
        return DELEGATE.perror(s);
    }

    /**
     * The strerror() function returns a string describing the error code passed
     * in the argument errnum, possibly using the LC_MESSAGES part of the
     * current locale to select the appropriate language. This string must not
     * be modified by the application, but may be modified by a subsequent call
     * to perror() or strerror(). No library function will modify this string.
     */
    public static String strerror(int errnum) {
        return DELEGATE.strerror(errnum);
    }

    public static String strerror() {
        return strerror(errno());
    }

    /**
     * The <errno.h> header file defines the integer variable errno, which is
     * set by system calls and some library functions in the event of an error
     * to indicate what went wrong. Its value is significant only when the call
     * returned an error (usually -1), and a function that does succeed is
     * allowed to change errno.
     */
    public static int errno() {
        return Native.getLastError();
    }

    interface InterfaceDelegate extends Library {

        /**
         * A descriptive error message output to the standard errors stderr
         *
         * @param s error msg
         * @return errno
         */
        int perror(String s);

        /**
         * Find the reason for the error
         *
         * @param errnum errno
         * @return err desc
         */
        String strerror(int errnum);

    }

}
