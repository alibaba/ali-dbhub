/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.sqlserver;

import java.sql.SQLException;

import com.alibaba.dbhub.server.domain.support.dialect.BaseMetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.sql.DataSource;

/**
 * @author jipengfei
 * @version : SqlserverMetaSchemaSupport.java
 */
public class SqlServerMetaSchemaSupport extends BaseMetaSchema implements MetaSchema {

   private String functionSQL = " CREATE FUNCTION tableSchema.ufn_GetCreateTableScript\n"
        + "         (\n"
        + "         @schema_name NVARCHAR(128),\n"
        + "         @table_name NVARCHAR(128)\n"
        + "         )\n"
        + "         RETURNS NVARCHAR(MAX)\n"
        + "         AS\n"
        + "         BEGIN\n"
        + "         DECLARE @CreateTableScript NVARCHAR(MAX);\n"
        + "         DECLARE @IndexScripts NVARCHAR(MAX) = '';\n"
        + "         DECLARE @ColumnDescriptions NVARCHAR(MAX) = N'';\n"
        + "         SELECT\n"
        + "         @CreateTableScript = CONCAT(\n"
        + "         'CREATE TABLE [', s.name, '].[' , t.name, '] (',\n"
        + "         STUFF(\n"
        + "         (\n"
        + "         SELECT\n"
        + "         ', [' + c.name + '] ' + tp.name +\n"
        + "         CASE WHEN tp.name IN ('varchar', 'nvarchar', 'char', 'nchar') THEN\n"
        + "         '(' + IIF(c.max_length = -1, 'MAX', CAST(c.max_length AS NVARCHAR(10))) + ')'\n"
        + "         WHEN tp.name IN ('decimal', 'numeric') THEN\n"
        + "         '(' + CAST(c.precision AS NVARCHAR(10)) + ', ' + CAST(c.scale AS NVARCHAR(10)) + ')'\n"
        + "         ELSE\n"
        + "         ''\n"
        + "         END + ' ' +\n"
        + "         CASE WHEN c.is_nullable = 1 THEN 'NULL' ELSE 'NOT NULL' END\n"
        + "         FROM\n"
        + "         sys.columns c\n"
        + "         JOIN sys.types tp ON c.user_type_id = tp.user_type_id\n"
        + "         WHERE\n"
        + "         c.object_id = t.object_id\n"
        + "         FOR XML PATH(''), TYPE\n"
        + "         ).value('/', 'nvarchar(max)'), 1, 1, ''),\n"
        + "         ');'\n"
        + "         )\n"
        + "         FROM\n"
        + "         sys.tables t\n"
        + "         JOIN sys.schemas s ON t.schema_id = s.schema_id\n"
        + "         WHERE\n"
        + "         t.name = @table_name\n"
        + "         AND s.name = @schema_name;\n"
        + "\n"
        + "         SELECT @IndexScripts = @IndexScripts + 'CREATE ' +\n"
        + "         CASE WHEN i.is_unique = 1 THEN 'UNIQUE ' ELSE '' END +\n"
        + "         i.type_desc + ' INDEX [' + i.name + '] ON [' + s.name + '].[' + t.name + '] (' +\n"
        + "         STUFF(\n"
        + "         (\n"
        + "         SELECT\n"
        + "         ', [' + c.name + ']' + CASE WHEN ic.is_descending_key = 1 THEN ' DESC' ELSE ' ASC' END\n"
        + "         FROM\n"
        + "         sys.index_columns ic\n"
        + "         JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id\n"
        + "         WHERE\n"
        + "         ic.object_id = i.object_id\n"
        + "         AND ic.index_id = i.index_id\n"
        + "         ORDER BY\n"
        + "         ic.key_ordinal  FOR XML PATH('')\n"
        + "         ), 1, 1, '') + ')' +\n"
        + "         CASE WHEN i.has_filter = 1 THEN ' WHERE ' + i.filter_definition ELSE '' END + ';' + CHAR(13) + "
        + "CHAR(10)\n"
        + "         FROM\n"
        + "         sys.indexes i\n"
        + "         JOIN sys.tables t ON i.object_id = t.object_id\n"
        + "         JOIN sys.schemas s ON t.schema_id = s.schema_id\n"
        + "         WHERE\n"
        + "         i.type > 0\n"
        + "         -- AND i.is_primary_key = 0\n"
        + "         AND t.name = @table_name\n"
        + "         AND s.name = @schema_name;\n"
        + "\n"
        + "         SELECT\n"
        + "         @ColumnDescriptions += 'EXEC sp_addextendedproperty @name=N''MS_Description'', @value=N''' + CAST"
        + "(p.value AS NVARCHAR(MAX)) + ''', @level0type=N''SCHEMA'', @level0name=N''' + @schema_name + ''', "
        + "@level1type=N''TABLE'', @level1name=N''' + @table_name + ''', @level2type=N''COLUMN'', @level2name=N''' + "
        + "c.name + ''';' + CHAR(13) + CHAR(10)\n"
        + "         FROM\n"
        + "         sys.extended_properties p\n"
        + "         JOIN sys.columns c ON p.major_id = c.object_id AND p.minor_id = c.column_id\n"
        + "         JOIN sys.tables t ON c.object_id = t.object_id\n"
        + "         JOIN sys.schemas s ON t.schema_id = s.schema_id\n"
        + "         WHERE\n"
        + "         p.class = 1\n"
        + "         AND t.name = @table_name\n"
        + "         AND s.name = @schema_name;\n"
        + "\n"
        + "         SET @CreateTableScript = @CreateTableScript + CHAR(13) + CHAR(10) + @IndexScripts +  CHAR(13) + "
        + "CHAR(10)+ @ColumnDescriptions+  CHAR(10);\n"
        + "         RETURN @CreateTableScript;\n"
        + "         END";

    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.SQLSERVER;
    }

    @Override
    public String tableDDL(String databaseName, String schemaName, String tableName) {
        DataSource.getInstance().executeSql(functionSQL.replace("tableSchema", schemaName), resultSet -> {
            return null;
        });
        String ddlSql = "SELECT " + databaseName + ".ufn_GetCreateTableScript('" + schemaName + "', '" + tableName
            + "') AS sql";
        return DataSource.getInstance().executeSql(ddlSql, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getString("sql");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
}