package com.alibaba.ctoo.opensource.domain.api.service.user;

import java.util.List;
import java.util.Map;

import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserSelector;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserUpdateParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * 用户服务查询/更新
 *
 * @author qiuyuyu
 * @date 2022/03/03
 */
public interface UserDomainService {

    /**
     * 查询单个用户信息，找不到会抛出异常
     *
     * @param userId
     * @param selector
     * @return
     */
    DataResult<UserDTO> get(String userId, UserSelector selector);

    /**
     * 查询单个用户信息
     *
     * @param userId
     * @param selector
     * @return
     */
    DataResult<UserDTO> query(String userId, UserSelector selector);

    /**
     * 批量查询用户信息
     *
     * @param userIdList
     * @param selector
     * @return
     */
    ListResult<UserDTO> queryList(List<String> userIdList, UserSelector selector);

    /**
     * 批量查询用户信息
     *
     * @param param
     * @param selector
     * @return
     */
    ListResult<UserDTO> queryList(UserQueryParam param, UserSelector selector);

    /**
     * 创建用户
     *
     * @param param
     * @return
     */
    DataResult<String> create(UserCreateParam param);

    /**
     * 更新用户
     *
     * @param param
     * @return
     */
    ActionResult update(UserUpdateParam param);

    /**
     * 批量获取用户信息.
     *
     * @param userIdList userIdList
     * @param selector   selector
     * @return 不可能返回为空 至少是个空map
     */
    DataResult<Map<String, UserDTO>> queryMap(List<String> userIdList, UserSelector selector);

    /**
     * 查询登陆用户信息，缓存10分钟清除
     *
     * @param userId
     * @return
     */
    DataResult<UserDTO> loginUser(String userId);

    /**
     * 修改用户最近登陆时间
     * @param userId
     * @return
     */
    ActionResult updateGmtLastLogin(String userId);
}
