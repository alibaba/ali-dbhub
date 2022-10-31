package com.alibaba.dataops.server.domain.data.core.dialect.mysq;

import com.alibaba.dataops.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dataops.server.domain.data.core.dialect.common.enums.BaseIndexTypeEnum;

import lombok.Getter;

/**
 * 列的类型
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum MysqlIndexTypeEnum implements BaseIndexTypeEnum {
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

    MysqlIndexTypeEnum(String code, IndexTypeEnum indexType) {
        this.code = code;
        this.indexType = indexType;
    }

    @Override
    public String getDescription() {
        return getCode();
    }

}
