package com.alibaba.dbhub.server.web.api.controller.rdb.vo;

import java.util.List;

import com.alibaba.dbhub.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dbhub.server.tools.base.enums.YesOrNoEnum;

import lombok.Data;

/**
 * @author moji
 * @version IndexVO.java, v 0.1 2022年09月16日 17:47 moji Exp $
 * @date 2022/09/16
 */
@Data
public class IndexVO {

    /**
     * 包含列
     */
    private String columns;

    /**
     * 索引名称
     */
    private String name;

    /**
     * 是否主键
     *
     * @see YesOrNoEnum
     */
    private String primary;

    /**
     * 所以类型
     *
     * @see IndexTypeEnum
     */
    private String type;

    /**
     * 注释
     */
    private String comment;

    /**
     * 索引包含的列
     */
    private List<ColumnVO> columnList;
}
