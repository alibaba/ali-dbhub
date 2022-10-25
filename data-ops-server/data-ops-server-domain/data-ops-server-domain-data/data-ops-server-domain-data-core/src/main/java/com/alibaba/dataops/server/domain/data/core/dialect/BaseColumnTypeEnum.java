package com.alibaba.dataops.server.domain.data.core.dialect;

import com.alibaba.dataops.server.domain.data.api.enums.ColumnTypeEnum;
import com.alibaba.dataops.server.tools.base.enums.BaseEnum;

/**
 * 列的类型
 *
 * @author Jiaju Zhuang
 */
public interface BaseColumnTypeEnum extends BaseEnum<String> {

    /**
     * 返回列的类型
     *
     * @return
     */
    ColumnTypeEnum getColumnType();
}
