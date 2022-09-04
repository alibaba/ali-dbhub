package com.alibaba.ctoo.opensource.domain.api.service.project;

import com.alibaba.ctoo.opensource.domain.api.param.project.document.RefreshDocumentParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * 项目服务
 *
 * @author 是仪
 */
public interface ProjectDomainManageService {

    /**
     * 刷新文档信息
     * 仅仅发送个mq信息
     *
     * @param param
     * @return
     */
    ActionResult refreshDocument(RefreshDocumentParam param);

    /**
     * 刷新文档信息回调
     * 真正的去做刷新文档
     *
     * @param param
     * @return
     */
    ActionResult refreshDocumentCallback(RefreshDocumentParam param);

    /**
     * 获取捐献项目
     *
     * @return
     */
    ListResult<Long> getDonateProjectIdList();


    /**
     * 刷新项目数据
     *
     * @return
     */
    ActionResult refreshProject();


}
