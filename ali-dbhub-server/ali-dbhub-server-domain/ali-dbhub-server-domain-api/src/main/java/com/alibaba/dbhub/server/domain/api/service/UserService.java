package com.alibaba.dbhub.server.domain.api.service;

import com.alibaba.dbhub.server.domain.api.model.User;
import com.alibaba.dbhub.server.domain.support.param.user.UserQueryParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;

/**
 * 用户服务
 *
 * @author Jiaju Zhuang
 */
public interface UserService {

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    DataResult<User> query(Long id);

    /**
     * 查询用户信息
     *
     * @param param
     * @return
     */
    DataResult<User> query(UserQueryParam param);

}
