package com.alibaba.dbhub.server.domain.core.api.service;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.core.api.model.UserSavedDdlDTO;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlCreateParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlPageQueryParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlUpdateParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * 用户保存ddl
 *
 * @author moji
 * @version UserSavedDdlCoreService.java, v 0.1 2022年09月23日 17:35 moji Exp $
 * @date 2022/09/23
 */
public interface UserSavedDdlCoreService {

    /**
     * 保存用户的ddl
     *
     * @param param
     * @return
     */
    DataResult<Long> create(UserSavedDdlCreateParam param);

    /**
     * 更新用户的ddl
     *
     * @param param
     * @return
     */
    ActionResult update(UserSavedDdlUpdateParam param);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    ActionResult delete(@NotNull Long id);

    /**
     * 查询用户执行的ddl记录
     *
     * @param param
     * @return
     */
    PageResult<UserSavedDdlDTO> queryPage(UserSavedDdlPageQueryParam param);
}
