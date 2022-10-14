package com.alibaba.dataops.server.domain.core.core.converter;

import java.util.List;
import java.util.Objects;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dataops.server.domain.core.repository.entity.DataSourceDO;
import com.alibaba.dataops.server.domain.data.api.enums.DriverClassEnum;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.excption.CommonErrorEnum;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author moji
 * @version DataSourceCoreConverter.java, v 0.1 2022年09月23日 15:53 moji Exp $
 * @date 2022/09/23
 */
@Mapper(componentModel = "spring")
public abstract class DataSourceCoreConverter {

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract DataSourceDO param2do(DataSourceCreateParam param);

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract DataSourceDO param2do(DataSourceUpdateParam param);

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract ConsoleCreateParam param2consoleParam(DataSourceExecuteParam param);

    /**
     * 参数转换
     *
     * @param dataSourceDO
     * @return
     */
    public com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam do2param(
        DataSourceDO dataSourceDO) {
        com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam param
            = new com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam();
        param.setDataSourceId(dataSourceDO.getId());
        DriverClassEnum driverClassEnum = DriverClassEnum.getByName(dataSourceDO.getType());
        if (Objects.isNull(driverClassEnum)) {
            throw new BusinessException(CommonErrorEnum.DRIVER_CLASS_NOT_EXIST);
        }
        param.setDriverClass(driverClassEnum.name());
        param.setUrl(dataSourceDO.getUrl());
        param.setUsername(dataSourceDO.getUserName());
        param.setPassword(dataSourceDO.getPassword());
        return param;
    }

    /**
     * 模型转换
     *
     * @param dataSourceDO
     * @return
     */
    public abstract DataSourceDTO do2dto(DataSourceDO dataSourceDO);

    /**
     * 模型转换
     *
     * @param dataSourceDOList
     * @return
     */
    public abstract List<DataSourceDTO> do2dto(List<DataSourceDO> dataSourceDOList);
}
