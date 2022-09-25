package com.alibaba.dataops.server.domain.data.api.param.console;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 控制台关闭参数
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ConsoleCloseParam {

    /**
     * 控制台的id ，确保全局唯一
     */
    @NotNull
    private Long consoleId;
}
