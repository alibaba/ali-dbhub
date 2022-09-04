package com.alibaba.ctoo.opensource.domain.api.param.project.document;

import com.alibaba.ctoo.opensource.domain.api.enums.DocumentSourceTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 创建文档
 *
 * @author 是仪
 */
@Data
public class ProjectDocumentCreateParam {

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 文档源类型
     *
     * @see DocumentSourceTypeEnum
     */
    private String sourceType;

    /**
     * 文档源地址
     */
    private String sourceUrl;

    /**
     * 文档源分支
     */
    private String sourceBranch;

    /**
     * 相对文档地址
     */
    private String sourcePath;

    /**
     * git的类型
     *
     * @see GitTypeEnum
     */
    private String sourceGitType;

}
