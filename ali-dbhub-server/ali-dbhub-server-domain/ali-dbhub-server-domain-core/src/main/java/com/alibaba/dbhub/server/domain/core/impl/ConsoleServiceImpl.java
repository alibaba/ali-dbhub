package com.alibaba.dbhub.server.domain.core.impl;

import com.alibaba.dbhub.server.domain.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.api.service.ConsoleService;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;

import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class ConsoleServiceImpl implements ConsoleService {
    @Override
    public ActionResult createConsole(ConsoleConnectParam param) {
        DbhubDataSource.getInstance().connectDatabase(param.getDatabaseName());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult closeConsole(ConsoleCloseParam param) {
        DbhubDataSource.getInstance().close();
        return ActionResult.isSuccess();
    }

}
