package com.alibaba.ctoo.opensource.domain.core.service.impl.user;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.constants.RedisConstant;
import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserSelector;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserCacheDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserDomainService;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.common.util.EasyStringUtils;
import com.alibaba.easytools.spring.cache.EasyCache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Service
@Slf4j
public class UserCacheDomainServiceImpl implements UserCacheDomainService {

    @Resource
    private EasyCache<DataResult<UserDTO>> easyCacheQuery;
    @Resource
    private EasyCache<String> easyCache;
    @Resource
    private UserDomainService userDomainService;

    @Override
    public DataResult<UserDTO> queryByUserId(String userId, UserSelector selector) {
        String cacheKey = RedisConstant.USER_ID_QUERY_PREFIX + EasyStringUtils.cutUserId(userId);
        return easyCacheQuery.get(cacheKey, () -> userDomainService.query(userId, selector));
    }

    @Override
    public ActionResult cacheEvict(String userId) {
        easyCache.delete(
            RedisConstant.USER_ID_QUERY_PREFIX + userId,
            RedisConstant.USER_MANAGER + RedisConstant.USER_PREFIX + userId
        );
        log.info("移除缓存");
        return ActionResult.isSuccess();
    }
}
