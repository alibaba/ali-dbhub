package com.alibaba.ctoo.opensource.domain.api.service.user;

import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserSelector;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;

/**
 * 用户缓存服务
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
public interface UserCacheDomainService {

    /**
     * 根据工号查询缓存
     *
     * @param userId
     * @return
     */
    DataResult<UserDTO> queryByUserId(String userId, UserSelector selector);

    /**
     * 移除用户缓存
     *
     * @param userId
     * @return
     */
    ActionResult cacheEvict(String userId);
}
