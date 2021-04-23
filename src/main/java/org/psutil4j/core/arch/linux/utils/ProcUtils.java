package org.psutil4j.core.arch.linux.utils;

import org.psutil4j.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * for linux /proc
 */
public class ProcUtils {

    /**
     * get ids from proc path
     *
     * @param path proc path
     * @return process ids or thread ids
     */
    public static List<Integer> getProcessIds(String path) {
        List<Integer> ids = new ArrayList<>();
        File file = new File(path);
        if (!file.exists() || Objects.requireNonNull(file.listFiles()).length <= 0) {
            return ids;
        }
        for (File f : Objects.requireNonNull(file.listFiles(procFile -> procFile.isDirectory() && CommonUtils.isInteger(procFile.getName())))) {
            ids.add(Integer.parseInt(f.getName()));
        }
        return ids;
    }
}
