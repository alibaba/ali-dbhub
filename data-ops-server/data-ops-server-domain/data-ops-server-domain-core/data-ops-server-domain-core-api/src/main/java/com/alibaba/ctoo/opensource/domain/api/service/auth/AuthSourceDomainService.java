package com.alibaba.ctoo.opensource.domain.api.service.auth;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.AuthSourceDTO;
import com.alibaba.ctoo.opensource.domain.api.param.auth.AuthSourceQueryParam;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * 权限资源服务
 *
 * @author qiuyuyu
 * @date 2022/03/22
 */
public interface AuthSourceDomainService {

    /**
     * 角色权限
     *
     * @param parentIdList
     * @param systemType
     * @return
     */
    ListResult<AuthSourceDTO> queryByParentId(List<Long> parentIdList, String systemType);

    /**
     * 查询数据
     *
     * @param param
     * @return
     */
    ListResult<AuthSourceDTO> query(AuthSourceQueryParam param);

    /**
     * 查询ugc需要显示的前端资源
     *
     * @param roleCodeList
     * @return
     */
    ListResult<String> queryAdminFrontSourceByRoleList(List<String> roleCodeList);

}
