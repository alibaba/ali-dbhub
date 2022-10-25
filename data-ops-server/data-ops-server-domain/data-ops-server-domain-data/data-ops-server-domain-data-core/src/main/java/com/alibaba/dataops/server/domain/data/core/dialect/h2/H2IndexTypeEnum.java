package com.alibaba.dataops.server.domain.data.core.dialect.h2;

import com.alibaba.dataops.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dataops.server.domain.data.core.dialect.BaseIndexTypeEnum;

import lombok.Getter;

/**
 * 列的类型
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum H2IndexTypeEnum implements BaseIndexTypeEnum {
    /**
     * PRIMARY_KEY
     */
    PRIMARY_KEY("PRIMARY KEY", IndexTypeEnum.PRIMARY_KEY),
    /**
     * UNIQUE INDEX
     */
    UNIQUE("UNIQUE INDEX", IndexTypeEnum.UNIQUE),
    ;

    final String code;
    final IndexTypeEnum indexType;

    H2IndexTypeEnum(String code, IndexTypeEnum indexType) {
        this.code = code;
        this.indexType = indexType;
    }

    @Override
    public String getDescription() {
        return getCode();
    }

}
