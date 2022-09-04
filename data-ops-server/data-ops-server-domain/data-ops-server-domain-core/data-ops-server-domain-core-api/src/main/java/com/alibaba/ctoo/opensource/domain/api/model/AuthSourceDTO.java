package com.alibaba.ctoo.opensource.domain.api.model;

import lombok.Data;

/**
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Data
public class AuthSourceDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 前端资源配置，目前存的是乐高的页面id
     */
    private String frontSource;

    /**
     * 资源名称
     */
    private String sourceName;

    /**
     * 后端资源的url 多个用逗号隔开。可以匹配* 号
     */
    private String url;

    /**
     * 排序字段 默认0
     */
    private Integer sourceOrder;

    /**
     * 父资源id
     */
    private Long parentId;

    /**
     * 资源等级 L0 - 总的资源 L1 - 一级菜单 L2 - 二级菜单 L3 - 按钮权限(暂无)
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.AuthSourceLevelEnum
     */
    private String sourceLevel;
}
