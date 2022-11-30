package com.alibaba.dbhub.server.domain.api.test;

import com.alibaba.dbhub.server.tools.base.wrapper.param.PageQueryParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 测试新增参数
 *
 * @author Jiaju Zhuang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TestCreateParam {

    /**
     * 姓名
     */
    public String name;
}
