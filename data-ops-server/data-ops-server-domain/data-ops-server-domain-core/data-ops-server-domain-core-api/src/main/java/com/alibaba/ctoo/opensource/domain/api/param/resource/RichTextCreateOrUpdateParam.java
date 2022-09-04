package com.alibaba.ctoo.opensource.domain.api.param.resource;

import lombok.Data;

/**
 * 富文本资源
 * grow平台的
 *
 * @author 是仪
 */
@Data
public class RichTextCreateOrUpdateParam {

    /**
     * id
     */
    private Long id;

    /**
     * 自定义内容样式，可以是私有格式
     */
    private String customText;

    /**
     * 纯文本用于检索
     */
    private String plainText;

    /**
     * 富文本
     */
    private String richText;

}
