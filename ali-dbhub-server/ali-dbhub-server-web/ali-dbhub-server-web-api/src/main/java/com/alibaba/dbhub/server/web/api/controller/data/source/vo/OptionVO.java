package com.alibaba.dbhub.server.web.api.controller.data.source.vo;

import lombok.Data;

import java.util.List;

@Data
public class OptionVO {

    /**
     * 下拉内容
     */
    private String cnValue;

    /**
     * 下拉内容
     */
    private String enValue;

    /**
     * 是否默认选中
     */
    private boolean selected;

    /**
     * 如何关联级联展示的表单
     */
    private List<FormItemVO> items;
}
