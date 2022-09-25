
package com.alibaba.dataops.server.domain.data.api.service;

import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;

/**
 * 控制台服务
 *
 * @author Jiaju Zhuang
 */
public interface ConsoleDataService {

    /**
     * 创建数据库控制台
     *
     * @param param
     * @return
     */
    ActionResult create(ConsoleCreateParam param);

    /**
     * 关闭数据库控制台
     *
     * @param param
     * @return
     */
    ActionResult close(ConsoleCloseParam param);

}
