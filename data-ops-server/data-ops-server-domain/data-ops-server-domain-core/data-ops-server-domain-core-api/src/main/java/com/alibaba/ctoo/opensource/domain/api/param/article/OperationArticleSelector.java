package com.alibaba.ctoo.opensource.domain.api.param.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客 活动 新闻选择器
 *
 * @author hyh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationArticleSelector {

    /**
     * 文章id
     */
    private Boolean article;

    /**
     * 文章选择器
     */
    private ArticleSelector articleSelector;

    /**
     * 创建人
     */
    private Boolean creatorDetail;

    /**
     * 修改人
     */
    private Boolean modifierDetail;

}
