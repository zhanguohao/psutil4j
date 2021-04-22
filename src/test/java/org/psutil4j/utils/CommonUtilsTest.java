package org.psutil4j.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonUtilsTest {

    @Test
    void isInteger() {
        Assertions.assertTrue(CommonUtils.isInteger("123"));
        Assertions.assertTrue(CommonUtils.isInteger("-123"));
        Assertions.assertTrue(CommonUtils.isInteger("+123"));
        Assertions.assertTrue(CommonUtils.isInteger("0"));
        Assertions.assertTrue(CommonUtils.isInteger("-0"));
        Assertions.assertTrue(CommonUtils.isInteger("+0"));
        Assertions.assertFalse(CommonUtils.isInteger("+123 1"));
        Assertions.assertFalse(CommonUtils.isInteger("1 1"));
        Assertions.assertFalse(CommonUtils.isInteger("a123"));
        Assertions.assertFalse(CommonUtils.isInteger("123a"));
    }
}
