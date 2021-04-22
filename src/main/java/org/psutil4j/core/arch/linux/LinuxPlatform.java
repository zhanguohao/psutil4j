package org.psutil4j.core.arch.linux;

import com.sun.jna.Platform;
import org.psutil4j.common.Constants;
import org.psutil4j.core.arch.NativePlatform;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.utils.CommonUtils;

import java.io.File;
import java.util.*;

/**
 * for linux
 *
 * @author zhangguohao
 */
public class LinuxPlatform implements NativePlatform {


    private static class LinuxPlatformHandler {
        private static final LinuxPlatform INSTANCE = new LinuxPlatform();
    }

    @Override
    public LinuxPlatform getInstance() {
        return LinuxPlatformHandler.INSTANCE;
    }

    @Override
    public Integer getOsType() {
        return Platform.LINUX;
    }

    @Override
    public NativeProcess getProcess(Integer pid) {
        return new LinuxProcess(pid);
    }

    @Override
    public List<Integer> getPids() {
        List<Integer> pids = new ArrayList<>();
        File file = new File(ProcPath.PROC);
        if (!file.exists() || Objects.requireNonNull(file.listFiles()).length <= 0) {
            return pids;
        }
        for (File f : Objects.requireNonNull(file.listFiles(procFile -> procFile.isDirectory() && CommonUtils.isInteger(procFile.getName())))) {
            pids.add(Integer.parseInt(f.getName()));
        }
        return pids;
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
            ppidMap.put(pid, linuxProcess.getPpid());
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
}
