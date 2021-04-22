package org.psutil4j.core.arch;

import java.util.List;
import java.util.Map;

/**
 * @author zhangguohao
 */
public interface NativePlatform {

    /**
     * get platform Process
     *
     * @param pid pid
     * @return Process
     */
    NativeProcess getProcess(Integer pid);

    /**
     * get all pids
     *
     * @return pids
     */
    List<Integer> getPids();

    /**
     * pid exist or not
     *
     * @param pid pid
     * @return boolean
     */
    boolean pidExists(Integer pid);

    /**
     * get ppid map
     *
     * @return ppid map
     */
    Map<Integer, Integer> getPpidMap();

    /**
     * get boot time
     *
     * @return boot timestamp
     */
    Integer getBootTime();

}
