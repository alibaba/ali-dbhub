package com.alibaba.ctoo.opensource.domain.api.service.tag;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.TagDTO;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagBatchCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.tag.TagSelector;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * @author 知闰
 * @date 2022/03/23
 */
public interface TagDomainService {

    /**
     * 根据ID查询tag
     *
     * @param id
     * @param selector
     * @return
     */
    DataResult<TagDTO> get(Long id, TagSelector selector);

    /**
     * 根据ID批量查询
     *
     * @param ids
     * @param selector
     * @return
     */
    ListResult<TagDTO> getList(List<Long> ids, TagSelector selector);

    /**
     * 根据条件查询单个tag
     *
     * @param queryParam
     * @param selector
     * @return
     */
    DataResult<TagDTO> query(TagQueryParam queryParam, TagSelector selector);

    /**
     * 创建tag值
     *
     * @param param
     * @return
     */
    DataResult<Long> create(TagCreateParam param);

    /**
     * 批量创建
     *
     * @param param
     * @return
     */
    ListResult<Long> createBatch(TagBatchCreateParam param);

    /**
     * 根据条件查询list
     *
     * @param queryParam
     * @param selector
     * @return
     */
    ListResult<TagDTO> queryList(TagQueryParam queryParam, TagSelector selector);

}
