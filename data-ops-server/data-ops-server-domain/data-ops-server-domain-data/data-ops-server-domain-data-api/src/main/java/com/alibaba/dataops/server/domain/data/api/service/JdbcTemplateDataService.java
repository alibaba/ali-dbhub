package com.alibaba.dataops.server.domain.data.api.service;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateUpdateParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;

/**
 * jdbc服务
 *
 * @author Jiaju Zhuang
 */
public interface JdbcTemplateDataService {

    /**
     * 根据sql查询数据
     *
     * @param param
     * @return
     */
    ListResult<Map<String, Object>> queryForList(TemplateQueryParam param);

    /**
     * 根据sql查询数据
     *
     * @param param
     * @return
     */
    DataResult<Integer> update(TemplateUpdateParam param);

    /**
     * 执行一条sql
     *
     * @param param
     * @return
     */
    ActionResult execute(TemplateExecuteParam param);
}
