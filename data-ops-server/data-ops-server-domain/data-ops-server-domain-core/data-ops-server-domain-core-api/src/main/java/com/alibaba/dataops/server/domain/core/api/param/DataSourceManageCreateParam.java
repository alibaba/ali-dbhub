package com.alibaba.dataops.server.domain.core.api.param;

import lombok.Data;

/**
 * @author moji
 * @version DataSourceCreateParam.java, v 0.1 2022年09月23日 15:23 moji Exp $
 * @date 2022/09/23
 */
@Data
public class DataSourceManageCreateParam {

    /**
     * 别名
     */
    private String alias;

    /**
     * 连接地址
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库类型
     */
    private String type;

    /**
     * 环境类型
     */
    private String envType;

}
