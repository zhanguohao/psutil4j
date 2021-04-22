package org.psutil4j.core.arch.linux.enums;

public enum LinuxProcessStatEnum {

    /**
     * The process ID.
     */
    PID,

    /**
     * The filename of the executable.
     */
    NAME,

    /**
     * One of the following characters, indicating process state:
     * <p>
     * R Running
     * <p>
     * S Sleeping in an interruptible wait
     * <p>
     * D Waiting in uninterruptible disk sleep
     * <p>
     * Z Zombie
     * <p>
     * T Stopped (on a signal) or (before Linux 2.6.33) trace stopped
     * <p>
     * t Tracing stop (Linux 2.6.33 onward)
     * <p>
     * W Paging (only before Linux 2.6.0)
     * <p>
     * X Dead (from Linux 2.6.0 onward)
     * <p>
     * x Dead (Linux 2.6.33 to 3.13 only)
     * <p>
     * K Wakekill (Linux 2.6.33 to 3.13 only)
     * <p>
     * W Waking (Linux 2.6.33 to 3.13 only)
     * <p>
     * P Parked (Linux 3.9 to 3.13 only)
     */
    STATE,

    /**
     * The PID of the parent of this process.
     */
    PPID,
    /**
     * The process group ID of the process.
     */
    PGRP,
    /**
     * The session ID of the process.
     */
    SESSION,
    /**
     * The controlling terminal of the process. (The minor device number is
     * contained in the combination of bits 31 to 20 and 7 to 0; the major device
     * number is in bits 15 to 8.)
     */
    TTY_NR,
    /**
     * The ID of the foreground process group of the controlling terminal of the
     * process.
     */
    PTGID,
    /**
     * The kernel flags word of the process. For bit meanings, see the PF_* defines
     * in the Linux kernel source file include/linux/sched.h. Details depend on the
     * kernel version.
     */
    FLAGS,
    /**
     * The number of minor faults the process has made which have not required
     * loading a memory page from disk.
     */
    MINFLT,
    /**
     * The number of minor faults that the process's waited-for children have made.
     */
    CMINFLT,
    /**
     * The number of major faults the process has made which have required loading a
     * memory page from disk.
     */
    MAJFLT,
    /**
     * The number of major faults that the process's waited-for children have made.
     */
    CMAJFLT,
    /**
     * Amount of time that this process has been scheduled in user mode, measured in
     * clock ticks. This includes guest time, cguest_time (time spent running a
     * virtual CPU), so that applications that are not aware of the guest time field
     * do not lose that time from their calculations.
     */
    UTIME,
    /**
     * Amount of time that this process has been scheduled in kernel mode, measured
     * in clock ticks.
     */
    STIME,
    /**
     * Amount of time that this process's waited-for children have been scheduled in
     * user mode, measured in clock ticks. This includes guest time, cguest_time
     * (time spent running a virtual CPU).
     */
    CUTIME,
    /**
     * Amount of time that this process's waited-for children have been scheduled in
     * kernel mode, measured in clock ticks.
     */
    CSTIME,
    /**
     * For processes running a real-time scheduling policy (policy below; see
     * sched_setscheduler(2)), this is the negated scheduling priority, minus one;
     * that is, a number in the range -2 to -100, corresponding to real-time
     * priorities 1 to 99. For processes running under a non-real-time scheduling
     * policy, this is the raw nice value (setpriority(2)) as represented in the
     * kernel. The kernel stores nice values as numbers in the range 0 (high) to 39
     * (low), corresponding to the user-visible nice range of -20 to 19.
     */
    PRIORITY,
    /**
     * The nice value (see setpriority(2)), a value in the range 19 (low priority)
     * to -20 (high priority).
     */
    NICE,
    /**
     * Number of threads in this process.
     */
    NUM_THREADS,
    /**
     * The time in jiffies before the next SIGALRM is sent to the process due to an
     * interval timer. Since ker‐nel 2.6.17, this field is no longer maintained, and
     * is hard coded as 0.
     */
    ITREALVALUE,
    /**
     * The time the process started after system boot, in clock ticks.
     */
    STARTTIME,
    /**
     * Virtual memory size in bytes.
     */
    VSIZE,
    /**
     * Resident Set Size: number of pages the process has in real memory. This is
     * just the pages which count toward text, data, or stack space. This does not
     * include pages which have not been demand-loaded in, or which are swapped out.
     */
    RSS,
    /**
     * Current soft limit in bytes on the rss of the process; see the description of
     * RLIMIT_RSS in getrlimit(2).
     */
    RSSLIM,
    /**
     * The address above which program text can run.
     */
    STARTCODE,

    /**
     * The address below which program text can run.
     */
    ENDCODE,
    /**
     * The address of the start (i.e., bottom) of the stack.
     */
    STARTSTACK,
    /**
     * The current value of ESP (stack pointer), as found in the kernel stack page
     * for the process.
     */
    KSTKESP,
    /**
     * The current EIP (instruction pointer).
     */
    KSTKEIP,
    /**
     * The bitmap of pending signals, displayed as a decimal number. Obsolete,
     * because it does not provide information on real-time signals; use
     * /proc/[pid]/status instead.
     */
    SIGNAL,
    /**
     * The bitmap of blocked signals, displayed as a decimal number. Obsolete,
     * because it does not provide information on real-time signals; use
     * /proc/[pid]/status instead.
     */
    BLOCKED,
    /**
     * The bitmap of ignored signals, displayed as a decimal number. Obsolete,
     * because it does not provide information on real-time signals; use
     * /proc/[pid]/status instead.
     */
    SIGIGNORE,
    /**
     * The bitmap of caught signals, displayed as a decimal number. Obsolete,
     * because it does not provide information on real-time signals; use
     * /proc/[pid]/status instead.
     */
    SIGCATCH,
    /**
     * This is the "channel" in which the process is waiting. It is the address of a
     * location in the kernel where the process is sleeping. The corresponding
     * symbolic name can be found in /proc/[pid]/wchan.
     */
    WCHAN,
    /**
     * Number of pages swapped (not maintained).
     */
    NSWAP,
    /**
     * Cumulative nswap for child processes (not maintained).
     */
    CNSWAP,
    /**
     * Signal to be sent to parent when we die.
     */
    EXIT_SIGNAL,
    /**
     * CPU number last executed on.
     */
    PROCESSOR,
    /**
     * Real-time scheduling priority, a number in the range 1 to 99 for processes
     * scheduled under a real-time policy, or 0, for non-real-time processes (see
     * sched_setscheduler(2)).
     */
    RT_PRIORITY,
    /**
     * Scheduling policy (see sched_setscheduler(2)). Decode using the SCHED_*
     * constants in linux/sched.h.
     */
    POLICY,
    /**
     * Aggregated block I/O delays, measured in clock ticks (centiseconds).
     */
    DELAYACCT_BLKIO_TICKS,
    /**
     * Guest time of the process (time spent running a vir‐ tual CPU for a guest
     * operating system), measured in clock ticks.
     */
    GUEST_TIME,
    /**
     * Guest time of the process's children, measured in clock ticks.
     */
    CGUEST_TIME,
    /**
     * Address above which program initialized and uninitialized (BSS) data are
     * placed.
     */
    START_DATA,
    /**
     * Address below which program initialized and uninitialized (BSS) data are
     * placed.
     */
    END_DATA,
    /**
     * Address above which program heap can be expanded with brk(2).
     */
    START_BRK,
    /**
     * Address above which program command-line arguments (argv) are placed.
     */
    ARG_START,

    /**
     * Address below program command-line arguments (argv) are placed.
     */
    ARG_END,

    /**
     * Address above which program environment is placed.
     */
    ENV_START,

    /**
     * Address below which program environment is placed.
     */
    ENV_END,

    /**
     * The thread's exit status in the form reported by waitpid(2).
     */
    EXIT_CODE;
}
