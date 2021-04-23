package org.psutil4j.core.arch.linux;

import org.psutil4j.common.Constants;
import org.psutil4j.common.enums.Signal;
import org.psutil4j.common.enums.State;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.core.arch.linux.enums.LinuxMemoryInfoEnum;
import org.psutil4j.core.arch.linux.enums.LinuxProcessStatEnum;
import org.psutil4j.core.arch.linux.utils.ProcUtils;
import org.psutil4j.core.jna.NativeProcessOperation;
import org.psutil4j.core.jna.NativeSignalProcess;
import org.psutil4j.core.pojo.PriorityUserId;
import org.psutil4j.utils.CommonUtils;
import org.psutil4j.utils.ParseUtils;
import org.psutil4j.utils.StringUtils;
import org.psutil4j.utils.tuples.Quartet;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * @author zhangguohao
 */
public class LinuxProcess implements NativeProcess {

    private static final LinuxPlatform linuxPlatform = LinuxPlatform.getInstance();

    private final Integer pid;

    public LinuxProcess(Integer pid) {
        if (pid == null || pid == 0) {
            this.pid = NativeProcessOperation.getPid();
        } else {
            this.pid = pid;
        }
    }

    @Override
    public String getName() {
        Quartet<Integer, String, String, EnumMap<LinuxProcessStatEnum, Long>> stat = getStat();
        if (stat != null) {
            return stat.getB();
        }
        return null;
    }

    @Override
    public String getExe() {
        String exePath = String.format(ProcPath.PID_EXE, this.getPid());
        File exeFile = new File(exePath);
        try {
            return Files.readSymbolicLink(exeFile.toPath()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String[] getCmdLine() {
        String cmdLinePath = String.format(ProcPath.PID_CMDLINE, this.getPid());
        File cmdLineFile = new File(cmdLinePath);
        if (!cmdLineFile.exists()) {
            return null;
        }
        List<String> content = CommonUtils.getFileContentWithString(cmdLinePath);
        if (content == null || content.size() < 1) {
            return null;
        }
        return content.get(0).split("\u0000");
    }

    @Override
    public Integer getStartTime() {
        Integer bootTime = linuxPlatform.getBootTime();
        return (int) (getStat().getD().get(LinuxProcessStatEnum.STARTTIME) / LinuxPlatform.USER_HZ) + bootTime;
    }

    @Override
    public EnumMap<LinuxMemoryInfoEnum, Integer> getMemoryInfo() {
        EnumMap<LinuxMemoryInfoEnum, Integer> memInfo = new EnumMap<>(LinuxMemoryInfoEnum.class);
        String statmPath = String.format(ProcPath.PID_STATM, this.pid);
        List<String> lineList = CommonUtils.getFileContentWithString(statmPath);
        if (lineList == null || lineList.size() <= 0) {
            return null;
        }
        String statmStr = lineList.get(0);
        LinuxMemoryInfoEnum[] enumArray = LinuxMemoryInfoEnum.class.getEnumConstants();
        String[] fields = statmStr.split(Constants.SYMBOL_SPACING);
        for (int i = 0; i < enumArray.length && i < fields.length; i++) {
            memInfo.put(enumArray[i], ParseUtils.parseIntOrDefault(fields[i], 0));
        }
        return memInfo;
    }

    @Override
    public Integer getNice() {
        return NativeProcessOperation.getPriority(this.pid);
    }

    @Override
    public Integer setNice(Integer value) {
        return NativeProcessOperation.setPriority(this.pid, value);
    }

    @Override
    public State getState() {
        Quartet<Integer, String, String, EnumMap<LinuxProcessStatEnum, Long>> stat = getStat();
        if (stat != null) {
            String stateValue = stat.getC() != null ? stat.getC() : "";
            return getState(stateValue);
        }
        return State.OTHER;
    }

    @Override
    public Integer getPid() {
        return this.pid;
    }

    @Override
    public Integer getPpid() {
        Quartet<Integer, String, String, EnumMap<LinuxProcessStatEnum, Long>> stat = getStat();
        if (stat != null) {
            return stat.getD().get(LinuxProcessStatEnum.PPID).intValue();
        }
        return null;
    }

    @Override
    public PriorityUserId getUids() {
        String uidsPrefix = "Uid:";
        return getPriorityUserId(uidsPrefix);
    }

    @Override
    public PriorityUserId getGids() {
        String gidsPrefix = "Gid:";
        return getPriorityUserId(gidsPrefix);
    }

    @Override
    public boolean isRunning() {
        return NativeSignalProcess.kill(this.pid, Signal.SIGHUP.getCode()) == 0;
    }

    @Override
    public List<Integer> getThreadIds() {
        if (!isRunning()) {
            return new ArrayList<>();
        }
        return ProcUtils.getProcessIds(String.format(ProcPath.TASK_PATH, this.pid));
    }

    /**
     * Parse /proc/{pid}/status prefix with Uid: or Gid:
     *
     * @param prefix prefix link Uid:  Gid:
     * @return PriorityUserId
     */
    private PriorityUserId getPriorityUserId(String prefix) {
        PriorityUserId priorityUserId = new PriorityUserId();
        String statusPath = String.format(ProcPath.PID_STATUS, this.pid);
        List<String> lineList = CommonUtils.getFileContentWithString(statusPath);
        if (lineList == null || lineList.size() <= 0) {
            return null;
        }
        for (String line : lineList) {
            if (line.startsWith(prefix)) {
                String[] uids = line.substring(prefix.length() + 1).split(Constants.SYMBOL_HORIZONTAL_TAB);
                if (uids.length >= 3) {
                    priorityUserId.setRealUserId(Integer.parseInt(uids[0]));
                    priorityUserId.setEffectiveUserId(Integer.parseInt(uids[1]));
                    priorityUserId.setSavedSetUserId(Integer.parseInt(uids[2]));
                    return priorityUserId;
                }
            }
        }
        return null;
    }

    /**
     * Parse /proc/{pid}/stat file
     * <p>
     * 1045 (rsyslogd) S 1 1045 1045 0 -1 1077944576 49769 0 14 0 1216 634 0 0 20 0 3 0 1023 328638464 3987 18446744073709551615 1 1 0 0 0 0 0 16781830 1132545 18446744073709551615 0 0 17 0 0 0 4 0 0 0 0 0 0 0 0 0 0
     *
     * @return Quartet<pid, name, state, EnumMap < ProcessStatEnum, Long>>
     */
    public Quartet<Integer, String, String, EnumMap<LinuxProcessStatEnum, Long>> getStat() {
        String statPath = String.format(ProcPath.PID_STAT, this.getPid());
        List<String> lineList = CommonUtils.getFileContentWithString(statPath);
        if (lineList == null || lineList.size() <= 0) {
            return null;
        }
        String statStr = lineList.get(0);
        if (StringUtils.isNotEmpty(statStr) && statStr.length() > 0) {
            int endOfName = statStr.lastIndexOf(Constants.SYMBOL_CLOSE_PARENTHESIS);
            String name = statStr.substring(statStr.indexOf(Constants.SYMBOL_OPEN_PARENTHESIS) + 1, endOfName);
            String[] fields = statStr.substring(endOfName + 2).split(Constants.SYMBOL_SPACING);
            String state = fields[0];
            EnumMap<LinuxProcessStatEnum, Long> statEnumMap = new EnumMap<>(LinuxProcessStatEnum.class);
            LinuxProcessStatEnum[] enumArray = LinuxProcessStatEnum.class.getEnumConstants();
            for (int i = 2; i < enumArray.length && i - 2 < fields.length; i++) {
                statEnumMap.put(enumArray[i], ParseUtils.parseLongOrDefault(fields[i - 2], 0L));
            }
            return new Quartet<>(this.getPid(), name, state, statEnumMap);
        }
        return null;
    }

    /***
     * Returns Enum STATE for the state value obtained from status file of any
     * process/thread.
     *
     * @param stateValue
     *            state value from the status file
     * @return State
     */
    public static State getState(String stateValue) {
        State state;
        switch (stateValue) {
            case "R":
                state = State.RUNNING;
                break;
            case "S":
                state = State.SLEEPING;
                break;
            case "D":
                state = State.WAITING;
                break;
            case "Z":
                state = State.ZOMBIE;
                break;
            case "X":
            case "x":
                state = State.DEAD;
                break;
            case "T":
                state = State.STOPPED;
                break;
            case "t":
                state = State.TRACING_STOP;
                break;
            default:
                state = State.OTHER;
                break;
        }
        return state;
    }

}
