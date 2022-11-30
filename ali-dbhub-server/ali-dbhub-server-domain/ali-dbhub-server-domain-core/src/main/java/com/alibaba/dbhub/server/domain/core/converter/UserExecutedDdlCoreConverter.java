package com.alibaba.dbhub.server.domain.core.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.model.UserExecutedDdlDTO;
import com.alibaba.dbhub.server.domain.api.param.UserExecutedDdlCreateParam;
import com.alibaba.dbhub.server.domain.repository.entity.UserExecutedDdlDO;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version UserExecutedDdlCoreConverter.java, v 0.1 2022年09月25日 14:08 moji Exp $
 * @date 2022/09/25
 */
@Mapper(componentModel = "spring")
public abstract class UserExecutedDdlCoreConverter {

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract UserExecutedDdlDO param2do(UserExecutedDdlCreateParam param);

    /**
     * 模型转换
     *
     * @param userExecutedDdlDO
     * @return
     */
    public abstract UserExecutedDdlDTO do2dto(UserExecutedDdlDO userExecutedDdlDO);

    /**
     * 模型转换
     *
     * @param userExecutedDdlDOS
     * @return
     */
    public abstract List<UserExecutedDdlDTO> do2dto(List<UserExecutedDdlDO> userExecutedDdlDOS);
}
