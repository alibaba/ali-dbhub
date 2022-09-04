package com.alibaba.ctoo.opensource.domain.api.param.project;

import java.util.List;

import com.alibaba.easytools.base.wrapper.param.QueryParam;

import lombok.Data;

/**
 * 项目查询参数
 * @author 知闰
 * @date 2022/03/22
 */
@Data
public class ProjectQueryParam extends QueryParam {

    /**
     * id集合
     */
    private List<Long> ids;

    /**
     * 是否展示
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ShowEnum
     */
    private String inShow;
}
