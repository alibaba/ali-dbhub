package com.alibaba.dataops.server.domain.data.core.dialect.common.enums;

import com.alibaba.dataops.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dataops.server.tools.base.enums.BaseEnum;

/**
 * 索引的类型
 *
 * @author Jiaju Zhuang
 */
public interface BaseIndexTypeEnum extends BaseEnum<String> {

    /**
     * 返回索引的类型
     *
     * @return
     */
    IndexTypeEnum getIndexType();
}
