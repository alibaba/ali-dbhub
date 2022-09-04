package com.alibaba.ctoo.opensource.domain.api.param.article;

import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * banner分页查询
 *
 * @author hyh
 */
@Data
public class OperationBannerPageQueryParam extends PageQueryParam {

    /**
     * 排序
     */
    @Getter
    public enum OrderCondition {
        /**
         * 主键
         */
        ID("ID"),
        /**
         * 排序
         */
        ORDER_NUM("ORDERNUM"),
        /**
         * 修改时间
         */
        GMT_MODIFIED("GMTMODIFIED");

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
