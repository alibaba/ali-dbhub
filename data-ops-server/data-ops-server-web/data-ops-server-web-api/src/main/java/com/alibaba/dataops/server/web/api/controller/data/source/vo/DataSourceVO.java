package com.alibaba.dataops.server.web.api.controller.data.source.vo;

import lombok.Data;

/**
 * @author moji
 * @version ConnectionVO.java, v 0.1 2022年09月16日 14:15 moji Exp $
 * @date 2022/09/16
 */
@Data
public class DataSourceVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 连接别名
     */
    private String alias;

    /**
     * 连接地址
     */
    private String url;

    /**
     * 连接用户
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接类型
     */
    private String type;

    /**
     * 环境类型
     */
    private String envType;
}
