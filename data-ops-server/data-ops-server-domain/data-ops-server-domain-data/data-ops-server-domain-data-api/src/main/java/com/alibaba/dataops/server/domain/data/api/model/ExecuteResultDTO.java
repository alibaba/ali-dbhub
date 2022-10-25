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
     * 是否成功标志位
     */
    private Boolean success;

    /**
     * 失败消息提示
     * 只有失败的情况下会有
     */
    private String message;

    /**
     * 执行的sql
     */
    private String sql;

    /**
     * 描述
     */
    private String description;

    /**
     * 修改行数 查询sql不会返回
     */
    private Integer updateCount;

    /**
     * 展示头的列表
     */
    private List<CellDTO> headerList;

    /**
     * 数据的列表
     */
    private List<List<CellDTO>> dataList;

    /**
     * 分页编码
     * 只有select语句才有
     */
    private Integer pageNo;

    /**
     * 分页大小
     * 只有select语句才有
     */
    private Integer pageSize;

    /**
     * 总的大小
     * 只有select语句才有
     */
    private Long total;
}
