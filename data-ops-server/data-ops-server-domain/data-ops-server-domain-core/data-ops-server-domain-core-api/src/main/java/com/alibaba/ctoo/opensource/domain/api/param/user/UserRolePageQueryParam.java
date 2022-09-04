package com.alibaba.ctoo.opensource.domain.api.param.user;

import java.util.List;

import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * 用户角色
 *
 * @author qiuyuyu
 * @date 2022/03/03
 */
@Data
public class UserRolePageQueryParam extends PageQueryParam {

    /**
     * 关键词
     */
    private String searchKey;

    /**
     * 主键
     */
    private List<Long> idList;

    /**
     * 用户id
     */
    private List<String> userIdList;

    /**
     * 角色编码
     */
    private List<String> roleCodeList;

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
         * 修改时间
         */
        GMT_CREATE("GMTCREATE");

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
