package com.alibaba.ctoo.opensource.domain.repository.dataobject.search;

import com.alibaba.easytools.base.wrapper.param.OrderBy;

import lombok.Data;

/**
 * 基础查询
 *
 * @author zyb
 */
@Data
public class BaseParam {

    /**
     * 是否分页
     */
    private boolean _page;

    /**
     * 分页index
     */
    private int _pageIndex;

    /**
     * 分页大小
     */
    private int _pageSize;

    /**
     * 排序
     */
    protected String _orderByClause;

    public void setPagination(int pageStart, int pageSize) {
        this._page = true;
        this._pageSize = pageSize < 1 ? 10 : pageSize;
        this._pageIndex = pageStart < 1 ? 0 : (pageStart - 1) * this._pageSize;
    }

    public void appendOrderByClause(OrderBy orderBy) {
        if (orderBy == null) {
            return;
        }
        if (null != _orderByClause) {
            _orderByClause = _orderByClause + ", " + orderBy.getOrderConditionName() + " " + orderBy.getDirection()
                    .name();
        } else {
            _orderByClause = orderBy.getOrderConditionName() + " " + orderBy.getDirection().name();
        }
    }

}
