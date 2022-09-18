package com.alibaba.dataops.server.domain.core.core.impl;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.core.api.model.TestDTO;
import com.alibaba.dataops.server.domain.core.api.service.TestCoreService;
import com.alibaba.dataops.server.domain.core.api.test.TestCreateParam;
import com.alibaba.dataops.server.domain.core.api.test.TestPageQueryParam;
import com.alibaba.dataops.server.domain.core.api.test.TestSelector;
import com.alibaba.dataops.server.domain.core.core.converter.TestCoreConverter;
import com.alibaba.dataops.server.domain.core.repository.entity.TestDO;
import com.alibaba.dataops.server.domain.core.repository.mapper.TestMapper;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 测试实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class TestCoreServiceImpl implements TestCoreService {

    @Resource
    private TestMapper testMapper;
    @Resource
    private TestCoreConverter testCoreConverter;

    @Override
    public DataResult<Long> create(TestCreateParam param) {
        TestDO test = testCoreConverter.param2do(param);
        testMapper.insert(test);
        //return DataResult.of(test.getId());
        return null;
    }

    @Override
    public PageResult<TestDTO> queryPage(TestPageQueryParam param, TestSelector selector) {
        return null;
    }
}
