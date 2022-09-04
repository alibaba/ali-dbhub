package com.alibaba.ctoo.opensource.domain.api.param.tag;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.enums.ProjectTagTypeEnum;
import com.alibaba.ctoo.opensource.domain.api.enums.TagShowEnum;
import com.alibaba.easytools.base.wrapper.param.QueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * tag 查询参数
 *
 * @author 知闰
 * @date 2022/03/23
 */
@Data
public class TagQueryParam extends QueryParam {

    /**
     * 标签值
     */
    private String tagValue;

    /**
     * tag列表
     */
    private List<String> tagValueList;

    /**
     * 标签类型
     *
     * @see ProjectTagTypeEnum
     */
    private ProjectTagTypeEnum tagTypeEnum;

    /**
     * tag是否展示
     * @see TagShowEnum
     */
    private String tagShow;


    @Getter
    public enum OrderCondition {

        /**
         * 排序
         */
        ORDERNUM("ORDERNUM");

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
