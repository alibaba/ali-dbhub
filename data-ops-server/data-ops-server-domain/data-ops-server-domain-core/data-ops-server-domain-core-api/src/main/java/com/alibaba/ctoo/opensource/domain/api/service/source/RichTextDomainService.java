package com.alibaba.ctoo.opensource.domain.api.service.source;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.RichTextDTO;
import com.alibaba.ctoo.opensource.domain.api.param.resource.RichTextCreateOrUpdateParam;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;


/**
 * 富文本接口
 *
 * @author zyb
 */
public interface RichTextDomainService {

    /**
     * 创建或者修改富文本
     * param 为空 则返回 空的id
     *
     * @param param
     * @return
     */
    DataResult<Long> createOrUpdate(RichTextCreateOrUpdateParam param);

    /**
     * 查询富文本
     *
     * @param idList
     * @param selector
     * @return
     */
    ListResult<RichTextDTO> queryList(List<Long> idList, RichTextSelector selector);

}
