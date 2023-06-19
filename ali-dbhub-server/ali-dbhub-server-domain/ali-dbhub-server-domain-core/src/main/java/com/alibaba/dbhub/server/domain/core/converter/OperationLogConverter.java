package com.alibaba.dbhub.server.domain.core.converter;

import com.alibaba.dbhub.server.domain.api.model.OperationLog;
import com.alibaba.dbhub.server.domain.api.param.OperationLogCreateParam;
import com.alibaba.dbhub.server.domain.repository.entity.OperationLogDO;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * @author moji
 * @version UserExecutedDdlCoreConverter.java, v 0.1 2022年09月25日 14:08 moji Exp $
 * @date 2022/09/25
 */
@Mapper(componentModel = "spring")
public abstract class OperationLogConverter {

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract OperationLogDO param2do(OperationLogCreateParam param);

    /**
     * 模型转换
     *
     * @param userExecutedDdlDO
     * @return
     */
    public abstract OperationLog do2dto(OperationLogDO userExecutedDdlDO);

    /**
     * 模型转换
     *
     * @param userExecutedDdlDOS
     * @return
     */
    public abstract List<OperationLog> do2dto(List<OperationLogDO> userExecutedDdlDOS);
}
