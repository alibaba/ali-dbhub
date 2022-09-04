package com.alibaba.ctoo.opensource.domain.api.param.article;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;
import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * 博客 活动 新闻分页查询
 *
 * @author hyh
 */
@Data
public class OperationArticlePageQueryParam extends PageQueryParam {

    /**
     * 类型
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
