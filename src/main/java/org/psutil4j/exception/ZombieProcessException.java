package org.psutil4j.exception;

import org.psutil4j.utils.StringUtils;

/**
 * @author zhangguohao
 */
public class ZombieProcessException extends NoSuchProcessException {

    private Integer pid;
    private String name;
    private Integer ppid;
    private String message;

    public ZombieProcessException(Integer pid, String name, Integer ppid, String message) {
        super(message);
        this.pid = pid;
        this.name = name;
        this.ppid = ppid;
        this.message = message;
        if (StringUtils.isEmpty(this.message)) {
            StringBuilder sb = new StringBuilder();
            sb.append("process still exists but it's a zombie ");
            sb.append("(");
            sb.append(String.format("pid=%s ", pid.toString()));
            sb.append(String.format("ppid=%s ", ppid.toString()));
            sb.append(String.format("name=%s ", name));
            sb.append(")");
            this.message = sb.toString();
        }
    }
}
