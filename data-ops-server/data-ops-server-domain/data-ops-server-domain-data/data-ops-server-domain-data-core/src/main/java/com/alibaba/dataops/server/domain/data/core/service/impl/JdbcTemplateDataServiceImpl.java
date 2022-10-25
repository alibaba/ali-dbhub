package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
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
    public DataResult<ExecuteResultDTO> execute(TemplateExecuteParam param) {
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplate(param.getDataSourceId(), param.getConsoleId());
        return DataResult.of(jdbcDataTemplate.query(param.getSql()));
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
