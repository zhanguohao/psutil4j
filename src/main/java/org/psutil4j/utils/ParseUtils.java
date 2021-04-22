package org.psutil4j.utils;

import java.math.BigInteger;

/**
 * @author zhangguohao
 */
public class ParseUtils {

    /**
     * parse Int Or Default
     *
     * @param s int string
     * @param defaultInt default int
     * @return int
     */
    public static int parseIntOrDefault(String s, int defaultInt) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultInt;
        }
    }

    /**
     * Attempts to parse a string to a long. If it fails, returns the default
     *
     * @param s The string to parse
     * @param defaultLong The value to return if parsing fails
     * @return The parsed long, or the default if parsing failed
     */
    public static long parseLongOrDefault(String s, long defaultLong) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return defaultLong;
        }
    }

    /**
     * Attempts to parse a string to an "unsigned" long. If it fails, returns the
     * default
     *
     * @param s The string to parse
     * @param defaultLong The value to return if parsing fails
     * @return The parsed long containing the same 64 bits that an unsigned long
     * would contain (which may produce a negative value)
     */
    public static long parseUnsignedLongOrDefault(String s, long defaultLong) {
        try {
            return new BigInteger(s).longValue();
        } catch (NumberFormatException e) {
            return defaultLong;
        }
    }

    /**
     * Attempts to parse a string to a double. If it fails, returns the default
     *
     * @param s The string to parse
     * @param defaultDouble The value to return if parsing fails
     * @return The parsed double, or the default if parsing failed
     */
    public static double parseDoubleOrDefault(String s, double defaultDouble) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultDouble;
        }
    }

}
