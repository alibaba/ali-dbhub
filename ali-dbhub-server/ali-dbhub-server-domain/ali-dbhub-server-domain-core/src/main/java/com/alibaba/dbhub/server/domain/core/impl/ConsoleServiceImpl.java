package com.alibaba.dbhub.server.domain.core.impl;

import com.alibaba.dbhub.server.domain.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.api.service.ConsoleService;
import com.alibaba.dbhub.server.domain.core.converter.DataSourceConverter;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class ConsoleServiceImpl implements ConsoleService {

    @Autowired
    private ConsoleOperations consoleOperations;


    @Autowired
    private DataSourceConverter dataSourceConverter;

    @Override
    public ActionResult createConsole(ConsoleConnectParam param) {
        ConsoleCreateParam createParam = dataSourceConverter.param2consoleParam(param);
        consoleOperations.create(createParam);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult closeConsole(ConsoleCloseParam param) {
        consoleOperations.close(param);
        return ActionResult.isSuccess();
    }

}
