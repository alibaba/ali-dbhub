package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
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
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.get(
            param.getDataSourceId());
        if (jdbcDataTemplateMap == null) {
            throw new BusinessException(ErrorEnum.CONSOLE_NOT_FOUND);
        }
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplateMap.remove(param.getConsoleId());
        if (jdbcDataTemplate == null) {
            throw new BusinessException(ErrorEnum.CONSOLE_NOT_FOUND);
        }
        return ListResult.of(jdbcDataTemplate.queryForList(param.getSql()));
    }
}
