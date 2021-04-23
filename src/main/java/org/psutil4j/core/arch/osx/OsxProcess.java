package org.psutil4j.core.arch.osx;

import org.psutil4j.common.enums.State;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.core.jna.NativeProcessOperation;
import org.psutil4j.core.pojo.PriorityUserId;

import java.util.EnumMap;
import java.util.List;

/**
 * @author zhangguohao
 */
public class OsxProcess implements NativeProcess {

    private final OsxPlatform platform = new OsxPlatform();

    private Integer pid;

    public OsxProcess(Integer pid) {
        if (pid == null || pid == 0) {
            this.pid = NativeProcessOperation.getPid();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getExe() {
        return null;
    }

    @Override
    public String[] getCmdLine() {
        return null;
    }

    @Override
    public Integer getStartTime() {
        return null;
    }

    @Override
    public EnumMap<?, Integer> getMemoryInfo() {
        return null;
    }

    @Override
    public Integer getNice() {
        return null;
    }

    @Override
    public Integer setNice(Integer value) {
        return null;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public Integer getPid() {
        return this.pid;
    }

    @Override
    public Integer getPpid() {
        return null;
    }

    @Override
    public PriorityUserId getUids() {
        return null;
    }

    @Override
    public PriorityUserId getGids() {
        return null;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public List<Integer> getThreadIds() {
        return null;
    }
}
