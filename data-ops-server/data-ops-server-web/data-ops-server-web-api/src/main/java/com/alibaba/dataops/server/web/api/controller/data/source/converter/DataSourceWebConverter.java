package com.alibaba.dataops.server.web.api.controller.data.source.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.model.DatabaseDTO;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourcePageQueryParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceTestParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceCreateRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceQueryRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceTestRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceUpdateRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.vo.DataSourceVO;
import com.alibaba.dataops.server.web.api.controller.data.source.vo.DatabaseVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author moji
 * @version DataSourceWebConverter.java, v 0.1 2022年09月23日 16:45 moji Exp $
 * @date 2022/09/23
 */
@Mapper(componentModel = "spring")
public abstract class DataSourceWebConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    @Mappings({
        @Mapping(source = "user", target = "userName")
    })
    public abstract DataSourceCreateParam createReq2param(DataSourceCreateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    @Mappings({
        @Mapping(source = "user", target = "userName")
    })
    public abstract DataSourceUpdateParam updateReq2param(DataSourceUpdateRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract DataSourcePageQueryParam queryReq2param(DataSourceQueryRequest request);

    /**
     * 模型转换
     *
     * @param dataSourceDTO
     * @return
     */
    @Mappings({
        @Mapping(target = "user", source = "userName")
    })
    public abstract DataSourceVO dto2vo(DataSourceDTO dataSourceDTO);

    /**
     * 模型转换
     *
     * @param dataSourceDTOS
     * @return
     */
    public abstract List<DataSourceVO> dto2vo(List<DataSourceDTO> dataSourceDTOS);

    /**
     * 模型转换
     *
     * @param databaseDTO
     * @return
     */
    public abstract DatabaseVO databaseDto2vo(DatabaseDTO databaseDTO);

    /**
     * 模型转换
     *
     * @param databaseDTOS
     * @return
     */
    public abstract List<DatabaseVO> databaseDto2vo(List<DatabaseDTO> databaseDTOS);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract DataSourceTestParam testRequest2param(DataSourceTestRequest request);

}
