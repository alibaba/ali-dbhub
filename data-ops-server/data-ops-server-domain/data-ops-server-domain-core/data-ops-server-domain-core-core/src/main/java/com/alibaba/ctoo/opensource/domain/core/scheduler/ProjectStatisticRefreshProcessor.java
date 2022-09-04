package com.alibaba.ctoo.opensource.domain.core.scheduler;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectGeneralStatisticUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainManageService;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectGeneralStatisticDomainService;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.CustomProjectStatisticDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomProjectMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.spring.scheduler.AbstractBaseJavaProcessor;
import com.alibaba.schedulerx.worker.domain.JobContext;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Component;

/**
 * @author 知闰
 * @date 2022/03/24
 */
@Component
public class ProjectStatisticRefreshProcessor extends AbstractBaseJavaProcessor {

    @Resource
    private ProjectDomainManageService projectDomainManageService;
    @Resource
    private ProjectGeneralStatisticDomainService projectGeneralStatisticDomainService;
    @Resource
    private CustomProjectMapper customProjectMapper;
    @Resource
    private ProjectMapper projectMapper;

    @Override
    public void doProcess(JobContext jobContext) {

        // 更新全部数据
        updateProjectStatistic(3L, null);
        // 更新特定的项目数据
        ListResult<Long> projectIdListResult = projectDomainManageService.getDonateProjectIdList();
        List<Long> donateProjectIdList = projectIdListResult.getData();
        if (CollectionUtil.isNotEmpty(donateProjectIdList)) {
            updateProjectStatistic(4L, donateProjectIdList);

        }

    }

    private void updateProjectStatistic(Long id, List<Long> projectIds) {

        // 获取fork,star总数
        CustomProjectStatisticDO allStatistic = customProjectMapper.selectInDataProjectStatistic(projectIds);
        Integer forkCount = allStatistic.getForkCount();
        Integer starCount = allStatistic.getStarCount();
        // 项目数量
        Integer projectCount;
        if (CollectionUtil.isNotEmpty(projectIds)) {
            // 统计根据特定项目的数据
            projectCount = projectIds.size();
        } else {
            // 统计要展示在主页数据的项目
            ProjectParam projectParam = new ProjectParam();
            projectParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            projectCount = Math.toIntExact(projectMapper.countByParam(projectParam));
        }

        ProjectGeneralStatisticUpdateParam param = new ProjectGeneralStatisticUpdateParam();
        param.setId(id);
        param.setForkCount(forkCount);
        //param.setStarCount(starCount);
        param.setProjectCount(projectCount);
        projectGeneralStatisticDomainService.updateProjectStatistic(param);
    }
}
