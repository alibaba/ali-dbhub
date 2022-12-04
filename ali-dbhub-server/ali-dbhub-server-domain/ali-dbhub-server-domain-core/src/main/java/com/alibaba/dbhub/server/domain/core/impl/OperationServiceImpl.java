package com.alibaba.dbhub.server.domain.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.alibaba.dbhub.server.domain.api.model.Operation;
import com.alibaba.dbhub.server.domain.api.param.OperationPageQueryParam;
import com.alibaba.dbhub.server.domain.api.param.OperationSavedParam;
import com.alibaba.dbhub.server.domain.api.param.OperationUpdateParam;
import com.alibaba.dbhub.server.domain.api.service.OperationService;
import com.alibaba.dbhub.server.domain.core.converter.OperationConverter;
import com.alibaba.dbhub.server.domain.repository.entity.OperationSavedDO;
import com.alibaba.dbhub.server.domain.repository.mapper.OperationSavedMapper;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
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
 * @version UserSavedDdlCoreServiceImpl.java, v 0.1 2022年09月25日 15:50 moji Exp $
 * @date 2022/09/25
 */
@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationSavedMapper operationSavedMapper;

    @Autowired
    private OperationConverter operationConverter;

    @Override
    public DataResult<Long> create(OperationSavedParam param) {
        OperationSavedDO userSavedDdlDO = operationConverter.param2do(param);
        userSavedDdlDO.setGmtCreate(LocalDateTime.now());
        userSavedDdlDO.setGmtModified(LocalDateTime.now());
        operationSavedMapper.insert(userSavedDdlDO);
        return DataResult.of(userSavedDdlDO.getId());
    }

    @Override
    public ActionResult update(OperationUpdateParam param) {
        OperationSavedDO userSavedDdlDO = operationConverter.param2do(param);
        userSavedDdlDO.setGmtModified(LocalDateTime.now());
        operationSavedMapper.updateById(userSavedDdlDO);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult delete(Long id) {
        operationSavedMapper.deleteById(id);
        return ActionResult.isSuccess();
    }

    @Override
    public PageResult<Operation> queryPage(OperationPageQueryParam param) {
        QueryWrapper<OperationSavedDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getSearchKey())) {
            queryWrapper.like("name", param.getSearchKey());
        }
        if (Objects.nonNull(param.getDataSourceId())) {
            queryWrapper.eq("data_source_id", param.getDataSourceId());
        }
        if (StringUtils.isNotBlank(param.getDatabaseName())) {
            queryWrapper.eq("database_name", param.getDatabaseName());
        }
        if (StringUtils.isNotBlank(param.getStatus())) {
            queryWrapper.eq("status", param.getStatus());
        }
        Integer start = param.getPageNo();
        Integer offset = param.getPageSize();
        Page<OperationSavedDO> page = new Page<>(start, offset);
        IPage<OperationSavedDO> iPage = operationSavedMapper.selectPage(page, queryWrapper);
        List<Operation> userSavedDdlDOS = operationConverter.do2dto(iPage.getRecords());
        return PageResult.of(userSavedDdlDOS, iPage.getTotal(), param);
    }
}

