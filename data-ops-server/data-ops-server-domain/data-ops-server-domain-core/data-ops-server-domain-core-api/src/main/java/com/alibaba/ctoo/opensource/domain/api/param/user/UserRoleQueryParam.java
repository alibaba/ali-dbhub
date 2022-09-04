package com.alibaba.ctoo.opensource.domain.api.param.user;

import java.util.List;

import com.alibaba.easytools.base.wrapper.param.QueryParam;

import lombok.Data;

/**
 * 批量查询
 *
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Data
public class UserRoleQueryParam extends QueryParam {

    /**
     * 主键
     */
    private List<Long> idList;
    /**
     * 用户id
     */
    private List<String> userIdList;

    /**
     * 角色编码
     */
    private List<String> roleCodeList;
}
