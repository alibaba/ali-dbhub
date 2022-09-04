package com.alibaba.ctoo.opensource.domain.api.service;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectDocumentDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentSelector;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentUpdateParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;

/**
 * 项目文档服务
 *
 * @author 是仪
 */
public interface ProjectDocumentDomainService {
    /**
     * 查询文档信息
     *
     * @param id
     * @param selector
     * @return
     */
    DataResult<ProjectDocumentDTO> get(Long id, ProjectDocumentSelector selector);

    /**
     * 文档详情
     *
     * @param projectId
     * @param selector
     * @return
     */
    DataResult<ProjectDocumentDTO> queryByProjectId(Long projectId, ProjectDocumentSelector selector);

    /**
     * 查询文档信息
     *
     * @param param
     * @param selector
     * @return
     */
    DataResult<ProjectDocumentDTO> query(ProjectDocumentQueryParam param, ProjectDocumentSelector selector);

    /**
     * 创建
     *
     * @param param
     * @return
     */
    DataResult<Long> create(ProjectDocumentCreateParam param);

    /**
     * 修改
     *
     * @param param
     * @return
     */
    ActionResult update(ProjectDocumentUpdateParam param);

}
