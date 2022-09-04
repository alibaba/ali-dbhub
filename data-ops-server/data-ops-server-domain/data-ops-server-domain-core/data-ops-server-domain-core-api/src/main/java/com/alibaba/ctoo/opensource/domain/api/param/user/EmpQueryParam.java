package com.alibaba.ctoo.opensource.domain.api.param.user;

import lombok.Data;

/**
 * 员工信息查询参数
 *
 * @author qiuyuyu
 * @date 2022/03/04
 */
@Data
public class EmpQueryParam {
    /**
     * 关键字
     */
    private String searchKey;
}
