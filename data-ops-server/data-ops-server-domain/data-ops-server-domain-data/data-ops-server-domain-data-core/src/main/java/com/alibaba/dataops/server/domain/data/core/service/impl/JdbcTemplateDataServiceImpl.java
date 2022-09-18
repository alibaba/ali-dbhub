package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * jdbc服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class JdbcTemplateDataServiceImpl implements JdbcTemplateDataService {
    public static final Map<Long, JdbcTemplate> JDBC_TEMPLATE_MAP = new ConcurrentHashMap<>();

    @Override
    public ListResult<Map<String, Object>> queryForList(TemplateQueryParam param) {
        JdbcTemplate jdbcTemplate = JDBC_TEMPLATE_MAP.get(param.getDataSourceId());
        //jdbcTemplate.u
        if (jdbcTemplate == null) {
            throw new BusinessException("请先连接数据库");
        }
        return ListResult.of(jdbcTemplate.queryForList(param.getSql()));
    }
}
