package com.alibaba.ctoo.opensource.domain.api.param.article;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;
import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * 分页查询参数
 *
 * @author hyh
 */
@Data
public class OperationArticlePageQueryFromSearchParam extends PageQueryParam {

    /**
     * 文章类型
     *
     * @see ArticleTypeEnum
     */
    private String type;

    /**
     * 排序
     */
    @Getter
    public enum OrderCondition {
        /**
         * 主键
         */
        ID("a.id"),
        /**
         * 排序
         */
        ORDER_NUM("a.order_num"),
        /**
         * 修改时间
         */
        GMT_MODIFIED("a.gmt_modified");

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

}
