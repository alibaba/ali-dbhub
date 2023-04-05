package com.alibaba.dbhub.server.web.api.controller.data.source.vo;

import lombok.Data;

import java.util.List;

@Data
public class FormItemVO {

    /**
     * 输入类型
     */
    private String inputType;

    /**
     * 参数名
     */
    private String name;

    /**
     * 表单参数展示的英文名
     */
    private String labelNameEN;

    /**
     * 表单参数展示的中文名
     */
    private String labelNameCN;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否必填
     */
    private boolean required;

    /**
     * 下拉列表
     */
    private List<OptionVO> selects;
}
