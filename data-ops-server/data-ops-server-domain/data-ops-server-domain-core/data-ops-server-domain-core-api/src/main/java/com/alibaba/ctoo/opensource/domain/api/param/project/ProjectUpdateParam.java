package com.alibaba.ctoo.opensource.domain.api.param.project;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 更新项目参数类
 *
 * @author 知闰
 * @date 2022/03/22
 */
@Data
public class ProjectUpdateParam {

    /**
     * 数据主键
     */
    private Long id;

    /**
     * 项目名
     */
    private String name;

    /**
     * 展示名
     */
    private String showName;

    /**
     * 完整项目名
     */
    private String fullName;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 点赞数量
     */
    private Integer starCount;

    /**
     * 分支数量
     */
    private Integer forkCount;

    /**
     * 贡献者数量
     */
    private Integer contributorCount;

    /**
     * 是否展示
     */
    private String inShow;

    /**
     * github
     */
    private String githubUrl;

    /**
     * gitee url
     */
    private String giteeUrl;

    /**
     * 文档地址
     */
    private String documentUrl;

    /**
     * 官方网站
     */
    private String officialUrl;

    /**
     * 项目语言
     */
    private String languageTag;

    /**
     * 领域标签值
     */
    private List<String> domainTag;

    /**
     * 项目基础标签
     */
    private List<String> basisTag;

    /**
     * 刷新时间
     */
    private Date gmtRefresh;

    /**
     * 是否加入主页数据展示
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.InDataEnum
     */
    private String inData;

    /**
     * 不更新
     */
    private Boolean allowUpdateTag;

}
