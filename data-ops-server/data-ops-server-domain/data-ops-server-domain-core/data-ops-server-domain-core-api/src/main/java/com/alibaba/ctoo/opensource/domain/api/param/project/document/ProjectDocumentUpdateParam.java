package com.alibaba.ctoo.opensource.domain.api.param.project.document;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.enums.DocumentSourceTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum;

import lombok.Data;

/**
 * 修改文档
 *
 * @author 是仪
 */
@Data
public class ProjectDocumentUpdateParam {

    /**
     * id
     */
    private Long id;

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

    /**
     * 项目文档域名
     */
    private List<String> projectDocumentDomainDomainList;

}
