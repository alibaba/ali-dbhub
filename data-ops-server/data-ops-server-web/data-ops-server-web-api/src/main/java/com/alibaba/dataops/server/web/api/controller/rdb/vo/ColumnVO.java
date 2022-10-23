package com.alibaba.dataops.server.web.api.controller.rdb.vo;

import lombok.Data;

/**
 * @author moji
 * @version TableVO.java, v 0.1 2022年09月16日 17:16 moji Exp $
 * @date 2022/09/16
 */
@Data
public class ColumnVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private String type;

}
