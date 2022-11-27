package com.alibaba.dbhub.server.domain.core.api.service;

import com.alibaba.dbhub.server.domain.core.api.model.TestDTO;
import com.alibaba.dbhub.server.domain.core.api.test.TestCreateParam;
import com.alibaba.dbhub.server.domain.core.api.test.TestPageQueryParam;
import com.alibaba.dbhub.server.domain.core.api.test.TestSelector;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * 测试服务
 *
 * @author Jiaju Zhuang
 */
public interface TestCoreService {

    /**
     * 新增数据
     *
     * @param param 新增参数
     * @return id
     */
    DataResult<Long> create(TestCreateParam param);

    /**
     * 分页查询
     *
     * @param param    分页参数
     * @param selector 选择器
     * @return 分页结果
     */
    PageResult<TestDTO> queryPage(TestPageQueryParam param, TestSelector selector);

}
