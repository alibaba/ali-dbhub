package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * 项目数据统计结果
 *
 * @author 知闰
 * @date 2022/03/23
 */
@Data
public class ProjectGeneralStatisticDTO {

    /**
     * 数据主键ID
     */
    private Long id;

    /**
     * 点赞数
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
     * 关联的项目数量
     */
    private Integer projectCount;
}
