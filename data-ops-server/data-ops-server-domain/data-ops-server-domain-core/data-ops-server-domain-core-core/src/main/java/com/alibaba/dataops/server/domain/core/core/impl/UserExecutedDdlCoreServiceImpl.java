package com.alibaba.dataops.server.domain.core.core.impl;

import java.time.LocalDateTime;

import com.alibaba.dataops.server.domain.core.api.model.UserExecutedDdlDTO;
import com.alibaba.dataops.server.domain.core.api.param.UserExecutedDdlCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.UserExecutedDdlPageQueryParam;
import com.alibaba.dataops.server.domain.core.api.service.UserExecutedDdlCoreService;
import com.alibaba.dataops.server.domain.core.core.converter.UserExecutedDdlCoreConverter;
import com.alibaba.dataops.server.domain.core.repository.entity.UserExecutedDdlDO;
import com.alibaba.dataops.server.domain.core.repository.mapper.UserExecutedDdlMapper;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version UserExecutedDdlCoreServiceImpl.java, v 0.1 2022年09月25日 14:07 moji Exp $
 * @date 2022/09/25
 */
@Service
public class UserExecutedDdlCoreServiceImpl implements UserExecutedDdlCoreService {

    @Autowired
    private UserExecutedDdlMapper userExecutedDdlMapper;

    @Autowired
    private UserExecutedDdlCoreConverter userExecutedDdlCoreConverter;

    @Override
    public DataResult<Long> create(UserExecutedDdlCreateParam param) {
        UserExecutedDdlDO userExecutedDdlDO = userExecutedDdlCoreConverter.param2do(param);
        userExecutedDdlDO.setGmtCreate(LocalDateTime.now());
        userExecutedDdlDO.setGmtModified(LocalDateTime.now());
        userExecutedDdlMapper.insert(userExecutedDdlDO);
        return DataResult.of(userExecutedDdlDO.getId());
    }

    @Override
    public PageResult<UserExecutedDdlDTO> queryPage(UserExecutedDdlPageQueryParam param) {
        return null;
    }
}
