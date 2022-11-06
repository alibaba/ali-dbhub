package com.alibaba.dataops.server.domain.data.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据库连接对象
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class DataSourceConnectDTO {

    /**
     * 是否成功标志位
     */
    private Boolean success;

    /**
     * 失败消息提示
     * 只有失败的情况下会有
     */
    private String message;

    /**
     * 描述
     */
    private String description;
}
