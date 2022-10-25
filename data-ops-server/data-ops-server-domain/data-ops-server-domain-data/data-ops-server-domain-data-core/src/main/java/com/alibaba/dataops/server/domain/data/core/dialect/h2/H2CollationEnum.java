package com.alibaba.dataops.server.domain.data.core.dialect.h2;

import com.alibaba.dataops.server.domain.data.api.enums.CollationEnum;
import com.alibaba.dataops.server.domain.data.core.dialect.BaseCollationEnum;

import lombok.Getter;

/**
 * 排序枚举
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum H2CollationEnum implements BaseCollationEnum {
    /**
     * ASC
     */
    ASC("ASC", CollationEnum.ASC),
    /**
     * DESC
     */
    DESC("DESC", CollationEnum.DESC),
    ;

    final String code;
    final CollationEnum collation;

    H2CollationEnum(String code, CollationEnum collation) {
        this.code = code;
        this.collation = collation;
    }

    @Override
    public String getDescription() {
        return getCode();
    }

}
