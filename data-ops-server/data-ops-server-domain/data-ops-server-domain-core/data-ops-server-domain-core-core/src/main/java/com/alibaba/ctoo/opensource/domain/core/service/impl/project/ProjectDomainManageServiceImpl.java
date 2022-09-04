package com.alibaba.ctoo.opensource.domain.core.service.impl.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.config.dynamic.DynamicDiamondDataCallback;
import com.alibaba.ctoo.opensource.common.constants.MixConstants;
import com.alibaba.ctoo.opensource.common.enums.OpenSourceOssKindEnum;
import com.alibaba.ctoo.opensource.common.util.EnumUtils;
import com.alibaba.ctoo.opensource.domain.api.enums.DocumentSourceTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ProjectTagTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ShowEnum;
import com.alibaba.ctoo.opensource.domain.api.metaq.ProjectDocumentMetaqDTO;
import com.alibaba.ctoo.opensource.domain.api.model.ProjectDTO;
import com.alibaba.ctoo.opensource.domain.api.model.ProjectDocumentDTO;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectCreateOrUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectPageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectSelector;
import com.alibaba.ctoo.opensource.domain.api.param.project.ProjectUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.RefreshDocumentParam;
import com.alibaba.ctoo.opensource.domain.api.service.ProjectDocumentDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainManageService;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ProjectCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.metaq.producer.ProjectDocumentProducer;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectTagDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectTagParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomProjectMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectDocumentMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectTagMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.TagMapper;
import com.alibaba.ctoo.opensource.integration.dto.BranchIntegrationDTO;
import com.alibaba.ctoo.opensource.integration.dto.CodeCloneIntegrationDTO;
import com.alibaba.ctoo.opensource.integration.dto.ProjectIntegrationDTO;
import com.alibaba.ctoo.opensource.integration.param.BranchQueryIntegrationParam;
import com.alibaba.ctoo.opensource.integration.param.CodeCloneIntegrationParam;
import com.alibaba.ctoo.opensource.integration.param.ProjectQueryIntegrationParam;
import com.alibaba.ctoo.opensource.integration.param.ProjectQueryPageIntegrationParam;
import com.alibaba.ctoo.opensource.integration.service.BranchIntegrationService;
import com.alibaba.ctoo.opensource.integration.service.CodeIntegrationService;
import com.alibaba.ctoo.opensource.integration.service.ProjectIntegrationService;
import com.alibaba.easytools.base.constant.SymbolConstant;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;
import com.alibaba.easytools.common.util.EasyEnumUtils;
import com.alibaba.easytools.spring.oss.OssClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.shade.org.apache.commons.lang.time.DateUtils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 项目服务
 *
 * @author 是仪
 */
@Slf4j
@Service
public class ProjectDomainManageServiceImpl implements ProjectDomainManageService {

    @Resource
    private ProjectDomainService projectDomainService;
    @Resource
    private ProjectDocumentDomainService projectDocumentDomainService;
    @Resource
    private CodeIntegrationService codeIntegrationService;
    @Resource
    private BranchIntegrationService branchIntegrationService;
    @Resource
    private ProjectIntegrationService projectIntegrationService;

    @Resource
    private ProjectDocumentMapper projectDocumentMapper;

    @Resource
    private ProjectTagMapper projectTagMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private CustomProjectMapper customProjectMapper;
    @Resource
    private OssClient ossClient;

    @Resource
    private ProjectCoreConverter projectCoreConverter;

    @Resource
    private ProjectDocumentProducer projectDocumentProducer;

    @Value("${project.domain.suffix}")
    private String projectDomainSuffix;

    /**
     * 默认分支
     */
    private static final String DEFAULT_BRANCH = "master";

    /**
     * 默认文件
     */
    private static final String DEFAULT_INDEX = "index.html";

    @Override
    public ActionResult refreshDocument(RefreshDocumentParam param) {
        ProjectDocumentMetaqDTO projectDocument = new ProjectDocumentMetaqDTO();
        projectDocument.setProjectId(param.getProjectId());
        projectDocumentProducer.produce(projectDocument);
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult refreshDocumentCallback(RefreshDocumentParam param) {
        log.info("刷新文档：{}", JSON.toJSONString(param));
        ProjectDTO project = projectDomainService.query(param.getProjectId(), null).getData();
        if (project == null) {
            log.info("找不到项目信息:{}", param.getProjectId());
            return ActionResult.isSuccess();
        }
        ProjectDocumentDTO projectDocument = projectDocumentDomainService.queryByProjectId(param.getProjectId(), null)
            .getData();
        if (projectDocument == null) {
            log.info("当前项目没有文档配置信息:{}", param.getProjectId());
            return ActionResult.isSuccess();
        }

        // 修改刷新时间
        ProjectDocumentDO projectDocumentUpdate = new ProjectDocumentDO();
        projectDocumentUpdate.setId(projectDocument.getId());
        try {
            doRefreshDocument(project, projectDocument, projectDocumentUpdate);
        } finally {
            projectDocumentUpdate.setGmtRefresh(DateUtil.date());
            projectDocumentMapper.updateByPrimaryKeySelective(projectDocumentUpdate);
        }
        return ActionResult.isSuccess();
    }

    public void doRefreshDocument(ProjectDTO project, ProjectDocumentDTO projectDocument,
        ProjectDocumentDO projectDocumentUpdate) {
        DocumentSourceTypeEnum sourceType = EasyEnumUtils.getEnum(DocumentSourceTypeEnum.class,
            projectDocument.getSourceType());
        if (sourceType == null) {
            log.info("当前项目没有文档类型:{}", project.getId());
            return;
        }

        String documentUrl = documentUrl(sourceType, projectDocument, project);
        if (documentUrl != null) {
            // 刷新连接成功
            projectDocumentUpdate.setGmtRefreshSuccess(DateUtil.date());

            ProjectUpdateParam projectUpdateParam = new ProjectUpdateParam();
            projectUpdateParam.setId(project.getId());
            projectUpdateParam.setDocumentUrl(documentUrl);
            projectDomainService.update(projectUpdateParam);
        }
    }

    private String documentUrl(DocumentSourceTypeEnum sourceType, ProjectDocumentDTO projectDocument,
        ProjectDTO project) {
        // 网站直接更新
        if (sourceType == DocumentSourceTypeEnum.WEBSITE) {
            return projectDocument.getSourceUrl();
        }
        if (projectDocument.getSourceUrl() == null) {
            log.info("当前项目没有源地址:{}", project.getId());
            return null;
        }
        String branch = projectDocument.getSourceBranch();
        if (StringUtils.isBlank(branch)) {
            branch = DEFAULT_BRANCH;
        }
        String index = projectDocument.getSourcePath();
        if (StringUtils.isBlank(index)) {
            index = DEFAULT_INDEX;
        }
        GitTypeEnum gitType = EasyEnumUtils.getEnum(GitTypeEnum.class, projectDocument.getSourceGitType());
        if (gitType == null) {
            gitType = GitTypeEnum.GITHUB;
        }

        // 校验是否需要刷新
        if (!checkRefresh(projectDocument, project, branch, gitType)) {
            log.info("项目不需要刷新:{}", project.getId());
            return null;
        }

        // 转成小写
        // 默认域名输入到浏览器会变成小写 但是放到oss的时候 放大写就不行 所以统一小写
        // 后续规则可以考虑重新确认二级域名规则
        String projectName = project.getName().toLowerCase();

        String path = null;
        try {
            // 去克隆 然后下载
            CodeCloneIntegrationParam codeCloneIntegrationParam = new CodeCloneIntegrationParam();
            codeCloneIntegrationParam.setFullName(projectDocument.getSourceFullName());
            codeCloneIntegrationParam.setBranch(branch);
            codeCloneIntegrationParam.setGitType(projectDocument.getSourceGitType());
            CodeCloneIntegrationDTO codeCloneIntegration = codeIntegrationService.clone(codeCloneIntegrationParam)
                .getData();
            path = codeCloneIntegration.getPath();
            File parentPath = new File(path);

            // 检查是否存在index页面
            File indexFile = new File(parentPath, index);
            if (!indexFile.exists()) {
                log.error("项目{}不存在index文件：{}", project.getId(), index);
                return null;
            }
            String parentAbsoluteParentPath = parentPath.getAbsolutePath();
            // 忽略 git开头的目录 不需要上传
            String gitPath = parentAbsoluteParentPath + "/.git/";

            // 上传到oss 采用覆盖的方式 多几个文件未删除应该问题不大
            // 后续如果 必须删除文件，版本比较然后删除
            List<File> fileList = FileUtil.loopFiles(path, file -> !file.getAbsolutePath().startsWith(gitPath));

            log.info("一共需要上传{}个文件", fileList.size());
            for (File file : fileList) {
                String key = OpenSourceOssKindEnum.WEB.getCode() + SymbolConstant.SLASH + projectName
                    + StringUtils.substringAfter(file.getAbsolutePath(), parentAbsoluteParentPath);
                ossClient.put(file, null, file.getName(), null, key, OpenSourceOssKindEnum.WEB.getObjectAcl());
            }
        } finally {
            FileUtil.del(path);
        }
        // 返回文档的地址 这里生成二级域名 会走nginx代理到oss
        // 因为爬过来的数据很多 用的都是相对域名的 路径 ，所以只能反相代理才能解决
        return "https://" + projectName + projectDomainSuffix + SymbolConstant.SLASH + index;
    }

    private boolean checkRefresh(ProjectDocumentDTO projectDocument, ProjectDTO project, String branch,
        GitTypeEnum gitType) {
        // 没有刷新过 一定要去刷新
        if (projectDocument.getGmtRefresh() == null || projectDocument.getGmtRefreshSuccess() == null) {
            return true;
        }

        // 去校验最近是否有人提交过
        BranchQueryIntegrationParam branchQueryIntegrationParam = new BranchQueryIntegrationParam();
        branchQueryIntegrationParam.setProjectFullName(projectDocument.getSourceFullName());
        branchQueryIntegrationParam.setBranch(branch);
        branchQueryIntegrationParam.setGitType(gitType.getCode());
        BranchIntegrationDTO branchIntegration = branchIntegrationService.query(branchQueryIntegrationParam).getData();
        if (branchIntegration == null || branchIntegration.getLastCommit() == null
            || branchIntegration.getLastCommit().getGmtCommit() == null) {
            log.error("无法查询到项目信息:{}", project.getId());
            return false;
        }
        // 在刷新成功之后提交过
        return branchIntegration.getLastCommit().getGmtCommit().after(projectDocument.getGmtRefreshSuccess());
    }

    @Override
    public ListResult<Long> getDonateProjectIdList() {
        TagParam tagParam = new TagParam();
        tagParam.createCriteria()
            .andTagTypeEqualTo(ProjectTagTypeEnum.PROJECT_BASIS.getCode())
            .andTagCodeEqualTo("DONATE")
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        TagDO donateTag = tagMapper.selectByParam(tagParam).get(0);
        ProjectTagParam projectTagParam = new ProjectTagParam();
        projectTagParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode()).andTagIdEqualTo(
            donateTag.getId());
        List<ProjectTagDO> projectTagList = projectTagMapper.selectByParam(projectTagParam);
        if (CollectionUtil.isNotEmpty(projectTagList)) {
            List<Long> projectIdList = projectTagList.stream().map(ProjectTagDO::getProjectId).collect(
                Collectors.toList());
            return ListResult.of(projectIdList);
        }

        return ListResult.empty();
    }

    @Override
    public ActionResult refreshProject() {
        // 拉取数据
        // 更新项目数据
        // 更新commit数据
        // 更新contributor数据
        // 存储已刷新项目，防止重复刷新
        HashMap<String, Integer> hadRefreshProjectMap = new HashMap<>();
        refreshOrg(hadRefreshProjectMap);
        refreshRepos(hadRefreshProjectMap);
        refreshNoRefreshProject();
        // 刷新时间超出1个月还未刷新过的，修改为删除
        updateInvalidProject();
        return ActionResult.isSuccess();
    }

    /**
     * 刷新配置的组织列表
     *
     * @param hadRefreshProjectMap
     */
    private void refreshOrg(HashMap<String, Integer> hadRefreshProjectMap) {
        // 组织列表
        List<String> orgList = DynamicDiamondDataCallback.dynamicConfig.getOrg();
        for (String org : orgList) {
            List<ProjectIntegrationDTO> dtoList = getProjectIntegrationDTO(org, null, RequestTypeEnum.BATCH,
                GitTypeEnum.GITHUB);

            for (ProjectIntegrationDTO dto : dtoList) {
                hadRefreshProjectMap.put(dto.getFullName(), 0);
                // 进行入库等操作
                ProjectDTO projectDTO = projectDomainService.queryByFullName(dto.getFullName(), null)
                    .getData();
                createOrUpdateProject(dto, projectDTO, GitTypeEnum.GITHUB);
            }
        }
    }

    /**
     * 刷新配置的仓库列表
     */
    private void refreshRepos(HashMap<String, Integer> hadRefreshProjectMap) {
        List<String> reposList = DynamicDiamondDataCallback.dynamicConfig.getRepos();
        for (String fullName : reposList) {
            // 已经刷新过的不再刷新
            if (hadRefreshProjectMap.containsKey(fullName)) {
                continue;
            }

            String[] split = fullName.split("/");
            String org = split[0];
            String repos = split[1];
            List<ProjectIntegrationDTO> dtoList = getProjectIntegrationDTO(org, repos, RequestTypeEnum.SINGLE,
                GitTypeEnum.GITHUB);
            if (CollectionUtil.isEmpty(dtoList)) {
                continue;
            }
            // 进行入库等操作
            ProjectDTO projectDTO = projectDomainService.queryByFullName(fullName, null)
                .getData();
            createOrUpdateProject(dtoList.get(0), projectDTO, GitTypeEnum.GITHUB);
        }
    }

    /**
     * 刷新数据库已经配置的项目数据，这一步的刷新是为了防止手动导入的项目不在配置的组织和仓库里，没刷新到
     */
    private void refreshNoRefreshProject() {
        ProjectSelector selector = new ProjectSelector();
        // 构建请求参数 只查询今日未刷新，且标记为展示的项目数据
        ProjectPageQueryParam queryParam = new ProjectPageQueryParam();
        queryParam.setEnableReturnCount(Boolean.FALSE);
        queryParam.setLtRefreshTime(DateUtil.beginOfDay(DateUtil.date()));
        queryParam.setInShow(ShowEnum.YES.getCode());
        int page = 1;
        boolean hasNextPage;
        do {
            queryParam.setPageNo(page);
            PageResult<ProjectDTO> pageResult = projectDomainService.queryPage(queryParam, selector);
            List<ProjectDTO> list = pageResult.getData();
            if (CollectionUtil.isEmpty(list)) {
                break;
            }
            // 处理数据
            for (ProjectDTO projectDTO : list) {

                GitTypeEnum gitTypeEnum = EnumUtils.getEnum(GitTypeEnum.class, projectDTO.getGitType());

                List<ProjectIntegrationDTO> dtoList = getProjectIntegrationDTO(projectDTO.getOrganization(),
                    projectDTO.getName(), RequestTypeEnum.SINGLE, gitTypeEnum);
                for (ProjectIntegrationDTO dto : dtoList) {
                    // 进行入库等操作
                    createOrUpdateProject(dto, projectDTO, gitTypeEnum);
                }
            }

            hasNextPage = list.size() >= queryParam.getPageSize();

        } while (hasNextPage && page++ < MixConstants.MAXIMUM_ITERATIONS);
    }

    /**
     * 从第三方获取项目数据
     *
     * @param org   组织名
     * @param repos 仓库名
     * @param type  请求类型 1-请求组织下的所有项目 2-请求单个项目
     * @return
     */
    private List<ProjectIntegrationDTO> getProjectIntegrationDTO(String org, String repos, RequestTypeEnum type,
        GitTypeEnum gitTypeEnum
    ) {
        List<ProjectIntegrationDTO> results = new ArrayList<>();
        try {
            switch (type) {
                case BATCH:
                    // 构建查询参数
                    ProjectQueryPageIntegrationParam param = new ProjectQueryPageIntegrationParam();
                    param.setGitTypeEnum(gitTypeEnum);
                    param.setOrg(org);
                    int page = 1;
                    boolean hasNextPage;
                    do {
                        param.setPageNo(page);
                        ListResult<ProjectIntegrationDTO> list = projectIntegrationService.queryPageReposOfOrg(param);
                        results.addAll(list.getData());
                        hasNextPage = list.getData().size() >= param.getPageSize();
                    } while (hasNextPage && page++ < MixConstants.MAXIMUM_ITERATIONS);
                    break;
                case SINGLE:
                    ProjectQueryIntegrationParam singleQueryParam = new ProjectQueryIntegrationParam();
                    singleQueryParam.setOrg(org);
                    singleQueryParam.setRepos(repos);
                    singleQueryParam.setGitEnum(gitTypeEnum);
                    ProjectIntegrationDTO integrationDto = projectIntegrationService.queryProjectByOrgAndRepos(
                        singleQueryParam).getData();
                    results.add(integrationDto);
                    break;
                default:
                    break;
            }
        } catch (BusinessException e) {
            log.error("刷新项目数据失败，org:{},repos:{},type:{},gitTypeEnum:{}", org, repos, type, gitTypeEnum, e);
            if (e.getCode().equals(CommonErrorEnum.ACCESS_LIMIT_EXCEEDED.getCode())) {
                // 抛出该异常，上层不捕获，直接结束
                throw e;
            }
        } catch (Exception e) {
            log.error("刷新项目数据失败，org:{},repos:{},type:{},gitTypeEnum:{}", org, repos, type, gitTypeEnum, e);
        }

        return results;
    }

    private void createOrUpdateProject(ProjectIntegrationDTO projectIntegrationDTO, ProjectDTO projectDTO,
        GitTypeEnum gitTypeEnum) {

        // 判断是否允许刷新
        Boolean allowRefresh = judgeAllowRefresh(projectDTO);
        if (!allowRefresh) {
            return;
        }
        // 更新到库
        ProjectCreateOrUpdateParam createOrUpdateParam = projectCoreConverter.projectIntegrationToParam(
            projectIntegrationDTO);
        // 定时器不允许更改标签值
        createOrUpdateParam.setAllowUpdateTag(false);
        if (projectDTO != null) {
            createOrUpdateParam.setId(projectDTO.getId());
        } else {
            // 新增项目不展示
            createOrUpdateParam.setInShow(ShowEnum.NO.getCode());
            createOrUpdateParam.setGitType(gitTypeEnum.getCode());
            if (gitTypeEnum.equals(GitTypeEnum.GITHUB)) {
                createOrUpdateParam.setGithubUrl(projectIntegrationDTO.getHtmlUrl());
            } else {
                createOrUpdateParam.setGiteeUrl(projectIntegrationDTO.getHtmlUrl());
            }
        }
        projectDomainService.createOrUpdate(createOrUpdateParam);
    }

    /**
     * 判断是否允许刷新
     *
     * @param projectDTO
     * @return
     */
    private Boolean judgeAllowRefresh(ProjectDTO projectDTO) {
        Date time = new Date();
        if (projectDTO == null) {
            return Boolean.TRUE;
        } else if (projectDTO.getGmtRefresh() != null && DateUtils.truncatedCompareTo(projectDTO.getGmtRefresh(), time,
            Calendar.DAY_OF_MONTH) >= 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void updateInvalidProject() {
        // 刷新时间超出1个月还未刷新过的，修改为删除
        DateTime dateTime = DateUtil.offset(new Date(), DateField.MONTH, -1);
        customProjectMapper.updateInvalidProjectTag(dateTime);
        customProjectMapper.updateInvalidProject(dateTime);

    }
}
