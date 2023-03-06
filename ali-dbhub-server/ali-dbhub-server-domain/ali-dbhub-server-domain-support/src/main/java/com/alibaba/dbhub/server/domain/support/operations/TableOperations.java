package com.alibaba.dbhub.server.domain.support.operations;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
public interface TableOperations {

    /**
     * 查询表信息
     *
     * @param param
     * @return
     */
    String showCreateTable(ShowCreateTableParam param);

    /**
     * 删除表结构
     *
     * @param param
     * @return
     */
    void drop(DropParam param);

    /**
     * 查询表信息
     *
     * @param param
     * @param selector
     * @return
     */
    Table query(TableQueryParam param, TableSelector selector);

    /**
     * 根据结构构件sql
     * 构建sql语句
     * 创建表则 oldTable传空
     *
     * @param oldTable
     * @param newTable
     * @return
     */
    List<Sql> buildSql(Table oldTable, Table newTable);

    /**
     * 分页查询表信息
     *
     * @param param
     * @param selector
     * @return
     */
    PageResult<Table> pageQuery(TablePageQueryParam param, TableSelector selector);


    /**
     * 查询表包含的字段
     * @param param
     * @return
     */
    List<TableColumn> queryColumns(TableQueryParam param);

    /**
     * 查询表索引
     * @param param
     * @return
     */
    List<TableIndex> queryIndexes(TableQueryParam param);}
