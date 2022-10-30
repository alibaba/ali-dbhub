package com.alibaba.dataops.server.domain.data.core.dialect.common.enums;

import com.alibaba.dataops.server.domain.data.api.enums.CollationEnum;
import com.alibaba.dataops.server.tools.base.enums.BaseEnum;

/**
 * 排序类型
 *
 * @author Jiaju Zhuang
 */
public interface BaseCollationEnum extends BaseEnum<String> {

    /**
     * 返回排序类型
     *
     * @return
     */
    CollationEnum getCollation();
}
