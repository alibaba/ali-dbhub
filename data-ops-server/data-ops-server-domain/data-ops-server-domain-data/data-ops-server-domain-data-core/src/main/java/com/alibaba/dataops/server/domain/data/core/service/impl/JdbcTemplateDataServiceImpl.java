package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * jdbc服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class JdbcTemplateDataServiceImpl implements JdbcTemplateDataService {

    @Override
    public ListResult<Map<String, Object>> queryForList(TemplateQueryParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.JDBC_TEMPLATE_CACHE.get(param.getConsoleId());
        //jdbcTemplate.u
        if (jdbcDataTemplate == null) {
            throw new BusinessException("请先连接数据库");
        }
        return ListResult.of(jdbcDataTemplate.queryForList(param.getSql()));
    }
}
