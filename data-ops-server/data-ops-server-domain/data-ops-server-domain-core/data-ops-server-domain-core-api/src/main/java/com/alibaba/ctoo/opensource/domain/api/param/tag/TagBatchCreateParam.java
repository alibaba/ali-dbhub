package com.alibaba.ctoo.opensource.domain.api.param.tag;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.enums.ProjectTagTypeEnum;

import lombok.Data;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Data
public class TagBatchCreateParam {

    /**
     * 标签值
     */
    private List<String> tagValues;

    /**
     * 标签类型
     * @see ProjectTagTypeEnum
     */
    private ProjectTagTypeEnum tagTypeEnum;
}
