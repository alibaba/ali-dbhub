package com.alibaba.dbhub.server.domain.core.core.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.dbhub.server.domain.core.api.model.UserExecutedDdlDTO;
import com.alibaba.dbhub.server.domain.core.api.param.UserExecutedDdlCreateParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserExecutedDdlPageQueryParam;
import com.alibaba.dbhub.server.domain.core.api.service.UserExecutedDdlCoreService;
import com.alibaba.dbhub.server.domain.core.core.converter.UserExecutedDdlCoreConverter;
import com.alibaba.dbhub.server.domain.core.repository.entity.UserExecutedDdlDO;
import com.alibaba.dbhub.server.domain.core.repository.mapper.UserExecutedDdlMapper;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
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
        QueryWrapper<UserExecutedDdlDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getSearchKey())) {
            queryWrapper.like("ddl", param.getSearchKey());
        }
        Integer start = param.getPageNo();
        Integer offset = param.getPageSize();
        Page<UserExecutedDdlDO> page = new Page<>(start, offset);
        IPage<UserExecutedDdlDO> executedDdlDOIPage = userExecutedDdlMapper.selectPage(page, queryWrapper);
        List<UserExecutedDdlDTO> executedDdlDTOS = userExecutedDdlCoreConverter.do2dto(executedDdlDOIPage.getRecords());
        return PageResult.of(executedDdlDTOS, executedDdlDOIPage.getTotal(), param);
    }
}
