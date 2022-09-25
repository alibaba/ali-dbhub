package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.service.SqlDataService;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * jdbc服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class SqlDataServiceImpl implements SqlDataService {

    @Override
    public ActionResult parse(ConsoleCreateParam param) {
        return null;
    }
}
