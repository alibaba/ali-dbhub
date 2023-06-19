/** Alipay.com Inc. Copyright (c) 2004-2022 All Rights Reserved. */
package com.alibaba.dbhub.server.domain.support.dialect.postgresql.model;

import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jipengfei
 * @version : PostgresqlColumn.java, v 0.1 2022年12月11日 14:38 jipengfei Exp $
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostgresqlColumn extends TableColumn implements Serializable {
    @Serial private static final long serialVersionUID = 6895029737377229332L;
    private Integer ordinalPosition;
    private boolean isArray;
    private Integer precision;
    private Integer scale;
    private String sortRule;
    private Integer attinhcount;
}
