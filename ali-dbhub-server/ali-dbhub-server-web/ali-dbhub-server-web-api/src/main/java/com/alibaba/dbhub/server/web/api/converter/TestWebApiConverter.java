package com.alibaba.dbhub.server.web.api.converter;

import com.alibaba.dbhub.server.domain.core.api.test.TestCreateParam;
import com.alibaba.dbhub.server.web.api.request.test.TestCreateRequest;

import org.mapstruct.Mapper;

/**
 * 转换器
 *
 * @author Jiaju Zhuang
 */
@Mapper(componentModel = "spring")
public abstract class TestWebApiConverter {

    /**
     * 转换
     *
     * @param request
     * @return
     */
    public abstract TestCreateParam request2Param(TestCreateRequest request);
}
