package com.alibaba.ctoo.opensource.domain.repository.dataobject.search;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 *
 * 自定义项目查询参数
 * @author 知闰
 * @date 2022/03/24
 */
@Data
public class CustomProjectSearchParam extends BaseParam{

    /**
     * 展示名
     */
    private String showName;

    /**
     * 刷新时间 大于该值
     */
    private Date gtRefreshTime;

    /**
     * 刷新时间 小于该值
     */
    private Date ltRefreshTime;

    /**
     * 是否展示
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ShowEnum
     */
    private String inShow;

    /**
     * 文档url不为空
     */
    private Boolean documentUrlIsNotNull;

    /**
     * 语言标签列表
     */
    private List<Long> languageTagIdList;


    /**
     * 领域标签列表
     */
    private List<Long> domainTagIdList;


    /**
     * 基础标签列表
     */
    private List<Long> basisTagIdList;

}
