package org.psutil4j.constant;

/**
 * @author zhangguohao
 */
public class Constants {

    /**
     * process status
     */
    public static final String STATUS_RUNNING = "running";
    public static final String STATUS_SLEEPING = "sleeping";
    public static final String STATUS_DISK_SLEEP = "disk-sleep";
    public static final String STATUS_STOPPED = "stopped";
    public static final String STATUS_TRACING_STOP = "tracing-stop";
    public static final String STATUS_ZOMBIE = "zombie";
    public static final String STATUS_DEAD = "dead";
    public static final String STATUS_WAKE_KILL = "wake-kill";
    public static final String STATUS_WAKING = "waking";

    /**
     * Linux, macOS, FreeBSD
     */
    public static final String STATUS_IDLE = "idle";

    /**
     * FreeBSD
     */
    public static final String STATUS_LOCKED = "locked";
    public static final String STATUS_WAITING = "waiting";

    /**
     * NetBSD
     */
    public static final String STATUS_SUSPENDED = "suspended";

    /**
     * Linux
     */
    public static final String STATUS_PARKED = "parked";

    /**
     * connection status
     */
    public static final String CONN_ESTABLISHED = "ESTABLISHED";
    public static final String CONN_SYN_SENT = "SYN_SENT";
    public static final String CONN_SYN_RECV = "SYN_RECV";
    public static final String CONN_FIN_WAIT1 = "FIN_WAIT1";
    public static final String CONN_FIN_WAIT2 = "FIN_WAIT2";
    public static final String CONN_TIME_WAIT = "TIME_WAIT";
    public static final String CONN_CLOSE = "CLOSE";
    public static final String CONN_CLOSE_WAIT = "CLOSE_WAIT";
    public static final String CONN_LAST_ACK = "LAST_ACK";
    public static final String CONN_LISTEN = "LISTEN";
    public static final String CONN_CLOSING = "CLOSING";
    public static final String CONN_NONE = "NONE";
}
