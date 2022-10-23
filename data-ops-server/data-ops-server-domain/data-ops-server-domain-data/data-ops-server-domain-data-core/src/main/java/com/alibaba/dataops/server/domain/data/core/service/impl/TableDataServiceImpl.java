package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.TableDataService;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class TableDataServiceImpl implements TableDataService {

    @Override
    public DataResult<TableDTO> query(TableQueryParam param) {
        return null;
    }

    @Override
    public PageResult<TableDTO> pageQuery(TablePageQueryParam param) {
        return null;
    }
}
