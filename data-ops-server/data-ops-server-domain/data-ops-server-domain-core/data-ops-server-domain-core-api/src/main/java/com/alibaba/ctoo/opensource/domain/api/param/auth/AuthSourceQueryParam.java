package com.alibaba.ctoo.opensource.domain.api.param.auth;

import java.util.List;

import com.alibaba.easytools.base.wrapper.param.QueryParam;

import lombok.Data;

/**
 * 权限资源查询参数
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
@Data
public class AuthSourceQueryParam extends QueryParam {
    /**
     * id列表
     */
    private List<Long> idList;

    /**
     * 父资源id
     */
    private Long parentId;

    /**
     * 资源枚举
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.AuthSourceLevelEnum
     */
    private String sourceLevel;
}
