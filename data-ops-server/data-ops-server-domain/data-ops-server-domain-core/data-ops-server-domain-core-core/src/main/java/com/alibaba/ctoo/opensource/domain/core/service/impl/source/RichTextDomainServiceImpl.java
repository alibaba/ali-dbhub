package com.alibaba.ctoo.opensource.domain.core.service.impl.source;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.RichTextDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.RichTextParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.RichTextMapper;
import com.alibaba.ctoo.opensource.common.util.ContextUtils;
import com.alibaba.ctoo.opensource.domain.api.model.RichTextDTO;
import com.alibaba.ctoo.opensource.domain.api.service.source.RichTextSelector;
import com.alibaba.ctoo.opensource.domain.core.converter.RichTextCoreConverter;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.excption.SystemException;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.ctoo.opensource.domain.api.param.resource.RichTextCreateOrUpdateParam;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.ctoo.opensource.domain.api.service.source.RichTextDomainService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;


/**
 * 富文本实现
 *
 * @author zyb
 */
@Service
public class RichTextDomainServiceImpl implements RichTextDomainService {

    @Resource
    private RichTextMapper richTextMapper;
    @Resource
    private RichTextCoreConverter richTextCoreConverter;


    @Override
    public DataResult<Long> createOrUpdate(RichTextCreateOrUpdateParam param) {
        if (param == null) {
            return DataResult.empty();
        }
        // 创建
        if (param.getId() == null) {
            RichTextDO richText = richTextCoreConverter.create2descriptionRich(param, ContextUtils.getContext());
            richTextMapper.insertSelective(richText);
            return DataResult.of(richText.getId());
        }
        // 修改
        RichTextDO richText = richTextMapper.selectByPrimaryKey(param.getId());
        if (richText == null) {
            throw new SystemException(CommonErrorEnum.DATA_NOT_FOUND);
        }
        richText = richTextCoreConverter.description2update(param, ContextUtils.getContext());
        richTextMapper.updateByPrimaryKeySelective(richText);
        return DataResult.of(richText.getId());
    }

    @Override
    public ListResult<RichTextDTO> queryList(List<Long> idList, RichTextSelector selector) {
        if (CollectionUtils.isEmpty(idList)) {
            return ListResult.empty();

        }
        RichTextParam richTextParam = new RichTextParam();
        richTextParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andIdIn(idList);
        List<RichTextDO> richTextList = richTextMapper.selectByParamWithBLOBs(richTextParam);
        List<RichTextDTO> list = richTextCoreConverter.do2dto(richTextList);

        return ListResult.of(list);
    }
}
