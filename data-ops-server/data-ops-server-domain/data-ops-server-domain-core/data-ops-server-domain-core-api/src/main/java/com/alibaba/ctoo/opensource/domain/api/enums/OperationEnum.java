package com.alibaba.ctoo.opensource.domain.api.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * 操作枚举
 *
 * @author 是仪
 */
@Getter
public enum OperationEnum implements BaseEnum<String> {
    /**
     * 新增
     */
    CREATE("新增"),

    /**
     * 修改
     */
    UPDATE("修改"),

    /**
     * 删除
     */
    DELETE("删除"),

    /**
     * 上架
     */
    SHELF("上架"),

    /**
     * 下架
     */
    UN_SHELF("上架"),

    ;

    final String description;

    OperationEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }

}
