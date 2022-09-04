package com.alibaba.ctoo.opensource.domain.api.param.project;

import lombok.Data;

/**
 * 项目刷新参数
 * @author 知闰
 * @date 2022/03/29
 */
@Data
public class RefreshProjectParam {

    /**
     * 项目ID
     */
    private Long id;



    /**
     *   完整项目名
     */
    private String fullName;


    /**
     * git类型
     * @see com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum
     */
    private String gitType;
}
