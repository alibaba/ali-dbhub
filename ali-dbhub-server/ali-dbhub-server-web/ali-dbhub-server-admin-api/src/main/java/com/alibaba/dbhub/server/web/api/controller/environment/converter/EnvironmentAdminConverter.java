package com.alibaba.dbhub.server.web.api.controller.environment.converter;

import com.alibaba.dbhub.server.domain.api.model.Environment;
import com.alibaba.dbhub.server.domain.api.param.environment.EnvironmentQueryParam;
import com.alibaba.dbhub.server.web.api.controller.environment.request.EnvironmentListRequest;
import com.alibaba.dbhub.server.web.api.controller.environment.vo.EnvironmentListVO;

import org.mapstruct.Mapper;

/**
 * 转换器
 *
 * @author 是仪
 */
@Mapper(componentModel = "spring")
public abstract class EnvironmentAdminConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract EnvironmentQueryParam request2param(EnvironmentListRequest request);

    /**
     * 参数转换
     *
     * @param dto
     * @return
     */
    public abstract EnvironmentListVO dto2vo(Environment dto);
}
