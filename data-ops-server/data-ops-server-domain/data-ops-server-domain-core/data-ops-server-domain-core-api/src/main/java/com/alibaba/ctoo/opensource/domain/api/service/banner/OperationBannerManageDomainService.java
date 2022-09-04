package com.alibaba.ctoo.opensource.domain.api.service.banner;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.OperationBannerDTO;
import com.alibaba.ctoo.opensource.domain.api.param.article.OperationBannerPageQueryParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.BannerCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.BannerUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.OperationBannerSelector;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.DataResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;
import com.alibaba.easytools.base.wrapper.result.PageResult;

/**
 * 运营管理/首页banner
 *
 * @author hyh
 */
public interface OperationBannerManageDomainService {

    /**
     * 列表分页查询
     *
     * @param param    param
     * @param selector selector
     * @return
     */
    PageResult<OperationBannerDTO> queryPage(OperationBannerPageQueryParam param, OperationBannerSelector selector);

    /**
     * banner列表
     *
     * @param idList   idList
     * @param selector selector
     * @return
     */
    ListResult<OperationBannerDTO> queryList(List<Long> idList, OperationBannerSelector selector);

    /**
     * banner详情
     *
     * @param id       id
     * @param selector selector
     * @return
     */
    DataResult<OperationBannerDTO> get(Long id, OperationBannerSelector selector);

    /**
     * 创建banner
     *
     * @param param param
     * @return
     */
    ActionResult create(BannerCreateParam param);

    /**
     * 修改banner
     *
     * @param param param
     * @return
     */
    ActionResult update(BannerUpdateParam param);

    /**
     * 删除banner
     *
     * @param id id
     * @return
     */
    ActionResult delete(Long id);
}
