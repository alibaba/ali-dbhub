package com.alibaba.dataops.server.domain.core.core.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dataops.server.domain.core.repository.entity.DataSourceDO;

import org.mapstruct.Mapper;

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
