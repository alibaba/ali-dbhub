package com.alibaba.ctoo.opensource.domain.api.param.project.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目文档的选择器
 *
 * @author 是仪
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDocumentSelector {

    /**
     * 空
     */
    private Boolean empty;
}

