package com.alibaba.ctoo.opensource.domain.api.model;

import com.alibaba.ctoo.opensource.domain.api.enums.ProjectTagTypeEnum;

import lombok.Data;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Data
public class TagDTO {

    /**
     * tag 主键ID
     */
    private Long id;

    /**
     * tag分类
     * @see ProjectTagTypeEnum
     */
    private String tagType;

    /**
     * tag 的code值
     */
    private String tagCode;

    /**
     * tag值
     */
    private String tagValue;

    /**
     * tag图标
     */
    private String tagIcon;


}
