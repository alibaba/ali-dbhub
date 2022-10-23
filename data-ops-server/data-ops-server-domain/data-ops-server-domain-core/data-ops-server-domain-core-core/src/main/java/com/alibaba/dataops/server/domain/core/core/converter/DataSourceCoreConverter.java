package com.alibaba.dataops.server.domain.core.core.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceManageCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceTestParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dataops.server.domain.core.repository.entity.DataSourceDO;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;

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
    public abstract DataSourceDO param2do(DataSourceManageCreateParam param);

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
    @Mappings({
        @Mapping(source = "type", target = "dbType"),
        @Mapping(source = "id", target = "dataSourceId")
    })
    public abstract DataSourceCreateParam do2param(DataSourceDO dataSourceDO);



    /**
     * 参数转换
     *
     * @param dataSourceTestParam
     * @return
     */
    @Mappings({
        @Mapping(source = "type", target = "dbType")
    })
    public abstract DataSourceCreateParam param2param(DataSourceTestParam dataSourceTestParam);

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
