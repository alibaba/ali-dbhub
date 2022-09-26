package com.alibaba.dataops.server.domain.core.core.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.model.UserSavedDdlDTO;
import com.alibaba.dataops.server.domain.core.api.param.UserSavedDdlCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.UserSavedDdlUpdateParam;
import com.alibaba.dataops.server.domain.core.repository.entity.UserSavedDdlDO;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version UserSavedDdlCoreConverter.java, v 0.1 2022年09月25日 15:50 moji Exp $
 * @date 2022/09/25
 */
@Mapper(componentModel = "spring")
public abstract class UserSavedDdlCoreConverter {

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract UserSavedDdlDO param2do(UserSavedDdlCreateParam param);

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract UserSavedDdlDO param2do(UserSavedDdlUpdateParam param);

    /**
     * 模型转换
     *
     * @param userSavedDdlDO
     * @return
     */
    public abstract UserSavedDdlDTO do2dto(UserSavedDdlDO userSavedDdlDO);

    /**
     * 模型转换
     *
     * @param userSavedDdlDOS
     * @return
     */
    public abstract List<UserSavedDdlDTO> do2dto(List<UserSavedDdlDO> userSavedDdlDOS);
}
