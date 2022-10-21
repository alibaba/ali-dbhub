package com.alibaba.dataops.server.domain.data.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 执行结果
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ExecuteResultDTO {

    /**
     * 执行的sql
     */
    private String sql;

    /**
     * 描述
     */
    private String description;

    /**
     * 展示头的列表
     */
    private List<CellDTO> headerList;

    /**
     * 数据的列表
     */
    private List<List<CellDTO>> dataList;
}
