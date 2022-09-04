package com.alibaba.ctoo.opensource.domain.api.param.project;

import java.util.Date;
import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.enums.InDataEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增/更新项目参数类
 *
 * @author 知闰
 * @date 2022/03/22
 */
@Data
public class ProjectCreateOrUpdateParam {

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
     * git类型
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum
     */
    @Getter
    @Setter
    private String gitType;

    /**
     * 刷新时间
     */
    private Date gmtRefresh;

    /**
     * 是否加入主页数据统计
     *
     * @see InDataEnum
     */
    private String inData;

    /**
     * logo
     */
    private String logoUrl;

    /**
     * 是否允许更新
     * true - 允许更新
     * false - 不允许
     */
    private Boolean allowUpdateTag;

    /**
     * 所属组织
     */
    private String organization;

}
