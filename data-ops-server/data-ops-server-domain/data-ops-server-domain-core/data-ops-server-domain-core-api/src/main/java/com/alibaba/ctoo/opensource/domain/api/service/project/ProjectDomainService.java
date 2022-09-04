package com.alibaba.ctoo.opensource.domain.api.service.project;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateOrUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectPageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectSelector;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectUpdateParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;

/**
 * 项目服务
 *
 * @author 知闰
 * @date 2022/03/22
 */
public interface ProjectDomainService {

    /**
     * 根据主键ID查询
     *
     * @param id       主键ID
     * @param selector 查询选择器
     * @return {@link ProjectDTO}
     */
    DataResult<ProjectDTO> get(Long id, ProjectSelector selector);
    /**
     * 根据主键ID查询
     *
     * @param id       主键ID
     * @param selector 查询选择器
     * @return {@link ProjectDTO}
     */
    DataResult<ProjectDTO> query(Long id, ProjectSelector selector);

    /**
     * 根据完整项目名查询项目信息
     *
     * @param fullName 项目完整名称
     * @param selector 查询选择器
     * @return {@link ProjectDTO}
     */
    DataResult<ProjectDTO> queryByFullName(String fullName, ProjectSelector selector);

    /**
     * 创建项目
     *
     * @param param {@link ProjectCreateParam} 创建参数
     * @return 主键ID
     */
    DataResult<Long> create(ProjectCreateParam param);

    /**
     * 更新项目
     *
     * @param param {@link ProjectUpdateParam }
     * @return {@link ActionResult}
     */
    ActionResult update(ProjectUpdateParam param);

    /**
     * 创建项目/更新项目，以主键ID作为创建或更新的依据
     *
     * @param param {@link ProjectCreateOrUpdateParam} 创建参数
     * @return 主键ID
     */
    DataResult<Long> createOrUpdate(ProjectCreateOrUpdateParam param);

    /**
     * 分页查询
     *
     * @param param    {@link ProjectPageQueryParam}
     * @param selector 查询选择器
     * @return {@link PageResult<ProjectDTO>}
     */
    PageResult<ProjectDTO> queryPage(ProjectPageQueryParam param, ProjectSelector selector);

    /**
     * 查询项目列表
     *
     * @param param    {@link ProjectQueryParam}
     * @param selector 查询选择器
     * @return {@link ListResult<ProjectDTO>}
     */
    ListResult<ProjectDTO> queryList(ProjectQueryParam param, ProjectSelector selector);
}
