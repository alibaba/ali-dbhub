package com.alibaba.dbhub.server.domain.support.operations;

import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateCountParam;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateExecuteParam;

/**
 * jdbc服务
 *
 * @author Jiaju Zhuang
 */
public interface JdbcOperations {

    /**
     * 执行一条sql
     *
     * @param param
     * @return
     */
    ExecuteResult execute(TemplateExecuteParam param);

    /**
     * 执行一条count语句
     *
     * @param param
     * @return
     */
    long count(TemplateCountParam param);
}
