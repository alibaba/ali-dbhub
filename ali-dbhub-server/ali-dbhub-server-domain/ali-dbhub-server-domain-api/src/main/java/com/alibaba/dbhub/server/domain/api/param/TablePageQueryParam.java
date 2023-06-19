package com.alibaba.dbhub.server.domain.api.param;

import com.alibaba.dbhub.server.tools.base.wrapper.param.PageQueryParam;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 分页查询表信息
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TablePageQueryParam extends PageQueryParam {
    private static final long serialVersionUID = 8054519332890887747L;
    /** 对应数据库存储的来源id */
    @NotNull private Long dataSourceId;

    /** 对应的连接数据库名称 */
    @NotNull private String databaseName;

    /** 表名 */
    private String tableName;

    /** */
    private String schemaName;
}
