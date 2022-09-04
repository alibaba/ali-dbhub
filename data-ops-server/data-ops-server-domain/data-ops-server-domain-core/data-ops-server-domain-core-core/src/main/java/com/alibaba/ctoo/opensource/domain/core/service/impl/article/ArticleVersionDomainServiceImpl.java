package com.alibaba.ctoo.opensource.domain.core.service.impl.article;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ArticleVersionMapper;
import com.alibaba.ctoo.opensource.domain.api.model.ArticleVersionDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.ArticleVersionSelector;
import com.alibaba.ctoo.opensource.domain.api.service.article.ArticleVersionDomainService;
import com.alibaba.ctoo.opensource.domain.core.converter.ArticleVersionCoreConverter;
import com.alibaba.ctoo.opensource.domain.core.converter.RichTextCoreConverter;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.base.wrapper.result.ListResult;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/**
 * 文章版本实现
 *
 * @author sunyongqiang
 */
@Service
public class ArticleVersionDomainServiceImpl implements ArticleVersionDomainService {

    @Resource
    private ArticleVersionMapper articleVersionMapper;
    @Resource
    private ArticleVersionCoreConverter articleVersionCoreConverter;
    @Resource
    private RichTextCoreConverter richTextCoreConverter;
    @Override
    public ListResult<ArticleVersionDTO> queryList(List<Long> idList, ArticleVersionSelector selector) {
        if (CollectionUtils.isEmpty(idList)) {
            return ListResult.empty();
        }
        ArticleVersionParam examVersionParam = new ArticleVersionParam();
        examVersionParam.createCriteria()
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andIdIn(idList);
        List<ArticleVersionDO> dataList = articleVersionMapper.selectByParam(examVersionParam);
        List<ArticleVersionDTO> list = articleVersionCoreConverter.do2dto(dataList);
        // 填充数据
        fillData(list, selector);
        return ListResult.of(list);
    }

    private void fillData(List<ArticleVersionDTO> list, ArticleVersionSelector selector) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if (selector == null) {
            return;
        }
        // 填充富文本
        fillRichTextDetail(list, selector);
    }

    private void fillRichTextDetail(List<ArticleVersionDTO> list, ArticleVersionSelector selector) {
        if (BooleanUtils.isNotTrue(selector.getRichTextDetail())) {
            return;
        }
        richTextCoreConverter.fillRichTextDetail(list.stream().map(ArticleVersionDTO::getContentRichText).collect(
            Collectors.toList()));
    }
}
