package org.psutil4j.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangguohao
 */
public enum State {

    /**
     * process state
     */
    RUNNING(0, "running"),
    SLEEPING(1, "sleeping"),
    DISK_SLEEP(2, "disk-sleep"),
    STOPPED(3, "stopped"),
    TRACING_STOP(4, "tracing-stop"),
    ZOMBIE(5, "zombie"),
    DEAD(6, "dead"),
    WAKE_KILL(7, "wake-kill"),
    WAKING(8, "waking"),
    // Linux, macOS, FreeBSD
    IDLE(9, "idle"),
    // FreeBSD
    LOCKED(10, "locked"),
    WAITING(11, "waiting"),
    // NetBSD
    SUSPENDED(12, "suspended"),
    // Linux
    PARKED(13, "parked"),
    OTHER(14, "OTHER"),
    ;

    State(int code, String state) {
        this.code = code;
        this.state = state;

    }

    private final int code;
    private final String state;

    public int getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    private static final Map<Integer, State> STATE_MAP = new HashMap<>();

    static {
        for (State state : State.values()) {
            STATE_MAP.put(state.code, state);
        }
    }

    public static State of(Integer code) {
        if (STATE_MAP.containsKey(code)) {
            return STATE_MAP.get(code);
        }
        throw new IllegalArgumentException("invalid code : " + code);
    }
}
