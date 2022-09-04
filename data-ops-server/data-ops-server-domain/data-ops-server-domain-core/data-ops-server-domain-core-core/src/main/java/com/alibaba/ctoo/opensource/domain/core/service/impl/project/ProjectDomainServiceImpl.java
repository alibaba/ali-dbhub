package com.alibaba.ctoo.opensource.domain.core.service.impl.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.ctoo.opensource.common.config.dynamic.DynamicDiamondDataCallback;
import com.alibaba.ctoo.opensource.common.config.dynamic.DynamicMainProjectConfig;
import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.common.util.EnumUtils;
import com.alibaba.ctoo.opensource.domain.api.enums.ProjectTagTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.model.ProjectDTO;
import com.alibaba.ctoo.opensource.domain.api.model.TagDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateOrUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectPageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectSelector;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagSelector;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.tag.TagDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ProjectCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectHistoryDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectParam.Criteria;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectParam.OrderCondition;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectParam.SortType;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectTagDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectTagParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomProjectSearchParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomProjectMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectHistoryMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectTagMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.excption.SystemException;
import com.alibaba.easytools.base.wrapper.param.OrderBy;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * project服务实现类
 *
 * @author 知闰
 * @date 2022/03/22
 */
@Slf4j
@Service
public class ProjectDomainServiceImpl implements ProjectDomainService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectTagMapper projectTagMapper;

    @Autowired
    private ProjectCoreConverter projectCoreConverter;

    @Autowired
    private TagDomainService tagDomainService;

    @Autowired
    private CustomProjectMapper customProjectMapper;

    @Autowired
    private ProjectHistoryMapper projectHistoryMapper;

    @Override
    public DataResult<ProjectDTO> get(Long id, ProjectSelector selector) {
        ProjectDTO project = query(id, selector).getData();
        if (project == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND.getCode());
        }
        return DataResult.of(project);
    }

    @Override
    public DataResult<ProjectDTO> query(Long id, ProjectSelector selector) {

        ProjectDO projectDO = projectMapper.selectByPrimaryKey(id);
        if (null == projectDO) {
            return DataResult.empty();
        }
        ProjectDTO dto = projectCoreConverter.doToDto(projectDO);
        fillProjectTag(Collections.singletonList(dto), selector);
        return DataResult.of(dto);
    }

    @Override
    public DataResult<ProjectDTO> queryByFullName(String fullName, ProjectSelector selector) {
        // 构建查询参数
        ProjectParam param = new ProjectParam();
        param.setPageSize(1);
        param.createCriteria().
            andFullNameEqualTo(fullName).
            andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());

        List<ProjectDO> list = projectMapper.selectByParam(param);

        return CollectionUtil.isEmpty(list) ? DataResult.empty() : DataResult.of(
            projectCoreConverter.doToDto(list.get(0)));

    }

    @Override
    public DataResult<Long> create(ProjectCreateParam param) {
        // 创建项目
        ProjectDO projectDO = projectCoreConverter.paramToDo(param);
        Context context = ContextUtils.getContext();
        projectDO.setCreator(context.getUserId());
        projectDO.setTenantId(context.getTenantId());
        projectDO.setModifier(context.getUserId());
        projectMapper.insertSelective(projectDO);

        // 新增项目tag数据
        createOrUpdateProjectTag(projectDO.getId(), param.getLanguageTag(), param.getDomainTag(), param.getBasisTag(),
            context);

        // 插入项目历史数据
        createProjectHistory(projectDO, context);

        return DataResult.of(projectDO.getId());
    }

    @Override
    public ActionResult update(ProjectUpdateParam param) {
        // 更新项目
        ProjectDO projectDO = projectCoreConverter.paramToDo(param);
        Context context = ContextUtils.getContext();
        projectDO.setModifier(context.getUserId());

        // 排除要更新的项目
        List<Long> noUpdateIdList = DynamicDiamondDataCallback.dynamicConfig.getMainProject().stream().map(
            DynamicMainProjectConfig::getId).collect(Collectors.toList());
        if (noUpdateIdList.contains(projectDO.getId())) {
            projectDO.setDescription(null);
        }
        projectMapper.updateByPrimaryKeySelective(projectDO);

        // 更新项目tag数据
        if (!noUpdateIdList.contains(projectDO.getId()) && BooleanUtils.isNotFalse(param.getAllowUpdateTag())) {
            createOrUpdateProjectTag(projectDO.getId(), param.getLanguageTag(), param.getDomainTag(),
                param.getBasisTag(),
                context);
        }

        // 插入项目历史数据
        createProjectHistory(projectDO, context);

        return ActionResult.isSuccess();
    }

    @Override
    public DataResult<Long> createOrUpdate(ProjectCreateOrUpdateParam param) {
        //不更新的项目列表
        List<Long> noUpdateIdList = DynamicDiamondDataCallback.dynamicConfig.getMainProject().stream().map(
            DynamicMainProjectConfig::getId).collect(Collectors.toList());
        ProjectDO projectDO = projectCoreConverter.paramToDo(param);
        Context context = ContextUtils.getContext();
        if (null == projectDO.getId()) {
            projectDO.setCreator(context.getUserId());
            projectDO.setTenantId(context.getTenantId());
            projectDO.setModifier(context.getUserId());
            projectMapper.insertSelective(projectDO);
        } else {
            projectDO.setDescription(noUpdateIdList.contains(projectDO.getId()) ? null : projectDO.getDescription());
            projectDO.setModifier(context.getUserId());
            projectMapper.updateByPrimaryKeySelective(projectDO);
        }

        if (!noUpdateIdList.contains(projectDO.getId()) && BooleanUtils.isNotFalse(param.getAllowUpdateTag())) {
            // 新增/修改项目tag数据
            createOrUpdateProjectTag(projectDO.getId(), param.getLanguageTag(), param.getDomainTag(),
                param.getBasisTag(),
                context);
        }

        // 插入项目历史数据
        createProjectHistory(projectDO, context);

        return DataResult.of(projectDO.getId());
    }

    /**
     * 更新项目的tag数据
     *
     * @param projectId     项目ID
     * @param language      项目语言标签
     * @param domainTagList 领域标签
     * @param basisTagList  基础标签
     * @param context
     */
    private void createOrUpdateProjectTag(Long projectId, String language, List<String> domainTagList,
        List<String> basisTagList, Context context) {
        List<Long> tagIds = new ArrayList<>();
        TagSelector tagSelector = new TagSelector();
        // 获取Language的tagId
        if (StringUtils.isNotEmpty(language)) {
            tagIds.addAll(this.getTagIdList(Arrays.asList(language), ProjectTagTypeEnum.PROJECT_LANGUAGE, tagSelector));
        }
        // 获取领域realm的tagId
        if (CollectionUtil.isNotEmpty(domainTagList)) {
            tagIds.addAll(this.getTagIdList(domainTagList, ProjectTagTypeEnum.PROJECT_DOMAIN, tagSelector));
        }
        // 获取基础标签id
        if (CollectionUtil.isNotEmpty(basisTagList)) {
            tagIds.addAll(this.getTagIdList(basisTagList, ProjectTagTypeEnum.PROJECT_BASIS, tagSelector));
        }
        if (CollectionUtil.isNotEmpty(tagIds)) {
            // 查询数据库中的已存在的tagIds
            ProjectTagParam projectTagParam = new ProjectTagParam();
            ProjectTagParam.Criteria criteria = projectTagParam.createCriteria();
            criteria.andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            criteria.andProjectIdEqualTo(projectId);

            List<ProjectTagDO> dos = projectTagMapper.selectByParam(projectTagParam);

            for (ProjectTagDO projectTagDO : dos) {
                if (!tagIds.contains(projectTagDO.getTagId())) {
                    // 不存在，说明本次操作该tag被删除
                    projectTagDO.setDeletedId(projectTagDO.getId());
                    projectTagMapper.updateByPrimaryKey(projectTagDO);
                } else {
                    // 存在不再新增
                    tagIds.remove(projectTagDO.getTagId());
                }
            }

            if (CollectionUtil.isNotEmpty(tagIds)) {
                // 新增项目标签数据
                List<ProjectTagDO> records = new ArrayList<>(tagIds.size());
                for (Long tagId : tagIds) {
                    ProjectTagDO projectTagDO = new ProjectTagDO();
                    projectTagDO.setCreator(context.getUserId());
                    projectTagDO.setModifier(context.getUserId());
                    projectTagDO.setTenantId(context.getTenantId());
                    projectTagDO.setTagId(tagId);
                    projectTagDO.setProjectId(projectId);
                    projectTagDO.setDeletedId(DeletedIdEnum.NOT_DELETED.getCode());
                    projectTagDO.setGmtCreate(new Date());
                    records.add(projectTagDO);
                }
                projectTagMapper.batchInsert(records);
            }
        }
    }

    /**
     * 根据tagValue获取tagId
     *
     * @param tagValueList tag值
     * @param tagTypeEnum  标签类型
     * @param tagSelector  查询选择器
     * @return
     */
    private List<Long> getTagIdList(List<String> tagValueList, ProjectTagTypeEnum tagTypeEnum,
        TagSelector tagSelector) {
        // 查询tagValue的tagId
        TagQueryParam queryParam = new TagQueryParam();
        queryParam.setTagValueList(tagValueList);
        queryParam.setTagTypeEnum(tagTypeEnum);
        ListResult<TagDTO> tagResult = tagDomainService.queryList(queryParam, tagSelector);
        // 判断是否都已经添加到数据库
        if (null == tagResult.getData() || tagValueList.size() != tagResult.getData().size()) {

            List<String> existsTagList = tagResult.getData().stream().map(TagDTO::getTagValue).collect(
                Collectors.toList());
            tagValueList.removeAll(existsTagList);

            log.error("出现了未知的标签值：{},标签类别:{}", tagValueList, tagTypeEnum.getCode());
            throw new SystemException(tagValueList + "," + tagTypeEnum.getDescription() + "标签不存在");
        }
        return tagResult.getData().stream().map(TagDTO::getId).collect(Collectors.toList());
    }

    /**
     * 生成项目的历史记录
     *
     * @param projectDO
     * @param context
     */
    private void createProjectHistory(ProjectDO projectDO, Context context) {
        ProjectHistoryDO historyDO = new ProjectHistoryDO();
        historyDO.setProjectId(projectDO.getId());
        historyDO.setForkCount(projectDO.getForkCount());
        historyDO.setStarCount(projectDO.getStarCount());
        historyDO.setCreator(context.getUserId());
        historyDO.setTenantId(context.getTenantId());
        historyDO.setModifier(context.getUserId());
        historyDO.setGmtRefresh(new Date());
        projectHistoryMapper.insertSelective(historyDO);
    }

    @Override
    public PageResult<ProjectDTO> queryPage(ProjectPageQueryParam queryParam, ProjectSelector selector) {

        List<ProjectDTO> results = null;
        // join查询
        CustomProjectSearchParam customParam = projectCoreConverter.queryParamToSearchParam(queryParam);
        customParam.setPagination(queryParam.getPageNo(), queryParam.getPageSize());
        customParam.setGtRefreshTime(queryParam.getGtRefreshTime());
        customParam.setLtRefreshTime(queryParam.getLtRefreshTime());
        // 排序
        customParam.appendOrderByClause(OrderBy.desc("p.star_count"));
        List<Long> ids = customProjectMapper.queryPageFromSearch(customParam);
        if (CollectionUtil.isNotEmpty(ids)) {
            ProjectParam param = new ProjectParam();
            param.createCriteria().andIdIn(ids);
            param.appendOrderByClause(OrderCondition.STARCOUNT, SortType.DESC);
            List<ProjectDO> dos = projectMapper.selectByParamWithBLOBs(param);
            results = projectCoreConverter.doToDto(dos);

        }
        // 查询总数
        long total = 0L;
        if (CollectionUtil.isNotEmpty(ids) && queryParam.getEnableReturnCount()) {
            total = customProjectMapper.countQueryPageFromSearch(customParam);
        }
        // 组装tag数据
        fillProjectTag(results, selector);

        return PageResult.of(results, total, queryParam.getPageNo(), queryParam.getPageSize());
    }

    /**
     * 填充tag数据
     */
    private void fillProjectTag(List<ProjectDTO> results, ProjectSelector selector) {
        if (selector == null || selector.getTagList() == null || !selector.getTagList() || CollectionUtil.isEmpty(
            results)) {
            return;
        }
        // 查询项目所有关联的标签ID
        List<Long> projectIds = results.stream().map(ProjectDTO::getId).collect(Collectors.toList());
        ProjectTagParam param = new ProjectTagParam();
        param.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode()).andProjectIdIn(projectIds);
        List<ProjectTagDO> projectTagDOS = projectTagMapper.selectByParam(param);
        if (CollectionUtil.isEmpty(projectTagDOS)) {
            return;
        }
        // 遍历组合project和tag的关系
        HashMap<Long, List<Long>> projectTagMap = new HashMap<>(results.size());
        Set<Long> tagIdSet = new HashSet<>();
        for (ProjectTagDO projectTagDO : projectTagDOS) {
            if (!projectTagMap.containsKey(projectTagDO.getProjectId())) {
                List<Long> tagIds = new ArrayList<>();
                tagIds.add(projectTagDO.getTagId());
                projectTagMap.put(projectTagDO.getProjectId(), tagIds);
            } else {
                projectTagMap.get(projectTagDO.getProjectId()).add(projectTagDO.getTagId());
            }
            tagIdSet.add(projectTagDO.getTagId());
        }
        // 查询tag数据
        ListResult<TagDTO> tagDTOs = tagDomainService.getList(new ArrayList<>(tagIdSet), new TagSelector());
        Map<Long, TagDTO> tagMap = tagDTOs.getData().stream().collect(Collectors.toMap(TagDTO::getId, e -> e));

        // 组装数据到project
        Map<Long, ProjectDTO> projectMap = results.stream().collect(Collectors.toMap(ProjectDTO::getId, e -> e));
        for (Entry<Long, List<Long>> entry : projectTagMap.entrySet()) {
            ProjectDTO projectDTO = projectMap.get(entry.getKey());
            for (Long tagId : entry.getValue()) {
                TagDTO tagDTO = tagMap.get(tagId);
                ProjectTagTypeEnum tagTypeEnum = EnumUtils.getEnum(ProjectTagTypeEnum.class, tagDTO.getTagType());
                if (tagTypeEnum.equals(ProjectTagTypeEnum.PROJECT_LANGUAGE)) {
                    projectDTO.setLanguageTag(tagDTO);
                } else if (tagTypeEnum.equals(ProjectTagTypeEnum.PROJECT_DOMAIN)) {
                    List<TagDTO> domainTagList = projectDTO.getDomainTagList() == null ? new ArrayList<>()
                        : projectDTO.getDomainTagList();
                    domainTagList.add(tagDTO);
                    projectDTO.setDomainTagList(domainTagList);
                } else if (tagTypeEnum.equals(ProjectTagTypeEnum.PROJECT_BASIS)) {
                    List<TagDTO> basisTagList = projectDTO.getBasisTagList() == null ? new ArrayList<>()
                        : projectDTO.getBasisTagList();
                    basisTagList.add(tagDTO);
                    projectDTO.setBasisTagList(basisTagList);
                }
            }
        }

    }

    @Override
    public ListResult<ProjectDTO> queryList(ProjectQueryParam queryParam, ProjectSelector selector) {
        ProjectParam projectParam = new ProjectParam();
        Criteria criteria = projectParam.createCriteria();
        criteria.andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        if (StringUtils.isNotEmpty(queryParam.getInShow())) {
            criteria.andInShowEqualTo(queryParam.getInShow());
        }
        if (CollectionUtil.isNotEmpty(queryParam.getIds())) {
            criteria.andIdIn(queryParam.getIds());
        }
        List<ProjectDO> dos = projectMapper.selectByParamWithBLOBs(projectParam);
        List<ProjectDTO> dtos = projectCoreConverter.doToDto(dos);

        fillProjectTag(dtos, selector);

        return ListResult.of(dtos);
    }
}
