package com.alibaba.dbhub.server.domain.core.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.model.DataSource;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourcePreConnectParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceUpdateParam;
import com.alibaba.dbhub.server.domain.repository.entity.DataSourceDO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author moji
 * @version DataSourceCoreConverter.java, v 0.1 2022年09月23日 15:53 moji Exp $
 * @date 2022/09/23
 */
@Mapper(componentModel = "spring")
public abstract class DataSourceConverter {

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
    public abstract ConsoleCreateParam param2consoleParam(ConsoleConnectParam param);

    /**
     * 参数转换
     *
     * @param dataSourceDO
     * @return
     */
    @Mappings({
        @Mapping(source = "type", target = "dbType"),
        @Mapping(source = "id", target = "dataSourceId"),
        @Mapping(source = "userName", target = "username"),
    })
    public abstract com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam do2param(DataSourceDO dataSourceDO);



    /**
     * 参数转换
     *
     * @param dataSourcePreConnectParam
     * @return
     */
    @Mappings({
        @Mapping(source = "type", target = "dbType"),
        @Mapping(source = "user", target = "username")
    })
    public abstract com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceTestParam param2param(
        DataSourcePreConnectParam dataSourcePreConnectParam);


    /**
     * 模型转换
     *
     * @param dataSourceDO
     * @return
     */
    public abstract DataSource do2dto(DataSourceDO dataSourceDO);

    /**
     * 模型转换
     *
     * @param dataSourceDOList
     * @return
     */
    public abstract List<DataSource> do2dto(List<DataSourceDO> dataSourceDOList);
}
