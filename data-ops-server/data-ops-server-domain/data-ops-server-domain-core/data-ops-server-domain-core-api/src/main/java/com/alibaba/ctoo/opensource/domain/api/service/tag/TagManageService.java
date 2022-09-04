package com.alibaba.ctoo.opensource.domain.api.service.tag;

import com.alibaba.ctoo.opensource.domain.api.param.tag.TagBatchCreateParam;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.base.wrapper.result.ListResult;

/**
 * 自定义tag服务操作
 * @author 知闰
 * @date 2022/03/23
 */
public interface TagManageService {

    /**
     * 判断传入的tag值是否已经存在，不存在则进行创建，最终返回所有的tag的主键ID
     * @param param
     * @return
     */
    ListResult<Long> judgeTagIsExistsAndGetTagId(TagBatchCreateParam param);

    /**
     * 刷新可展示的tag数据
     * @return
     */
    ActionResult refreshShowTag();
}
