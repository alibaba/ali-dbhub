package com.alibaba.dbhub.server.domain.core.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.model.Operation;
import com.alibaba.dbhub.server.domain.api.param.OperationSavedParam;
import com.alibaba.dbhub.server.domain.api.param.OperationUpdateParam;
import com.alibaba.dbhub.server.domain.repository.entity.OperationSavedDO;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version UserSavedDdlCoreConverter.java, v 0.1 2022年09月25日 15:50 moji Exp $
 * @date 2022/09/25
 */
@Mapper(componentModel = "spring")
public abstract class OperationConverter {

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract OperationSavedDO param2do(OperationSavedParam param);

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract OperationSavedDO param2do(OperationUpdateParam param);

    /**
     * 模型转换
     *
     * @param userSavedDdlDO
     * @return
     */
    public abstract Operation do2dto(OperationSavedDO userSavedDdlDO);

    /**
     * 模型转换
     *
     * @param userSavedDdlDOS
     * @return
     */
    public abstract List<Operation> do2dto(List<OperationSavedDO> userSavedDdlDOS);
}
