package com.alibaba.ctoo.opensource.domain.api.service.project;

import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectGeneralStatisticUpdateParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

/**
 * 项目统计数据服务
 * @author 知闰
 * @date 2022/05/16
 */
public interface ProjectGeneralStatisticDomainService {

    /**
     * 更新项目统计数据
     * @param param
     * @return
     */
    ActionResult updateProjectStatistic(ProjectGeneralStatisticUpdateParam param);
}
