package org.psutil4j.constant;

/**
 * @author zhangguohao
 */
public enum Options {
    /**
     * waitpid options in <bits/waitflags.h>
     */
    WNOHANG(1, "Don't block waiting. "),
    WUNTRACED(2, "Report status of stopped children. "),
    ;

    Options(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
