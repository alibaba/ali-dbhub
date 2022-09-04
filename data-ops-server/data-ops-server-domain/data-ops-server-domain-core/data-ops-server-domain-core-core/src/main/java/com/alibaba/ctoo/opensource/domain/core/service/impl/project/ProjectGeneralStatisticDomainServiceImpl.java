package com.alibaba.ctoo.opensource.domain.core.service.impl.project;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectGeneralStatisticUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectGeneralStatisticDomainService;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectGeneralStatisticDO;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectGeneralStatisticMapper;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

import org.springframework.stereotype.Service;

/**
 * @author 知闰
 * @date 2022/05/16
 */
@Service
public class ProjectGeneralStatisticDomainServiceImpl implements ProjectGeneralStatisticDomainService {

    @Resource
    private ProjectGeneralStatisticMapper projectGeneralStatisticMapper;

    @Override
    public ActionResult updateProjectStatistic(ProjectGeneralStatisticUpdateParam param) {
        Context context = ContextUtils.getContext();

        ProjectGeneralStatisticDO projectGeneralStatisticDO = projectGeneralStatisticMapper.selectByPrimaryKey(
            param.getId());
        if (projectGeneralStatisticDO == null) {
            projectGeneralStatisticDO = new ProjectGeneralStatisticDO();
            projectGeneralStatisticDO.setCreator(context.getUserId());
            projectGeneralStatisticDO.setModifier(context.getUserId());
            projectGeneralStatisticDO.setTenantId(context.getTenantId());
        }
        projectGeneralStatisticDO.setForkCount(param.getForkCount());
        //projectGeneralStatisticDO.setStarCount(param.getStarCount());
        projectGeneralStatisticDO.setProjectCount(param.getProjectCount());

        if (projectGeneralStatisticDO.getId() == null) {
            projectGeneralStatisticDO.setId(param.getId());
            projectGeneralStatisticMapper.insertSelective(projectGeneralStatisticDO);
        } else {
            projectGeneralStatisticDO.setModifier(context.getUserId());
            projectGeneralStatisticMapper.updateByPrimaryKeySelective(projectGeneralStatisticDO);
        }
        return ActionResult.isSuccess();
    }
}
