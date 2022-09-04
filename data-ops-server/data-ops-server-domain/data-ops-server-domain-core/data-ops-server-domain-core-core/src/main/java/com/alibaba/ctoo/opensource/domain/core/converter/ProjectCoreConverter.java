package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.Date;
import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateOrUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectPageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.RefreshProjectParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomProjectSearchParam;
import com.alibaba.ctoo.opensource.integration.dto.ProjectIntegrationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Mapper(componentModel = "spring",imports = Date.class)
public abstract class ProjectCoreConverter {

    /**
     * 转换器
     *
     * @param projectDO
     * @return
     */
    public abstract ProjectDTO doToDto(ProjectDO projectDO);

    /**
     * 转换器
     *
     * @param param
     * @return
     */
    public abstract ProjectDO paramToDo(ProjectCreateParam param);

    /**
     * 转换器
     *
     * @param param
     * @return
     */
    public abstract ProjectDO paramToDo(ProjectUpdateParam param);

    /**
     * 转换器
     *
     * @param param
     * @return
     */
    public abstract ProjectDO paramToDo(ProjectCreateOrUpdateParam param);

    /**
     * do转dto list转换
     *
     * @param projectDOList
     * @return
     */
    public abstract List<ProjectDTO> doToDto(List<ProjectDO> projectDOList);

    /**
     *  参数转换器
     * @param queryParam
     * @return
     */
    public abstract CustomProjectSearchParam queryParamToSearchParam(ProjectPageQueryParam queryParam);

    /**
     * 转换器
     *
     * @param dto
     * @return
     */
    public abstract RefreshProjectParam dtoToParam(ProjectDTO dto);

    @Mappings({
        @Mapping(target = "organization",source = "org"),
        @Mapping(target = "gmtRefresh",expression = "java(new Date())"),
        @Mapping(target = "forkCount",source = "forksCount")
    })
    public abstract ProjectCreateOrUpdateParam projectIntegrationToParam(ProjectIntegrationDTO projectIntegrationDTO);
}
