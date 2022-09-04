package com.alibaba.ctoo.opensource.domain.core.service.impl.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.enums.ShowEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.TagShowEnum;
import com.alibaba.ctoo.opensource.domain.api.model.TagDTO;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagBatchCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagSelector;
import com.alibaba.ctoo.opensource.domain.api.service.tag.TagDomainService;
import com.alibaba.ctoo.opensource.domain.api.service.tag.TagManageService;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectTagDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectTagParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.CustomTagMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectMapper;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectTagMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Service
public class TagManageServiceImpl implements TagManageService {

    @Autowired
    private TagDomainService tagDomainService;

    @Resource
    private ProjectTagMapper projectTagMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private CustomTagMapper customTagMapper;

    @Override
    public ListResult<Long> judgeTagIsExistsAndGetTagId(TagBatchCreateParam param) {
        List<Long> ids = new ArrayList<>();
        List<String> insertTagValues = new ArrayList<>();
        TagSelector selector = new TagSelector();
        for (String tagValue : param.getTagValues()) {
            TagQueryParam queryParam = new TagQueryParam();
            queryParam.setTagValue(tagValue);
            queryParam.setTagTypeEnum(param.getTagTypeEnum());
            DataResult<TagDTO> dataResult = tagDomainService.query(queryParam, selector);
            if (null != dataResult.getData()){
                ids.add(dataResult.getData().getId());
            }else {
                // 添加要创建的tag
                insertTagValues.add(tagValue);
            }
        }
        if (CollectionUtil.isNotEmpty(insertTagValues)){
            TagBatchCreateParam createParam = new TagBatchCreateParam();
            createParam.setTagTypeEnum(param.getTagTypeEnum());
            createParam.setTagValues(insertTagValues);
            ListResult<Long> batch = tagDomainService.createBatch(createParam);
            ids.addAll(batch.getData());
        }

        return ListResult.of(ids);
    }

    @Override
    public ActionResult refreshShowTag() {
        //查询展示的项目列表
        ProjectParam projectParam = new ProjectParam();
        projectParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode()).andInShowEqualTo(
            ShowEnum.YES.getCode());
        List<ProjectDO> showProjectList = projectMapper.selectByParam(projectParam);
        List<Long> projectIds = showProjectList.stream().map(ProjectDO::getId).collect(Collectors.toList());
        // 获取tag列表
        ProjectTagParam projectTagParam = new ProjectTagParam();
        projectTagParam.createCriteria().andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode()).andProjectIdIn(projectIds);
        List<ProjectTagDO> projectTagDOS = projectTagMapper.selectByParam(projectTagParam);
        List<Long> showTagIds = projectTagDOS.stream().map(ProjectTagDO::getTagId).distinct().collect(Collectors.toList());

        customTagMapper.updateTagShowStatus(TagShowEnum.YES.getCode(),showTagIds);
        return ActionResult.isSuccess();
    }
}
