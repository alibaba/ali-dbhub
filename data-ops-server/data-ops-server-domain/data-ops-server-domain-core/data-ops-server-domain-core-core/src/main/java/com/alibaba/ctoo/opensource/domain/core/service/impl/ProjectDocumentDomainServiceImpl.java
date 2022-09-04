package com.alibaba.ctoo.opensource.domain.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.constants.RedisConstant.Web;
import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.model.ProjectDTO;
import com.alibaba.ctoo.opensource.domain.api.model.ProjectDocumentDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentSelector;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.ProjectDocumentUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.ProjectDocumentDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ProjectDocumentCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDomainDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDomainParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectDocumentDomainMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectDocumentMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.common.util.EasyCollectionUtils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目文档服务
 *
 * @author 是仪
 */
@Slf4j
@Service
public class ProjectDocumentDomainServiceImpl implements ProjectDocumentDomainService {
    @Resource
    private ProjectDocumentMapper projectDocumentMapper;
    @Resource
    private ProjectDomainService projectDomainService;
    @Resource
    private ProjectDocumentCoreConverter projectDocumentCoreConverter;
    @Resource
    private ProjectDocumentDomainMapper projectDocumentDomainMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public DataResult<ProjectDocumentDTO> get(Long id, ProjectDocumentSelector selector) {
        ProjectDocumentDO data = projectDocumentMapper.selectByPrimaryKey(id);
        if (data == null) {
            throw new BusinessException(CommonErrorEnum.DATA_NOT_FOUND);
        }
        List<ProjectDocumentDO> dataList = Lists.newArrayList(data);
        List<ProjectDocumentDTO> list = projectDocumentCoreConverter.do2dto(dataList);
        return DataResult.of(list.get(0));
    }

    @Override
    public DataResult<ProjectDocumentDTO> queryByProjectId(Long projectId, ProjectDocumentSelector selector) {
        ProjectDocumentParam param = new ProjectDocumentParam();
        param.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andProjectIdEqualTo(projectId);
        List<ProjectDocumentDO> dataList = projectDocumentMapper.selectByParam(param);
        List<ProjectDocumentDTO> list = projectDocumentCoreConverter.do2dto(dataList);
        if (CollectionUtils.isEmpty(list)) {
            return DataResult.empty();
        }
        return DataResult.of(list.get(0));
    }

    @Override
    public DataResult<ProjectDocumentDTO> query(ProjectDocumentQueryParam param, ProjectDocumentSelector selector) {
        ProjectDocumentParam queryParam = new ProjectDocumentParam();
        queryParam.createCriteria()
            .andSourceTypeEqualToWhenPresent(param.getSourceType())
            .andSourceBranchEqualToWhenPresent(param.getSourceBranch())
            .andSourceGitTypeEqualToWhenPresent(param.getSourceGitType())
            .andSourceFullNameEqualToWhenPresent(param.getSourceFullName())
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());

        List<ProjectDocumentDO> dataList = projectDocumentMapper.selectByParam(queryParam);

        List<ProjectDocumentDTO> list = projectDocumentCoreConverter.do2dto(dataList);
        if (CollectionUtils.isEmpty(list)) {
            return DataResult.empty();
        }
        return DataResult.of(list.get(0));
    }

    @Override
    public DataResult<Long> create(ProjectDocumentCreateParam param) {
        Context context = ContextUtils.getContext();
        ProjectDocumentDO projectDocument = projectDocumentCoreConverter.param2do(param, context.getTenantId(),
            context.getUserId(), context.getUserId());
        projectDocumentMapper.insertSelective(projectDocument);
        return DataResult.of(projectDocument.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResult update(ProjectDocumentUpdateParam param) {
        ProjectDocumentDTO projectDocument = get(param.getId(), null).getData();
        ProjectDTO project = projectDomainService.get(projectDocument.getProjectId(), null).getData();

        Context context = ContextUtils.getContext();
        ProjectDocumentDO projectDocumentInsert = projectDocumentCoreConverter.param2do(param, context.getTenantId(),
            context.getUserId());
        projectDocumentMapper.updateByPrimaryKeySelective(projectDocumentInsert);

        // 修改了文档域名
        if (param.getProjectDocumentDomainDomainList() != null) {
            // 校验重复
            ProjectDocumentDomainParam projectDocumentDomainParam = new ProjectDocumentDomainParam();
            projectDocumentDomainParam.createCriteria()
                .andDomainIn(param.getProjectDocumentDomainDomainList())
                .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            List<ProjectDocumentDomainDO> projectDocumentDomainList = projectDocumentDomainMapper.selectByParam(
                projectDocumentDomainParam);
            projectDocumentDomainList.stream()
                .filter(projectDocumentDomain -> !param.getId().equals(projectDocumentDomain.getProjectDocumentId()))
                .findAny()
                .ifPresent(projectDocumentDomain -> {
                    throw new BusinessException("已经文档" + projectDocumentDomain.getProjectDocumentId() + "占用了。");
                });

            // 查询全部当前的域名
            projectDocumentDomainParam = new ProjectDocumentDomainParam();
            projectDocumentDomainParam.createCriteria()
                .andProjectDocumentIdEqualTo(param.getId())
                .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            projectDocumentDomainList = projectDocumentDomainMapper.selectByParam(
                projectDocumentDomainParam);
            List<String> projectDocumentDomainDomainDataList = EasyCollectionUtils.toList(projectDocumentDomainList,
                ProjectDocumentDomainDO::getDomain);

            // 新增的里面有，旧的里面没有 代表新增
            param.getProjectDocumentDomainDomainList().stream()
                .filter(domain -> !projectDocumentDomainDomainDataList.contains(domain))
                .forEach(domain -> {
                    ProjectDocumentDomainDO projectDocumentDomain = projectDocumentCoreConverter.param2doDomain(
                        param.getId(), domain, context.getTenantId(), context.getUserId(), context.getUserId());
                    projectDocumentDomainMapper.insertSelective(projectDocumentDomain);

                    // 新增缓存
                    stringRedisTemplate.opsForValue().set(Web.WEB + domain, project.getName());
                });

            //  旧的里面有，新增的里面没有 代表删除
            for (ProjectDocumentDomainDO projectDocumentDomain : projectDocumentDomainList) {
                if (param.getProjectDocumentDomainDomainList().contains(projectDocumentDomain.getDomain())) {
                    // 相同的不处理
                    continue;
                }
                // 代表删除
                ProjectDocumentDomainDO projectDocumentDomainDelete = new ProjectDocumentDomainDO();
                projectDocumentDomainDelete.setId(projectDocumentDomain.getId());
                projectDocumentDomainDelete.setDeletedId(projectDocumentDomain.getDeletedId());
                projectDocumentDomainDelete.setModifier(context.getUserId());
                projectDocumentDomainMapper.updateByPrimaryKeySelective(projectDocumentDomainDelete);

                // 删除缓存
                stringRedisTemplate.delete(Web.WEB + projectDocumentDomain.getDomain());
            }
        }

        return ActionResult.isSuccess();
    }
}
