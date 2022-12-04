package com.alibaba.dbhub.server.web.api.controller.log.manual.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.model.Operation;
import com.alibaba.dbhub.server.domain.api.param.OperationPageQueryParam;
import com.alibaba.dbhub.server.domain.api.param.OperationSavedParam;
import com.alibaba.dbhub.server.domain.api.param.OperationUpdateParam;
import com.alibaba.dbhub.server.web.api.controller.log.manual.request.OperationCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.log.manual.request.OperationQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.log.manual.request.OperationUpdateRequest;
import com.alibaba.dbhub.server.web.api.controller.log.manual.vo.OperationVO;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version DdlManageWebConverter.java, v 0.1 2022年09月26日 10:08 moji Exp $
 * @date 2022/09/26
 */
@Mapper(componentModel = "spring")
public abstract class OperationWebConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract OperationSavedParam req2param(OperationCreateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract OperationUpdateParam updateReq2param(OperationUpdateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract OperationPageQueryParam queryReq2param(OperationQueryRequest request);

    /**
     * 模型转换
     *
     * @param ddlDTO
     * @return
     */
    public abstract OperationVO dto2vo(Operation ddlDTO);

    /**
     * 模型转换
     *
     * @param ddlDTOS
     * @return
     */
    public abstract List<OperationVO> dto2vo(List<Operation> ddlDTOS);
}
