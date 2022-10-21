package com.alibaba.dataops.server.web.api.controller.mysql.converter;

import com.alibaba.dataops.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dataops.server.web.api.controller.mysql.request.DataManageRequest;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version MysqlDataConverter.java, v 0.1 2022年10月14日 14:04 moji Exp $
 * @date 2022/10/14
 */
@Mapper(componentModel = "spring")
public abstract class MysqlDataConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract DataSourceExecuteParam request2param(DataManageRequest request);
}
