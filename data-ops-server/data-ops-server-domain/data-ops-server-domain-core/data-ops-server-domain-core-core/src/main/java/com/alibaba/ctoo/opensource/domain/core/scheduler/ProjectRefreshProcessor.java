package com.alibaba.ctoo.opensource.domain.core.scheduler;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainManageService;
import com.alibaba.easytools.spring.scheduler.AbstractBaseJavaProcessor;
import com.alibaba.schedulerx.worker.domain.JobContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 项目数据刷新
 *
 * @author 知闰
 * @date 2022/04/02
 */
@Slf4j
@Component
public class ProjectRefreshProcessor extends AbstractBaseJavaProcessor {

    @Resource
    private ProjectDomainManageService projectDomainManageService;

    @Override
    public void doProcess(JobContext context) {
        projectDomainManageService.refreshProject();
    }

}
