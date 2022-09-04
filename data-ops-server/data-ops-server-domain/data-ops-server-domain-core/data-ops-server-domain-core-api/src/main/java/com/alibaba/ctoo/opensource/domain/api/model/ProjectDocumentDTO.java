package com.alibaba.ctoo.opensource.domain.api.model;

import java.util.Date;

import com.alibaba.ctoo.opensource.domain.api.enums.DocumentSourceTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.GitTypeEnum;

import lombok.Data;

/**
 * 文档
 *
 * @author 是仪
 */
@Data
public class ProjectDocumentDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文档源类型
     *
     * @see DocumentSourceTypeEnum
     */
    private String sourceType;

    /**
     * 项目id
     */
    private Long projectId;

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
     * 文档刷新时间
     */
    private Date gmtRefresh;

    /**
     * 刷新成功时间
     */
    private Date gmtRefreshSuccess;

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
