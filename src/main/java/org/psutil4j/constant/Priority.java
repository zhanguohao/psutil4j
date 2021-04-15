package org.psutil4j.constant;

/**
 * @author zhangguohao
 */

public enum Priority {
    /**
     * priority
     */
    PRIO_PROCESS(0, "WHO is a process ID."),
    PRIO_PGRP(1, " WHO is a process group ID."),
    PRIO_USER(2, "WHO is a user ID."),
    ;

    Priority(int code, String desc) {
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
