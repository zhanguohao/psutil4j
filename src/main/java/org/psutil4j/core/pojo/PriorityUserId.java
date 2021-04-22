package org.psutil4j.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangguohao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityUserId {

    private Integer realUserId;
    private Integer effectiveUserId;
    private Integer savedSetUserId;

}
