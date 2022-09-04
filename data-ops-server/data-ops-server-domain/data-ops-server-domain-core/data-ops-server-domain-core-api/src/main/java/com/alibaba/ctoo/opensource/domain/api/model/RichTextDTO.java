package com.alibaba.ctoo.opensource.domain.api.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 富文本资源
 *
 * @author zyb
 */
@Data
public class RichTextDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
