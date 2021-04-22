package org.psutil4j.core.arch.linux;

import org.psutil4j.common.Constants;
import org.psutil4j.common.enums.State;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.core.jna.NativeProcessOperation;
import org.psutil4j.core.pojo.PriorityUserId;
import org.psutil4j.utils.CommonUtils;
import org.psutil4j.utils.ParseUtils;
import org.psutil4j.utils.StringUtils;
import org.psutil4j.utils.tuples.Quartet;

import java.io.File;
import java.nio.file.Files;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangguohao
 */
public class LinuxProcess implements NativeProcess {

    private Integer pid;
    private String name;
    private Integer ppid;

    public LinuxProcess(Integer pid) {
        if (pid == null || pid == 0) {
            this.pid = NativeProcessOperation.getPid();
        } else {
            this.pid = pid;
        }
    }

    @Override
    public String getName() {
        return getStat().getB();
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
        return null;
    }

    @Override
    public Map<String, String> getMemoryInfo() {
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
        String stateValue = getStat().getC() != null ? getStat().getC() : "";
        return getState(stateValue);
    }

    @Override
    public Integer getPid() {
        return this.pid;
    }

    @Override
    public Integer getPpid() {
        return getStat().getD().get(ProcessStatEnum.PPID).intValue();
    }

    @Override
    public PriorityUserId getUids() {
        return null;
    }

    @Override
    public PriorityUserId getGids() {
        return null;
    }

    /**
     * Parse /proc/{pid}/stat file
     * <p>
     * 1045 (rsyslogd) S 1 1045 1045 0 -1 1077944576 49769 0 14 0 1216 634 0 0 20 0 3 0 1023 328638464 3987 18446744073709551615 1 1 0 0 0 0 0 16781830 1132545 18446744073709551615 0 0 17 0 0 0 4 0 0 0 0 0 0 0 0 0 0
     *
     * @return Quartet<pid, name, state, EnumMap < ProcessStatEnum, Long>>
     */
    public Quartet<Integer, String, String, EnumMap<ProcessStatEnum, Long>> getStat() {
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
            EnumMap<ProcessStatEnum, Long> statEnumMap = new EnumMap<ProcessStatEnum, Long>(ProcessStatEnum.class);
            ProcessStatEnum[] enumArray = ProcessStatEnum.class.getEnumConstants();
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
