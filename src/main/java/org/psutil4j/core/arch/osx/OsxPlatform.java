package org.psutil4j.core.arch.osx;

import com.sun.jna.Platform;
import org.psutil4j.core.arch.NativePlatform;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.utils.CommandUtils;
import org.psutil4j.utils.ParseUtils;

import java.util.List;
import java.util.Map;

/**
 * for macos
 *
 * @author zhangguohao
 */
public class OsxPlatform implements NativePlatform {

    /**
     * Jiffies per second, used for process time counters.
     */
    public static final int USER_HZ = ParseUtils.parseIntOrDefault(CommandUtils.getFirstAnswer("getconf CLK_TCK"), 100);

    private static class OsxPlatformHandler {
        private static final OsxPlatform INSTANCE = new OsxPlatform();
    }

    public static OsxPlatform getInstance() {
        return OsxPlatformHandler.INSTANCE;
    }

    public static Integer getOsType() {
        return Platform.MAC;
    }

    @Override
    public NativeProcess getProcess(Integer pid) {
        return new OsxProcess(pid);
    }

    @Override
    public List<Integer> getPids() {
        return null;
    }

    @Override
    public boolean pidExists(Integer pid) {
        return false;
    }

    @Override
    public Map<Integer, Integer> getPpidMap() {
        return null;
    }

    @Override
    public Integer getBootTime() {
        return null;
    }
}
