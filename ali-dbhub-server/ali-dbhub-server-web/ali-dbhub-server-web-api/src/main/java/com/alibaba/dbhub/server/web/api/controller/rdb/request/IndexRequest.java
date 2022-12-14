package com.alibaba.dbhub.server.web.api.controller.rdb.request;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.enums.IndexTypeEnum;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ColumnVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 索引
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IndexRequest {

    /**
     * 索引名称
     */
    private String name;

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
