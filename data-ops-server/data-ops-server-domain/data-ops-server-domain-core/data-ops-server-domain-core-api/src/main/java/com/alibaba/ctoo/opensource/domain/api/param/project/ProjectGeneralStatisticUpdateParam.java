package com.alibaba.ctoo.opensource.domain.api.param.project;

import lombok.Data;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Data
public class ProjectGeneralStatisticUpdateParam {

    /**
     * 数据主键ID
     */
    private Long id;
    /**
     *   点赞数
     */
    private Integer starCount;

    /**
     *   分支数量
     */
    private Integer forkCount;

    /**
     * 项目数量
     */
    private Integer projectCount;
}
