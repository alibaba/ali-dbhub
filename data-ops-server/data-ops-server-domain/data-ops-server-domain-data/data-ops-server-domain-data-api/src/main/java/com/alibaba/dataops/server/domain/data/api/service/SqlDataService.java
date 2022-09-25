
package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

/**
 * sql解析服务
 *
 * @author Jiaju Zhuang
 */
public interface SqlDataService {

    /**
     * 创建数据库控制台
     *
     * @param param
     * @return
     */
    ActionResult parse(ConsoleCreateParam param);


}
