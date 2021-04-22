package org.psutil4j.core;

import com.sun.jna.Platform;
import org.psutil4j.common.Constants;
import org.psutil4j.core.arch.NativePlatform;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.exception.NotSupportPlatformException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangguohao
 */
public class NativeManager {

    public static Map<Integer, NativePlatform> nativePlatforms = new HashMap<>();

    public static NativePlatform getPlatform() throws NotSupportPlatformException {
        if (nativePlatforms.containsKey(Platform.getOSType())) {
            return nativePlatforms.get(Platform.getOSType()).getInstance();
        } else {
            throw new NotSupportPlatformException(String.format("Platform: %s is not support now.", System.getProperty(Constants.OS_NAME)));
        }
    }

    public static NativePlatform getPlatform(Integer osType) {
        if (nativePlatforms.containsKey(Platform.getOSType())) {
            return nativePlatforms.get(Platform.getOSType()).getInstance();
        }
        return null;
    }

    public static NativeProcess getProcess(Integer pid) throws NotSupportPlatformException {
        return getPlatform().getProcess(pid);
    }

    public static void register(Integer platformId, NativePlatform nativePlatform) {
        nativePlatforms.put(platformId, nativePlatform);
    }

    static {
        Reflections reflections = new Reflections(
            new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("org.psutil4j.core.arch"))
                .setScanners(new SubTypesScanner())
        );
        for (Class<?> c : reflections.getSubTypesOf(NativePlatform.class)) {
            try {
                Object co = c.newInstance();
                Method getOsType = c.getMethod("getOsType");
                Method getInstance = c.getMethod("getInstance");
                register((Integer) getOsType.invoke(co), (NativePlatform) getInstance.invoke(co));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
