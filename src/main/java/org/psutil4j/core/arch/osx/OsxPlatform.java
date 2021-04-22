package org.psutil4j.core.arch.osx;

import com.sun.jna.Platform;
import org.psutil4j.core.arch.NativePlatform;
import org.psutil4j.core.arch.NativeProcess;

import java.util.List;
import java.util.Map;

/**
 * for macos
 *
 * @author zhangguohao
 */
public class OsxPlatform implements NativePlatform {

    private static class OsxPlatformHandler {
        private static final OsxPlatform INSTANCE = new OsxPlatform();
    }

    @Override
    public OsxPlatform getInstance() {
        return OsxPlatformHandler.INSTANCE;
    }

    @Override
    public Integer getOsType() {
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
