package com.alibaba.ctoo.opensource.domain.api.param.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章选择器
 *
 * @author zyb
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSelector {

    /**
     * 激活的文章版本
     */
    private Boolean activatedArticleVersion;

    /**
     * 激活版本文章选择器
     */
    private ArticleVersionSelector articleVersionSelector;
}
