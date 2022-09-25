package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateUpdateParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.tools.common.enums.ErrorEnum;

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
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplate(param.getDataSourceId(), param.getConsoleId());
        return ListResult.of(jdbcDataTemplate.queryForList(param.getSql()));
    }

    @Override
    public DataResult<Integer> update(TemplateUpdateParam param) {
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplate(param.getDataSourceId(), param.getConsoleId());
        return DataResult.of(jdbcDataTemplate.update(param.getSql()));
    }

    @Override
    public ActionResult execute(TemplateExecuteParam param) {
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplate(param.getDataSourceId(), param.getConsoleId());
        jdbcDataTemplate.execute(param.getSql());
        return ActionResult.isSuccess();
    }

    private JdbcDataTemplate jdbcDataTemplate(Long dataSourceId, Long consoleId) {
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.get(dataSourceId);
        if (jdbcDataTemplateMap == null) {
            throw new BusinessException(ErrorEnum.CONSOLE_NOT_FOUND);
        }
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplateMap.get(consoleId);
        if (jdbcDataTemplate == null) {
            throw new BusinessException(ErrorEnum.CONSOLE_NOT_FOUND);
        }
        return jdbcDataTemplate;
    }
}
