package com.alibaba.dbhub.server.web.api.controller.user.save.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.core.api.model.UserSavedDdlDTO;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlCreateParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlPageQueryParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlUpdateParam;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlUpdateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.vo.DdlVO;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlUpdateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.vo.DdlVO;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version DdlManageWebConverter.java, v 0.1 2022年09月26日 10:08 moji Exp $
 * @date 2022/09/26
 */
@Mapper(componentModel = "spring")
public abstract class DdlManageWebConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract UserSavedDdlCreateParam req2param(DdlCreateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract UserSavedDdlUpdateParam updateReq2param(DdlUpdateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract UserSavedDdlPageQueryParam queryReq2param(DdlQueryRequest request);

    /**
     * 模型转换
     *
     * @param ddlDTO
     * @return
     */
    public abstract DdlVO dto2vo(UserSavedDdlDTO ddlDTO);

    /**
     * 模型转换
     *
     * @param ddlDTOS
     * @return
     */
    public abstract List<DdlVO> dto2vo(List<UserSavedDdlDTO> ddlDTOS);
}
