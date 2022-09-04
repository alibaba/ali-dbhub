package com.alibaba.ctoo.opensource.domain.core.service.impl.amdp;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.EmpQueryParam;
import com.alibaba.ctoo.opensource.domain.api.service.amdp.EmpDomainService;
import com.alibaba.ctoo.opensource.integration.service.EmpIntegrationService;
import com.alibaba.easytools.base.wrapper.result.ListResult;

import org.springframework.stereotype.Service;

/**
 * 员工信息服务
 *
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Service
public class EmpDomainServiceImpl implements EmpDomainService {
    @Resource
    private EmpIntegrationService empIntegrationService;

    @Override
    public ListResult<UserDTO> queryByParam(EmpQueryParam param) {
        return empIntegrationService.queryByParam(param);
    }
}
