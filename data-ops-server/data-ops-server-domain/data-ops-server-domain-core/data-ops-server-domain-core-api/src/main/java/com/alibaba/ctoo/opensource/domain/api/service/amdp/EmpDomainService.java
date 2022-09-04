package com.alibaba.ctoo.opensource.domain.api.service.amdp;

import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.EmpQueryParam;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * @author qiuyuyu
 * @date 2022/03/22
 */
public interface EmpDomainService {
    /**
     * 查询员工信息
     *
     * @param param
     * @return
     */
    ListResult<UserDTO> queryByParam(EmpQueryParam param);
}
