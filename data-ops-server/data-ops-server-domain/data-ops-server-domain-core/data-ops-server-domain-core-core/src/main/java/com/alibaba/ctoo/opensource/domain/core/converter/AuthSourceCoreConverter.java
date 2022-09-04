package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.AuthSourceDO;
import com.alibaba.ctoo.opensource.domain.api.model.AuthSourceDTO;
import org.mapstruct.Mapper;

/**
 * 权限资源服务
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
@Mapper(componentModel = "spring")
public abstract class AuthSourceCoreConverter {

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<AuthSourceDTO> do2dto(List<AuthSourceDO> list);

    /**
     * 转换
     *
     * @param authSourceDO
     * @return
     */
    public abstract AuthSourceDTO do2dto(AuthSourceDO authSourceDO);
}
