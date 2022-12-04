package com.alibaba.dbhub.server.domain.api.service;

import com.alibaba.dbhub.server.domain.api.param.UserExecutedDdlPageQueryParam;
import com.alibaba.dbhub.server.domain.api.model.UserExecutedDdlDTO;
import com.alibaba.dbhub.server.domain.api.param.UserExecutedDdlCreateParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * 用户执行ddl
 *
 * @author moji
 * @version UserExecutedDdlCoreService.java, v 0.1 2022年09月23日 17:35 moji Exp $
 * @date 2022/09/23
 */
public interface UserExecutedDdlCoreService {

    /**
     * 创建用户执行的ddl记录
     *
     * @param param
     * @return
     */
    DataResult<Long> create(UserExecutedDdlCreateParam param);

    /**
     * 查询用户执行的ddl记录
     *
     * @param param
     * @return
     */
    PageResult<UserExecutedDdlDTO> queryPage(UserExecutedDdlPageQueryParam param);
}
