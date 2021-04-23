package org.psutil4j.core.arch.linux;

import com.sun.jna.Platform;
import org.psutil4j.common.Constants;
import org.psutil4j.common.enums.Signal;
import org.psutil4j.core.arch.NativePlatform;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.core.arch.linux.utils.ProcUtils;
import org.psutil4j.core.jna.NativeSignalProcess;
import org.psutil4j.utils.CommandUtils;
import org.psutil4j.utils.CommonUtils;
import org.psutil4j.utils.ParseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * for linux
 *
 * @author zhangguohao
 */
public class LinuxPlatform implements NativePlatform {

    /**
     * Jiffies per second, used for process time counters.
     */
    public static final int USER_HZ = ParseUtils.parseIntOrDefault(CommandUtils.getFirstAnswer("getconf CLK_TCK"), 100);

    private static class LinuxPlatformHandler {
        private static final LinuxPlatform INSTANCE = new LinuxPlatform();
    }

    public static LinuxPlatform getInstance() {
        return LinuxPlatformHandler.INSTANCE;
    }

    public static Integer getOsType() {
        return Platform.LINUX;
    }

    @Override
    public NativeProcess getProcess(Integer pid) {
        return new LinuxProcess(pid);
    }

    @Override
    public List<Integer> getPids() {
        return ProcUtils.getProcessIds(ProcPath.PROC);
    }

    @Override
    public boolean pidExists(Integer pid) {
        return pid != null && getPids().contains(pid);
    }

    @Override
    public Map<Integer, Integer> getPpidMap() {
        Map<Integer, Integer> ppidMap = new HashMap<>(128);
        for (Integer pid : getPids()) {
            LinuxProcess linuxProcess = new LinuxProcess(pid);
            if (linuxProcess.getPpid() != null) {
                ppidMap.put(pid, linuxProcess.getPpid());
            }
        }
        return ppidMap;
    }

    @Override
    public Integer getBootTime() {
        List<String> stat = CommonUtils.getFileContentWithString(ProcPath.STAT);
        if (stat == null) {
            return null;
        }
        for (String line : stat) {
            if (line.startsWith("btime")) {
                return Integer.parseInt(line.split(Constants.SYMBOL_SPACING)[1]);
            }
        }
        return null;
    }

    @Override
    public boolean terminate(Integer pid) {
        return NativeSignalProcess.kill(pid, Signal.SIGTERM.getCode()) == 0;
    }

    @Override
    public boolean kill(Integer pid) {
        return NativeSignalProcess.kill(pid, Signal.SIGKILL.getCode()) == 0;
    }
}
