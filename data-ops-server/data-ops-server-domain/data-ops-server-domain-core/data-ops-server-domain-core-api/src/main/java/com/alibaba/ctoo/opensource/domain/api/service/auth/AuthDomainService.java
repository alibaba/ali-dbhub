package com.alibaba.ctoo.opensource.domain.api.service.auth;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.RoleAuthSourceDTO;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * @author qiuyuyu
 * @date 2022/03/22
 */
public interface AuthDomainService {

    /**
     * 获取角色权限
     *
     * @param roleCodeList
     * @return
     */
    ListResult<RoleAuthSourceDTO> queryByRoles(List<String> roleCodeList);

    /**
     * 校验权限
     *
     * @param roleCodeList
     * @param url
     * @return
     */
    DataResult<Boolean> checkAuth(List<String> roleCodeList, String url);

    /**
     * 移除所有授权缓存
     *
     * @return void
     */
    ActionResult removeAuthCache();
}
