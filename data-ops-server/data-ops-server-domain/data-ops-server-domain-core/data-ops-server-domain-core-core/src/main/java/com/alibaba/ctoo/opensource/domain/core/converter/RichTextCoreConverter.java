package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.Map;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.service.source.RichTextDomainService;
import com.alibaba.easytools.common.util.EasyCollectionUtils;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.RichTextDO;
import com.alibaba.ctoo.opensource.common.model.Context;
import com.alibaba.ctoo.opensource.domain.api.model.RichTextDTO;
import com.alibaba.ctoo.opensource.domain.api.param.resource.RichTextCreateOrUpdateParam;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * 富文本转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring")
public abstract class RichTextCoreConverter {

    @Resource
    private RichTextDomainService richTextDomainService;

    /**
     * 富文本添加参数转换
     *
     * @param descriptionRich
     * @param context
     * @return
     */
    @Mappings({
        @Mapping(target = "creator", source = "context.userId"),
        @Mapping(target = "modifier", source = "context.userId")
    })
    public abstract RichTextDO create2descriptionRich(RichTextCreateOrUpdateParam descriptionRich, Context context);


    /**
     * 富文本修改参数转换
     *
     * @param descriptionRichText
     * @param context
     * @return
     */
    @Mappings({
        @Mapping(target = "modifier", source = "context.userId"),
    })
    public abstract RichTextDO description2update(RichTextCreateOrUpdateParam descriptionRichText, Context context);


    /**
     * do转换为dto
     *
     * @param richTextDO
     * @return
     */
    public abstract RichTextDTO do2resourceText(RichTextDO richTextDO);

    /**
     * List<RichTextDO>2List<RichTextDTO>
     *
     * @param list
     * @return
     */
    public abstract List<RichTextDTO> do2dto(List<RichTextDO> list);


    /**
     * 填充富文本信息
     *
     * @param list
     */
    public void fillRichTextDetail(List<RichTextDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<Long> idList = EasyCollectionUtils.toList(list, RichTextDTO::getId);

        List<RichTextDTO> richTextList = richTextDomainService.queryList(idList, null).getData();
        Map<Long, RichTextDTO> richTextMap = EasyCollectionUtils.toIdentityMap(richTextList, RichTextDTO::getId);
        for (RichTextDTO richText : list) {
            if (richText == null || richText.getId() == null) {
                continue;
            }
            RichTextDTO query = richTextMap.get(richText.getId());
            if (query == null) {
                continue;
            }
            addRichText(richText, query);
        }
    }

    /**
     * 转换
     *
     * @param target
     * @param source
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    protected abstract void addRichText(@MappingTarget RichTextDTO target, RichTextDTO source);

}
