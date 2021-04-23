package org.psutil4j.core;

import org.psutil4j.core.arch.NativePlatform;
import org.psutil4j.core.arch.NativeProcess;
import org.psutil4j.exception.NotSupportPlatformException;

import java.util.*;

/**
 * Represents an OS process with the given PID.
 *
 * <p>
 * The only exceptions for which process identity is pre-emptively
 * checked and guaranteed are:
 * <p>
 * - parent()
 * - children()
 * - nice() (set)
 * - terminate()
 * - kill()
 *
 * @author zhangguohao
 */
public class Process {

    private Integer pid;
    private final NativeProcess process;
    private final NativePlatform nativePlatform = NativeManager.getPlatform();

    public Process(Integer pid) throws NotSupportPlatformException {
        this.process = NativeManager.getProcess(pid);
        if (pid == null) {
            this.pid = this.process.getPid();
        } else {
            this.pid = pid;
        }
        Integer ppid = this.process.getPpid();
        String exe = this.process.getExe();
        String name = this.process.getName();
        String[] cmdLine = this.process.getCmdLine();
        // timestamp
        Integer startTime = this.process.getStartTime();
    }

    public Integer parent() {
        return this.process.getPpid();
    }

    public List<Integer> parents() {
        List<Integer> parents = new ArrayList<>();
        Map<Integer, Integer> ppidMap = nativePlatform.getPpidMap();
        Integer currentPid = this.pid;
        while (ppidMap.containsKey(currentPid)) {
            parents.add(ppidMap.get(currentPid));
            currentPid = ppidMap.get(currentPid);
        }
        return parents;
    }

    /**
     * Get children pids
     * A ─┐
     * │
     * ├─ B (child) ─┐
     * │             └─ E (grandchild) ─┐
     * │                                └─ F (great grandchild)
     * ├─ C (child)
     * └─ D (child)
     *
     * @param recursive recursive or not
     * @return pids
     */
    public List<Integer> children(boolean recursive) {
        List<Integer> children = new ArrayList<>();
        Map<Integer, Integer> ppidMap = nativePlatform.getPpidMap();
        Map<Integer, List<Integer>> childrenPpidMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : ppidMap.entrySet()) {
            List<Integer> childrenValue = childrenPpidMap.get(entry.getValue()) != null ? childrenPpidMap.get(entry.getValue()) : new ArrayList<>();
            childrenValue.add(entry.getKey());
            childrenPpidMap.put(entry.getValue(), childrenValue);
        }
        if (!recursive) {
            if (childrenPpidMap.containsKey(this.pid)) {
                return childrenPpidMap.get(this.pid);
            }
            return children;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(this.pid);
        while (!queue.isEmpty()) {
            Integer currentPid = queue.poll();
            if (childrenPpidMap.containsKey(currentPid)) {
                children.addAll(childrenPpidMap.get(currentPid));
                queue.addAll(childrenPpidMap.get(currentPid));
            }
        }
        return children;
    }

    public Integer nice(Integer value) {
        if (this.process.getNice().equals(value)) {
            return value;
        }
        if (this.process.setNice(value) == 0) {
            return value;
        }
        return this.process.getNice();
    }

    public boolean terminate() {
        return nativePlatform.terminate(this.pid);

    }

    public boolean kill() {
        return nativePlatform.kill(this.pid);
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPpid() {
        return this.process.getPpid();
    }

    public boolean isRunning() {
        return this.process.isRunning();
    }

}
