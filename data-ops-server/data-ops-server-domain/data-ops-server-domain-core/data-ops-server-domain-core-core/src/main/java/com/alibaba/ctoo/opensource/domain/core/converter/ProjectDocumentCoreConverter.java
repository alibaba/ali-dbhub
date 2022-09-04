package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectDocumentDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentUpdateParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDomainDO;

import org.mapstruct.Mapper;

/**
 * 项目文档
 *
 * @author 是仪
 */
@Mapper(componentModel = "spring")
public abstract class ProjectDocumentCoreConverter {

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<ProjectDocumentDTO> do2dto(List<ProjectDocumentDO> list);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract ProjectDocumentDO param2do(ProjectDocumentCreateParam param, String tenantId, String creator,
        String modifier);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract ProjectDocumentDO param2do(ProjectDocumentUpdateParam param, String tenantId, String modifier);

    /**
     * 转换¬
     * @param projectDocumentId
     * @param domain
     * @param tenantId
     * @param creator
     * @param modifier
     * @return
     */
    public abstract ProjectDocumentDomainDO param2doDomain(Long projectDocumentId, String domain, String tenantId,
        String creator, String modifier);
}
