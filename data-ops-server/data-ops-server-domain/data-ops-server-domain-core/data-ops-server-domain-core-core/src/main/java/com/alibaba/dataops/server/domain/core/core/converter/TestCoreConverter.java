package com.alibaba.dataops.server.domain.core.core.converter;

import com.alibaba.dataops.server.domain.core.api.test.TestCreateParam;
import com.alibaba.dataops.server.domain.core.repository.entity.TestDO;

import org.mapstruct.Mapper;

/**
 * 文章转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring")
public abstract class TestCoreConverter {

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract TestDO param2do(TestCreateParam param);

}
