package com.alibaba.dbhub.server.domain.core.converter;

import com.alibaba.dbhub.server.domain.api.test.TestCreateParam;
import com.alibaba.dbhub.server.domain.repository.entity.TestDO;

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
