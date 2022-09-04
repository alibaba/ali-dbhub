package com.alibaba.ctoo.opensource.domain.api.param.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章版本选择器
 *
 * @author sunyongqiang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVersionSelector {

    /**
     * 富文本
     */
    private Boolean richTextDetail;
}
