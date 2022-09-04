package com.alibaba.ctoo.opensource.domain.api.param.article;

import com.alibaba.ctoo.opensource.domain.api.enums.ArticleStatusEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.ArticleTypeEnum;
import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * 文章分页查询参数
 *
 * @author zyb
 */
@Data
public class ArticlePageQueryFromSearchParam extends PageQueryParam {

    /**
     * 搜索key
     */
    private String searchKey;

    /**
     * 状态
     *
     * @see ArticleStatusEnum
     */
    private String status;

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
         * 修改时间
         */
        GMT_MODIFIED("a.gmt_modified"),

        /**
         * 修改时间
         */
        GMT_RELEASE("a.gmt_release");

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

}
