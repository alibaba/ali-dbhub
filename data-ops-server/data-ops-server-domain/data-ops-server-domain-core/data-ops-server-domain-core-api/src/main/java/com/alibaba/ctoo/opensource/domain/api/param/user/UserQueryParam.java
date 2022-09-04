package com.alibaba.ctoo.opensource.domain.api.param.user;

import java.util.List;

import com.alibaba.easytools.base.wrapper.param.QueryParam;

import lombok.Data;

/**
 * @author qiuyuyu
 * @date 2022/03/04
 */
@Data
public class UserQueryParam extends QueryParam {
    /**
     * 主键
     */
    private Long id;

    /**
     * 主键
     */
    private List<Long> idList;

    /**
     * 用户id cms那边user_id 单独拎出来的 所以没办法
     */
    private String userId;

    /**
     * 用户id cms那边user_id 单独拎出来的 所以没办法
     */
    private List<String> userIdList;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色编码
     */
    private List<String> rolCodeList;
}
