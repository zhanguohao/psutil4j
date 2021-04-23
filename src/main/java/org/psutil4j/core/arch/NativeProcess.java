package org.psutil4j.core.arch;

import org.psutil4j.common.enums.State;
import org.psutil4j.core.pojo.PriorityUserId;

import java.util.EnumMap;
import java.util.List;

/**
 * @author zhangguohao
 */
public interface NativeProcess {
    /**
     * get process name
     *
     * @return name
     */
    String getName();

    /**
     * get process exe
     *
     * @return exe
     */
    String getExe();

    /**
     * get process cmdLine
     *
     * @return cmd line
     */
    String[] getCmdLine();

    /**
     * The time the process started after system boot
     *
     * @return start time
     */
    Integer getStartTime();

    /**
     * get mem info
     *
     * @return mem info
     */
    EnumMap<?, Integer> getMemoryInfo();

    /**
     * get process nice
     *
     * @return nice value
     */
    Integer getNice();

    /**
     * set process nice
     *
     * @param value nice value
     * @return int
     */
    Integer setNice(Integer value);

    /**
     * get process state
     *
     * @return Status
     */
    State getState();

    /**
     * get pid
     *
     * @return pid
     */
    Integer getPid();

    /**
     * get ppid
     *
     * @return ppid
     */
    Integer getPpid();

    /**
     * process UIDs as a (real, effective, saved)
     *
     * @return process UIDs
     */
    PriorityUserId getUids();

    /**
     * process GIDs as a (real, effective, saved)
     *
     * @return process GIDs
     */
    PriorityUserId getGids();

    /**
     * is running or not
     */
    boolean isRunning();

    /**
     * get thread ids
     */
    List<Integer> getThreadIds();

}
