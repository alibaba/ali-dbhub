package com.alibaba.dbhub.server.domain.support.template;

import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 控制台服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class ConsoleTemplate implements ConsoleOperations {

    @Override
    public void create(ConsoleCreateParam param) {
        DbhubDataSource.getInstance().connectDatabase(param.getDatabaseName());
    }

    @Override
    public void close(ConsoleCloseParam param) {
        DbhubDataSource.getInstance().close();
    }
}
