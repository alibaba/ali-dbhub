/** Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved. */
package com.alibaba.dbhub.server.domain.support.dialect.postgresql.model;

import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jipengfei
 * @version : PostgresqlTableIndex.java, v 0.1 2022年12月11日 15:27 jipengfei Exp $
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostgresqlTableIndex extends TableIndex {
    private String tableSchemaName;
    private long oid;
    private String accessMethod;
    private String tableSpace;
    private int fillRate;
    private boolean unique;
    private boolean cluster;
    private boolean parallelBuild;
    private String constraint;
    private int fieldCount;
    private List<PostgresqlTableIndexField> fields;
}
