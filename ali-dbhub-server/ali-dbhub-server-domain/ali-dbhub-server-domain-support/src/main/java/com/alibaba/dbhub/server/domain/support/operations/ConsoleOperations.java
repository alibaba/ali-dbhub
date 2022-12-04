
package com.alibaba.dbhub.server.domain.support.operations;

import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;

/**
 * 控制台服务
 *
 * @author Jiaju Zhuang
 */
public interface ConsoleOperations {

    /**
     * 创建数据库控制台
     *
     * @param param
     * @return
     */
    void create(ConsoleCreateParam param);

    /**
     * 关闭数据库控制台
     *
     * @param param
     * @return
     */
    void close(ConsoleCloseParam param);

}
