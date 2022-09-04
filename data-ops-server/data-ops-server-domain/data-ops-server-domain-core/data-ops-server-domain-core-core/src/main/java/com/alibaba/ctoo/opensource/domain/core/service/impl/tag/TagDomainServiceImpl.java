package com.alibaba.ctoo.opensource.domain.core.service.impl.tag;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.model.TagDTO;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagBatchCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagSelector;
import com.alibaba.ctoo.opensource.domain.api.service.tag.TagDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.TagCoreConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagParam.Criteria;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagParam.OrderCondition;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagParam.SortType;
import com.alibaba.ctoo.opensource.domain.repository.mapper.TagMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

import cn.hutool.core.collection.CollectionUtil;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * tag 服务
 *
 * @author 知闰
 * @date 2022/03/23
 */
@Service
public class TagDomainServiceImpl implements TagDomainService {

    @Resource
    private TagMapper tagMapper;

    @Autowired
    private TagCoreConverter tagCoreConverter;

    @Override
    public DataResult<TagDTO> get(Long id, TagSelector selector) {
        TagDO tagDO = tagMapper.selectByPrimaryKey(id);
        return DataResult.of(tagCoreConverter.do2dto(tagDO));
    }

    @Override
    public ListResult<TagDTO> getList(List<Long> ids, TagSelector selector) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR);
        }
        if (ids.size() > 1000) {
            throw new BusinessException("参数过多");
        }
        TagParam param = new TagParam();
        param.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andIdIn(ids);
        List<TagDO> list = tagMapper.selectByParam(param);
        return ListResult.of(tagCoreConverter.do2dto(list));
    }

    @Override
    public DataResult<TagDTO> query(TagQueryParam queryParam, TagSelector selector) {
        TagParam param = new TagParam();
        param.setPageSize(1);
        param.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andTagValueEqualTo(queryParam.getTagValue())
            .andTagTypeEqualTo(queryParam.getTagTypeEnum().getCode());
        List<TagDO> list = tagMapper.selectByParam(param);
        return CollectionUtil.isEmpty(list) ? DataResult.empty() : DataResult.of(tagCoreConverter.do2dto(list.get(0)));
    }

    @Override
    public DataResult<Long> create(TagCreateParam param) {
        Context context = ContextUtils.getContext();
        TagDO tagDO = new TagDO();
        tagDO.setTagType(param.getTagTypeEnum().getCode());
        tagDO.setTagValue(param.getTagValue());
        tagDO.setTenantId(context.getTenantId());
        tagDO.setCreator(context.getUserId());
        tagDO.setModifier(context.getUserId());
        tagDO.setTagCode(StringUtil.toUpperCase(param.getTagValue()));
        tagMapper.insertSelective(tagDO);

        tagMapper.updateByPrimaryKeySelective(tagDO);
        return DataResult.of(tagDO.getId());
    }

    @Override
    public ListResult<Long> createBatch(TagBatchCreateParam param) {

        Context context = ContextUtils.getContext();

        List<Long> ids = new ArrayList<>();
        List<TagDO> insertList = new ArrayList<>();

        for (String tagValue : param.getTagValues()) {
            TagDO tagDO = new TagDO();
            tagDO.setTagType(param.getTagTypeEnum().getCode());
            tagDO.setTagValue(tagValue);
            tagDO.setTenantId(context.getTenantId());
            tagDO.setCreator(context.getUserId());
            insertList.add(tagDO);
        }
        tagMapper.batchInsert(insertList);
        // 更新tagCode
        for (TagDO tagDO : insertList) {
            tagDO.setTagCode(param.getTagTypeEnum().getCode() + "_" + tagDO.getId());
            tagMapper.updateByPrimaryKeySelective(tagDO);
            ids.add(tagDO.getId());
        }
        return ListResult.of(ids);
    }

    @Override
    public ListResult<TagDTO> queryList(TagQueryParam queryParam, TagSelector selector) {
        TagParam param = new TagParam();
        Criteria criteria = param.createCriteria();
        criteria.andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
        if (StringUtil.isNotEmpty(queryParam.getTagValue())){
            criteria.andTagValueEqualTo(queryParam.getTagValue());
        }
        if (CollectionUtil.isNotEmpty(queryParam.getTagValueList())){
            criteria.andTagValueIn(queryParam.getTagValueList());
        }
        if ( null != queryParam.getTagTypeEnum())  {
            criteria.andTagTypeEqualTo(queryParam.getTagTypeEnum().getCode());
        }
        if (StringUtil.isNotEmpty(queryParam.getTagShow())){
            criteria.andTagShowEqualTo(queryParam.getTagShow());
        }
        // 排序
        if (CollectionUtils.isNotEmpty(queryParam.getOrderByList())) {
            queryParam.getOrderByList().forEach(orderBy -> param.appendOrderByClause(
                OrderCondition.getEnumByName(orderBy.getOrderConditionName()),
                SortType.getEnumByName(orderBy.getDirection().name())));
        }
        List<TagDO> list = tagMapper.selectByParam(param);
        return CollectionUtil.isEmpty(list) ? ListResult.empty() : ListResult.of(tagCoreConverter.do2dto(list));
    }
}
