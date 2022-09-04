package com.alibaba.ctoo.opensource.domain.api.param.user;

import java.util.List;

import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.Data;
import lombok.Getter;

/**
 * 用户角色分页查询
 * 搜索引擎
 *
 * @author qiuyuyu
 * @date 2022/03/10
 */
@Data
public class UserRolePageQueryFromSearchParam extends PageQueryParam {
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
     * 用户id
     */
    private String userId;

    /**
     * 真实名称
     */
    private String name;

    /**
     * 花名
     */
    private String nickName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 排序
     */
    @Getter
    public enum OrderCondition {
        /**
         * ID
         */
        ID("ur.id"),

        /**
         * 修改时间
         */
        GMT_MODIFIED("ur.gmt_modified"),
        ;

        private String columnName;

        OrderCondition(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
