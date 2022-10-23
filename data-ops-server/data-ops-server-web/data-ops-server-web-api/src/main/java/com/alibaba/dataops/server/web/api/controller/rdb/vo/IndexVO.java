package com.alibaba.dataops.server.web.api.controller.rdb.vo;

import lombok.Data;

/**
 * @author moji
 * @version IndexVO.java, v 0.1 2022年09月16日 17:47 moji Exp $
 * @date 2022/09/16
 */
@Data
public class IndexVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 包含列
     */
    private String columns;

}
