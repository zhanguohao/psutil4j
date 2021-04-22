package org.psutil4j.core;

import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.exception.NotSupportPlatformException;

import java.util.Date;
import java.util.List;

/**
 * Represents an OS process with the given PID.
 *
 * <p>
 * The only exceptions for which process identity is pre-emptively
 * checked and guaranteed are:
 * <p>
 * - parent()
 * - children()
 * - nice() (set)
 * - terminate()
 * - kill()
 *
 * @author zhangguohao
 */
public class Process {

    private Integer pid;
    private String exe;
    private Date createTime;
    private Boolean running;
    private String hash;
    private Integer ppid;
    private NativeProcess process;
    private Integer lastSysCpuTimes;
    private Integer lastProcCpuTimes;
    private Integer exitcode;

    public Process(Integer pid) throws NotSupportPlatformException {
        this.process = NativeManager.getProcess(pid);
        if (pid == null) {
            this.pid = this.process.getPid();
        } else {
            this.pid = pid;
        }
    }

    public Integer parent() {
        return null;
    }

    public List<Integer> parents() {
        return null;
    }

    public Integer nice(Integer value) {
        return null;
    }

    public void terminate() {
    }

    public void kill() {
    }

    public Integer waitProcess() {
        return null;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPpid() {
        return this.process.getPpid();
    }

}
