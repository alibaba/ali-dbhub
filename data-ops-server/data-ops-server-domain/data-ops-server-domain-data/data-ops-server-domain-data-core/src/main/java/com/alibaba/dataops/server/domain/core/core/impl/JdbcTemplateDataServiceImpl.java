package com.alibaba.dataops.server.domain.core.core.impl;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 测试实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class JdbcTemplateDataServiceImpl implements JdbcTemplateDataService {

    @Override
    public ListResult<Map<String, Object>> queryForList(String sql) {
        return null;
    }
}
