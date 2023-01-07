package com.alibaba.dbhub.server.domain.core.impl;

import com.alibaba.dbhub.server.domain.api.service.TableService;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.operations.ExampleOperations;
import com.alibaba.dbhub.server.domain.support.operations.TableOperations;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.common.util.EasyEnumUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moji
 * @version DataSourceCoreServiceImpl.java, v 0.1 2022年09月23日 15:51 moji Exp $
 * @date 2022/09/23
 */
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableOperations tableOperations;

    @Autowired
    private ExampleOperations exampleOperations;

    @Override
    public DataResult<String> showCreateTable(ShowCreateTableParam param) {
        return DataResult.of(tableOperations.showCreateTable(param));
    }

    @Override
    public ActionResult drop(DropParam param) {
        tableOperations.drop(param);
        return ActionResult.isSuccess();
    }

    @Override
    public DataResult<String> createTableExample(String dbType) {
        DbTypeEnum dbTypeEnum = EasyEnumUtils.getEnum(DbTypeEnum.class, dbType);
        return DataResult.of(exampleOperations.createTable(dbTypeEnum));
    }

    @Override
    public DataResult<String> alterTableExample(String dbType) {
        DbTypeEnum dbTypeEnum = EasyEnumUtils.getEnum(DbTypeEnum.class, dbType);
        return DataResult.of(exampleOperations.alterTable(dbTypeEnum));
    }

    @Override
    public DataResult<Table> query(TableQueryParam param, TableSelector selector) {
        return DataResult.of(tableOperations.query(param, selector));
    }

    @Override
    public ListResult<Sql> buildSql(Table oldTable, Table newTable) {
        return ListResult.of(tableOperations.buildSql(oldTable, newTable));
    }

    @Override
    public PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector) {
        return tableOperations.pageQuery(param, selector);
    }
}
