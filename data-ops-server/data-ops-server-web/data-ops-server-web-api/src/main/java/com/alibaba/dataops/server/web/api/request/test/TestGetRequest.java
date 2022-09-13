package com.alibaba.dataops.server.web.api.request.test;

import com.alibaba.dataops.server.tools.base.wrapper.request.PageQueryRequest;

import lombok.Data;

/**
 * 测试查询请求
 *
 * @author 是仪
 */
@Data
public class TestGetRequest extends PageQueryRequest {
    /**
     * 名字信息
     */
    private String name;
}
