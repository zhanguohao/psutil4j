package org.psutil4j.utils;

import com.sun.jna.Platform;
import lombok.extern.log4j.Log4j2;
import org.psutil4j.common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Executing on the command line and returning the result of execution.
 *
 * @author zhangguohao
 */
@Log4j2
public final class CommandUtils {

    private static final String[] DEFAULT_ENV = getDefaultEnv();

    private CommandUtils() {
    }

    private static String[] getDefaultEnv() {
        if (Platform.isWindows()) {
            return new String[]{"LANGUAGE=C"};
        } else {
            return new String[]{"LC_ALL=C"};
        }
    }

    public static List<String> runNative(String cmdToRun) {
        String[] cmd = cmdToRun.split(Constants.SYMBOL_SPACING);
        return runNative(cmd);
    }

    public static List<String> runNative(String[] cmdToRunWithArgs) {
        return runNative(cmdToRunWithArgs, DEFAULT_ENV);
    }

    public static List<String> runNative(String[] cmdToRunWithArgs, String[] envp) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmdToRunWithArgs, envp);
        } catch (SecurityException | IOException e) {
            log.trace("Couldn't run command {}: {}", Arrays.toString(cmdToRunWithArgs), e.getMessage());
            return new ArrayList<>(0);
        }

        ArrayList<String> sa = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(p.getInputStream(), Charset.defaultCharset()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sa.add(line);
            }
            p.waitFor();
        } catch (IOException e) {
            log.trace("Problem reading output from {}: {}", Arrays.toString(cmdToRunWithArgs), e.getMessage());
            return new ArrayList<>(0);
        } catch (InterruptedException ie) {
            log.trace("Interrupted while reading output from {}: {}", Arrays.toString(cmdToRunWithArgs),
                ie.getMessage());
            Thread.currentThread().interrupt();
        }
        return sa;
    }

    public static String getFirstAnswer(String cmd2launch) {
        return getAnswerAt(cmd2launch, 0);
    }

    public static String getAnswerAt(String cmd2launch, int answerIdx) {
        List<String> sa = CommandUtils.runNative(cmd2launch);

        if (answerIdx >= 0 && answerIdx < sa.size()) {
            return sa.get(answerIdx);
        }
        return "";
    }
}
