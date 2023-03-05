package com.alibaba.dbhub.server.domain.core.impl;

import java.util.Objects;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.api.model.User;
import com.alibaba.dbhub.server.domain.api.service.UserService;
import com.alibaba.dbhub.server.domain.core.converter.UserConverter;
import com.alibaba.dbhub.server.domain.repository.entity.DbhubUserDO;
import com.alibaba.dbhub.server.domain.repository.mapper.DbhubUserMapper;
import com.alibaba.dbhub.server.domain.support.param.user.UserQueryParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author 是仪
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private DbhubUserMapper dbhubUserMapper;
    @Resource
    private UserConverter userConverter;

    @Override
    public DataResult<User> query(Long id) {
        return DataResult.of(userConverter.do2dto(dbhubUserMapper.selectById(id)));
    }

    @Override
    public DataResult<User> query(UserQueryParam param) {
        LambdaQueryWrapper<DbhubUserDO> query = new LambdaQueryWrapper<>();
        if (Objects.nonNull(param.getUserName())) {
            query.eq(DbhubUserDO::getUserName, param.getUserName());
        }
        DbhubUserDO data = dbhubUserMapper.selectOne(query);
        return DataResult.of(userConverter.do2dto(data));
    }
}
