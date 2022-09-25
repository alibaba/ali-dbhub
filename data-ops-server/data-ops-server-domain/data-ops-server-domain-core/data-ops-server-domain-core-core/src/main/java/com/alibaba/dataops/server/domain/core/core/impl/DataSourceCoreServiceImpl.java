package com.alibaba.dataops.server.domain.core.core.impl;

import java.time.LocalDateTime;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.service.DataSourceCoreService;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourcePageQueryParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceSelector;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dataops.server.domain.core.core.converter.DataSourceCoreConverter;
import com.alibaba.dataops.server.domain.core.repository.entity.DataSourceDO;
import com.alibaba.dataops.server.domain.core.repository.mapper.DataSourceMapper;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class DataSourceCoreServiceImpl implements DataSourceCoreService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private DataSourceCoreConverter dataSourceCoreConverter;

    @Override
    public DataResult<Long> create(DataSourceCreateParam param) {
        DataSourceDO dataSourceDO = dataSourceCoreConverter.param2do(param);
        dataSourceDO.setGmtCreate(LocalDateTime.now());
        dataSourceDO.setGmtModified(LocalDateTime.now());
        dataSourceMapper.insert(dataSourceDO);
        return DataResult.of(dataSourceDO.getId());
    }

    @Override
    public ActionResult update(DataSourceUpdateParam param) {
        DataSourceDO dataSourceDO = dataSourceCoreConverter.param2do(param);
        dataSourceDO.setGmtModified(LocalDateTime.now());
        dataSourceMapper.updateById(dataSourceDO);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult delete(Long id) {
        dataSourceMapper.deleteById(id);
        return ActionResult.isSuccess();
    }

    @Override
    public DataResult<DataSourceDTO> queryById(Long id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectById(id);
        return DataResult.of(dataSourceCoreConverter.do2dto(dataSourceDO));
    }

    @Override
    public DataResult<Long> copyById(Long id) {
        DataSourceDO dataSourceDO = dataSourceMapper.selectById(id);
        dataSourceDO.setId(null);
        String alias = dataSourceDO.getAlias() + "Copy";
        dataSourceDO.setAlias(alias);
        dataSourceDO.setGmtCreate(LocalDateTime.now());
        dataSourceDO.setGmtModified(LocalDateTime.now());
        dataSourceMapper.insert(dataSourceDO);
        return DataResult.of(dataSourceDO.getId());
    }

    @Override
    public PageResult<DataSourceDTO> queryPage(DataSourcePageQueryParam param, DataSourceSelector selector) {
        return null;
    }
}
