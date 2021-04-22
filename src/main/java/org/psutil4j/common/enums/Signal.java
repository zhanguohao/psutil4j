package org.psutil4j.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * common signal
 *
 * @author zhangguohao
 */
public enum Signal {

    /**
     * common kernel signal
     */
    SIGHUP(1, "Term", "Hangup detected on controlling terminal or death of controlling process"),
    SIGINT(2, "Term", "Interrupt from keyboard"),
    SIGQUIT(3, "Core", "Quit from keyboard"),
    SIGILL(4, "Core", "Illegal Instruction"),
    SIGABRT(6, "Core", "Abort signal from abort(3)"),
    SIGFPE(8, "Core", "Floating point exception"),
    SIGKILL(9, "Term", "Kill signal"),
    SIGSEGV(11, "Core", "Invalid memory reference"),
    SIGPIPE(13, "Term", "Broken pipe: write to pipe with no readers"),
    SIGALRM(14, "Term", "Timer signal from alarm(2)"),
    SIGTERM(15, "Term", "Termination signal"),
    ;

    Signal(int code, String effect, String desc) {
        this.code = code;
        this.effect = effect;
        this.desc = desc;
    }

    private final int code;
    private final String effect;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getEffect() {
        return effect;
    }

    public String getDesc() {
        return desc;
    }

    private static final Map<Integer, Signal> SIGNAL_MAP = new HashMap<>();

    static {
        for (Signal signal : Signal.values()) {
            SIGNAL_MAP.put(signal.code, signal);
        }
    }

    public static Signal of(Integer code) {
        if (SIGNAL_MAP.containsKey(code)) {
            return SIGNAL_MAP.get(code);
        }
        throw new IllegalArgumentException("invalid code : " + code);
    }

}
