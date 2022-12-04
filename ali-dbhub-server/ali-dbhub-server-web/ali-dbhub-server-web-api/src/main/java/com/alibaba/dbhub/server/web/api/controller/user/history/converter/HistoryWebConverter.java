package com.alibaba.dbhub.server.web.api.controller.user.history.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.model.UserExecutedDdlDTO;
import com.alibaba.dbhub.server.domain.api.param.UserExecutedDdlCreateParam;
import com.alibaba.dbhub.server.domain.api.param.UserExecutedDdlPageQueryParam;
import com.alibaba.dbhub.server.web.api.controller.user.history.request.HistoryCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.history.request.HistoryQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.user.history.vo.HistoryVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author moji
 * @version HistoryWebConverter.java, v 0.1 2022年09月25日 16:53 moji Exp $
 * @date 2022/09/25
 */
@Mapper(componentModel = "spring")
public abstract class HistoryWebConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract UserExecutedDdlCreateParam createReq2param(HistoryCreateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract UserExecutedDdlPageQueryParam req2param(HistoryQueryRequest request);

    /**
     * 模型转换
     *
     * @param ddlDTO
     * @return
     */
    @Mappings({
        @Mapping(source = "ddl", target = "name")
    })
    public abstract HistoryVO dto2vo(UserExecutedDdlDTO ddlDTO);

    /**
     * 模型转换
     *
     * @param ddlDTOS
     * @return
     */
    public abstract List<HistoryVO> dto2vo(List<UserExecutedDdlDTO> ddlDTOS);
}
