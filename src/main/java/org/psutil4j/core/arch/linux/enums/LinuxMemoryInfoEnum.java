package org.psutil4j.core.arch.linux.enums;

public enum LinuxMemoryInfoEnum {

    /**
     * total program size
     * (same as VmSize in /proc/[pid]/status)
     * The size of the virtual address space
     */
    SIZE,

    /**
     * resident set size
     * (inaccurate; same as VmRSS in /proc/[pid]/status)
     * The size of the application is using the physical memory
     */
    RESIDENT,

    /**
     * number of resident shared pages
     * (i.e., backed by a file)
     * (inaccurate; same as RssFile+RssShmem in
     * /proc/[pid]/status)
     */
    SHARED,

    /**
     * text (code)
     */
    TEXT,

    /**
     * library (unused since Linux 2.6; always 0)
     */
    LIB,

    /**
     * data + stack
     */
    DATA,

    /**
     * dirty pages (unused since Linux 2.6; always 0)
     */
    DT,
}
