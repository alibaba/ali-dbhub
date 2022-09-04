package com.alibaba.ctoo.opensource.domain.api.param.project;

import java.util.Date;
import java.util.List;

import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * 项目分页查询参数
 *
 * @author 知闰
 * @date 2022/03/24
 */
@Data
public class ProjectPageQueryParam extends PageQueryParam {

    /**
     * 展示名
     */
    private String showName;

    /**
     * 刷新时间 大于该值
     */
    private Date gtRefreshTime;

    /**
     * 刷新时间 小于该值
     */
    private Date ltRefreshTime;

    /**
     * 语言标签列表
     */
    private List<Long> languageTagIdList;


    /**
     * 领域标签列表
     */
    private List<Long> domainTagIdList;


    /**
     * 基础标签列表
     */
    private List<Long> basisTagIdList;

    /**
     * 是否展示
     *
     * @see com.alibaba.ctoo.opensource.domain.api.enums.ShowEnum
     */
    private String inShow;

    /**
     * 文档url不为空
     */
    private Boolean documentUrlIsNotNull;

    /**
     * 排序
     */
    @Getter
    public enum OrderCondition {
        /**
         * star数量
         */
        START_COUNT("STARCOUNT");

        private final String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
