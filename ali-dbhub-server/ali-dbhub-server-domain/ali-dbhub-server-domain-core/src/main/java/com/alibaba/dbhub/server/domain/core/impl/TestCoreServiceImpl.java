package com.alibaba.dbhub.server.domain.core.impl;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.api.model.TestDTO;
import com.alibaba.dbhub.server.domain.api.service.TestCoreService;
import com.alibaba.dbhub.server.domain.api.test.TestCreateParam;
import com.alibaba.dbhub.server.domain.api.test.TestPageQueryParam;
import com.alibaba.dbhub.server.domain.api.test.TestSelector;
import com.alibaba.dbhub.server.domain.core.converter.TestCoreConverter;
import com.alibaba.dbhub.server.domain.repository.entity.TestDO;
import com.alibaba.dbhub.server.domain.repository.mapper.TestMapper;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

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
