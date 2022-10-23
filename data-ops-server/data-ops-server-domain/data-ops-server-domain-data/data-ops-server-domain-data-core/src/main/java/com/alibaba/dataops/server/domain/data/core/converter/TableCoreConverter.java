package com.alibaba.dataops.server.domain.data.core.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.core.dataobject.TableDO;

import org.mapstruct.Mapper;

/**
 * 转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring")
public abstract class TableCoreConverter {

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<TableDTO> do2dto(List<TableDO> list);

}
