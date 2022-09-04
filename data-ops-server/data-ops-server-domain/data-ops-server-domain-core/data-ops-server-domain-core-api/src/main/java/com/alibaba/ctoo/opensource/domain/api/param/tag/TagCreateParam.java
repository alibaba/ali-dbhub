package com.alibaba.ctoo.opensource.domain.api.param.tag;

import com.alibaba.ctoo.opensource.domain.api.enums.ProjectTagTypeEnum;

import lombok.Data;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Data
public class TagCreateParam {

    /**
     * 标签值
     */
    private String tagValue;

    /**
     * 标签类型
     * @see ProjectTagTypeEnum
     */
    private ProjectTagTypeEnum tagTypeEnum;


}
