package com.alibaba.dbhub.server.web.api.controller.data.source.vo;

import lombok.Data;

import java.util.List;

@Data
public class DataSourceFormVO {

    /**
     * 数据库DB类型
     */
    private String type;

    /**
     * 表单包含的字段
     */
    private List<FormItemVO> items;

    /**
     * 数据库icon
     */
    private String icon;

}
