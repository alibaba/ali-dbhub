package com.alibaba.ctoo.opensource.domain.api.service.user;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.UserRoleDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleDeleteParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRolePageQueryFromSearchParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRolePageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserRoleSelector;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;

/**
 * @author qiuyuyu
 * @date 2022/03/22
 */
public interface UserRoleDomainService {
    /**
     * 分页查询
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<UserRoleDTO> queryPage(UserRolePageQueryParam param, UserRoleSelector selector);

    /**
     * 批量查询
     *
     * @param param
     * @param selector
     * @return
     */
    ListResult<UserRoleDTO> queryList(UserRoleQueryParam param, UserRoleSelector selector);

    /**
     * 批量查询 根据主键
     *
     * @param idList
     * @param selector
     * @return
     */
    ListResult<UserRoleDTO> queryList(List<Long> idList, UserRoleSelector selector);

    /**
     * 新增用户角色
     *
     * @param param
     * @return
     */
    ActionResult create(UserRoleCreateParam param);

    /**
     * 删除用户角色
     *
     * @param param
     * @return
     */
    ActionResult delete(UserRoleDeleteParam param);
}
