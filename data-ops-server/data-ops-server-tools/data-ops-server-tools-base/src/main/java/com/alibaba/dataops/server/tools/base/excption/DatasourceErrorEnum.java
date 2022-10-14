package com.alibaba.dataops.server.tools.base.excption;

import com.alibaba.dataops.server.tools.base.enums.BaseErrorEnum;

import lombok.Getter;

/**
 * @author moji
 * @version DatasourceErrorEnum.java, v 0.1 2022年10月10日 14:32 moji Exp $
 * @date 2022/10/10
 */
@Getter
public enum DatasourceErrorEnum implements BaseErrorEnum {

    /**
     * 数据源测试连接错误
     */
    DATASOURCE_TEST_ERROR("数据源测试连接错误"),

    /**
     * 数据源连接错误
     */
    DATASOURCE_CONNECT_ERROR("数据源连接错误"),

    /**
     * 控制台链接错误
     */
    CONSOLE_CONNECT_ERROR("控制台链接错误"),

    ;

    DatasourceErrorEnum(String desctiption) {
        this.description = desctiption;
    }

    final String description;

    @Override
    public String getCode() {
        return this.name();
    }
}
