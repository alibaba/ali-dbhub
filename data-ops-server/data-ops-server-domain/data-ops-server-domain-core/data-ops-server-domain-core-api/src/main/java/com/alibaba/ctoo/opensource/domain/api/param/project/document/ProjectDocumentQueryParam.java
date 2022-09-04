package com.alibaba.ctoo.opensource.domain.api.param.project.document;

import com.alibaba.ctoo.opensource.domain.api.enums.DocumentSourceTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum;
import com.alibaba.easytools.base.wrapper.param.QueryParam;

import lombok.Data;

/**
 * 项目文档查询参数
 * @author 是仪
 */
@Data
public class ProjectDocumentQueryParam extends QueryParam {
    /**
     * 文档源类型
     *
     * @see DocumentSourceTypeEnum
     */
    private String sourceType;

    /**
     * 文档源分支
     */
    private String sourceBranch;

    /**
     * git的类型
     *
     * @see GitTypeEnum
     */
    private String sourceGitType;

    /**
     * 文档源全称
     */
    private String sourceFullName;
}
