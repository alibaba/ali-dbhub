package com.alibaba.ctoo.opensource.domain.core.metaq.listener;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.metaq.ProjectDocumentMetaqDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.RefreshDocumentParam;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainManageService;
import com.alibaba.easytools.spring.metaq.AbstractEasyMessageListenerConcurrentlySingle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 项目文档监听器
 *
 * @author qiuyuyu
 * @date 2022/04/11
 */
@Component
@Slf4j
public class ProjectDocumentListener extends AbstractEasyMessageListenerConcurrentlySingle<ProjectDocumentMetaqDTO> {
    @Resource
    private ProjectDomainManageService projectDomainManageService;

    @Override
    public void doConsume(ProjectDocumentMetaqDTO projectDocumentMetaqDTO) {
        RefreshDocumentParam param = new RefreshDocumentParam();
        param.setProjectId(projectDocumentMetaqDTO.getProjectId());
        //刷新项目文档
        projectDomainManageService.refreshDocumentCallback(param);
    }
}
