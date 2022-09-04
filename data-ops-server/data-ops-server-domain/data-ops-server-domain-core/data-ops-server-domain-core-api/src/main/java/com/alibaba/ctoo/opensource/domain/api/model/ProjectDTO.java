package com.alibaba.ctoo.opensource.domain.api.model;

import java.util.Date;
import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.enums.ShowEnum;

import lombok.Data;

/**
 * project查询结果dto
 *
 * @author 知闰
 * @date 2022/03/22
 */
@Data
public class ProjectDTO {

    /**
     * 数据主键
     */
    private Long id;

    /**
     * 项目展示名称
     */
    private String showName;
    /**
     * 项目名
     */
    private String name;

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
     * github
     */
    private String githubUrl;

    /**
     * gitee
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
    private TagDTO languageTag;

    /**
     * 领域标签
     */
    private List<TagDTO> domainTagList;

    /**
     * 项目基础标签
     */
    private List<TagDTO> basisTagList;

    /**
     * logo地址
     */
    private String logoUrl;

    /**
     * 刷新时间
     */
    private Date gmtRefresh;

    /**
     * 是否展示
     *
     * @see ShowEnum
     */
    private String inShow;

    /**
     * git类型
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum
     */
    private String gitType;

    /**
     * 是否加入主页数据展示
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.InDataEnum
     */
    private String inData;

    /**
     *   所属组织
     */

    private String organization;

}
