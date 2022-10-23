package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableSelector;
import com.alibaba.dataops.server.domain.data.api.service.TableDataService;
import com.alibaba.dataops.server.domain.data.core.converter.TableCoreConverter;
import com.alibaba.dataops.server.domain.data.core.dataobject.TableDO;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.druid.DbType;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * 表结构服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class TableDataServiceImpl implements TableDataService {
    @Resource
    private TableCoreConverter tableCoreConverter;

    @Override
    public DataResult<TableDTO> query(TableQueryParam param, TableSelector selector) {
        return null;
    }

    @Override
    public PageResult<TableDTO> pageQuery(TablePageQueryParam param, TableSelector selector) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getDefaultJdbcDataTemplate(param.getDataSourceId());
        // TODO 根据不同的数据库类型来区分
        DbType dbType = DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId());

        // 查询表结构信息
        List<TableDO> queryList = jdbcDataTemplate.query(
            " select TABLE_NAME \n"
                + "     , REMARKS   \n"
                + "        from INFORMATION_SCHEMA.TABLES\n"
                + "        where TABLE_SCHEMA = ?\n"
                + "        order by TABLE_NAME;", (RowMapper)(rs, rowNum) -> {
                TableDO tableDO = new TableDO();
                tableDO.setName(rs.getString("TABLE_NAME"));
                tableDO.setComment(rs.getString("REMARKS"));
                return tableDO;
            }, param.getDatabaseName());
        List<TableDTO> list = tableCoreConverter.do2dto(queryList);

        // 填充数据
        fillData(list, selector);
        return PageResult.of(list, 500L, param);
    }

    private void fillData(List<TableDTO> list, TableSelector selector) {
        if (CollectionUtils.isEmpty(list) || selector == null) {
            return;
        }
        // 填充
        fillColumnList(list, selector);
    }

    private void fillColumnList(List<TableDTO> list, TableSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getColumnList())) {
            return;
        }

        //List<TableColumnDO> tableColumnList = jdbcDataTemplate.queryForList(
        //    "SELECT COLUMN_NAME              as name ,\n"
        //        + "       DATA_TYPE              as type,\n"
        //        + "       IS_NULLABLE              as nullable,\n"
        //        + "       COLUMN_DEFAULT           as defaultValue,\n"
        //        + "       EXTRA  as extra,\n"
        //        + "       COLUMN_COMMENT           AS comment,\n"
        //        + "       NUMERIC_PRECISION        as numericPrecision   ,\n"
        //        + "       NUMERIC_SCALE            as numericScale,\n"
        //        + "       CHARACTER_MAXIMUM_LENGTH as characterMaximumLength\n"
        //        + "FROM INFORMATION_SCHEMA.COLUMNS\n"
        //        + "WHERE table_name = ?\n"
        //        + "  AND TABLE_SCHEMA = ?\n"
        //        + "ORDER BY ORDINAL_POSITION;", TableColumnDO.class, param.getTableName(), param.getDatabaseName());
    }
}
