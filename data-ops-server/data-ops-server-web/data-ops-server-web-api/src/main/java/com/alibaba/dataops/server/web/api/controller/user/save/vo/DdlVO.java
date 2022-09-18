package com.alibaba.dataops.server.web.api.controller.user.save.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author moji
 * @version DdlVO.java, v 0.1 2022年09月18日 11:06 moji Exp $
 * @date 2022/09/18
 */
@Data
public class DdlVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件别名
     */
    private String name;

    /**
     * 连接id
     */
    private Long connectionId;

    /**
     * DB名称
     */
    private String databaseName;

    /**
     * ddl语言类型
     * @see com.alibaba.dataops.server.tools.base.enums.ConnectionTypeEnum
     */
    private String type;
}
