package com.alibaba.dbhub.server.domain.data.api.service;

import com.alibaba.dbhub.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dbhub.server.domain.data.api.param.template.TemplateCountParam;
import com.alibaba.dbhub.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;

/**
 * jdbc服务
 *
 * @author Jiaju Zhuang
 */
public interface JdbcTemplateDataService {

    /**
     * 执行一条sql
     *
     * @param param
     * @return
     */
    DataResult<ExecuteResultDTO> execute(TemplateExecuteParam param);

    /**
     * 执行一条count语句
     *
     * @param param
     * @return
     */
    DataResult<Long> count(TemplateCountParam param);
}
