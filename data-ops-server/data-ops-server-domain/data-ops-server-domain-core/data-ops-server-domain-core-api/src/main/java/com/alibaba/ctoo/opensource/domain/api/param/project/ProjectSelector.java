package com.alibaba.ctoo.opensource.domain.api.param.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目查询选择器
 * @author 知闰
 * @date 2022/03/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSelector {

    /**
     * 是否填充tag数据
     */
    private Boolean tagList;
}
