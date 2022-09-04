package com.alibaba.ctoo.opensource.domain.api.param.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目统计结果查询选择器
 * @author 知闰
 * @date 2022/03/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGeneralStatisticSelector {

    private Boolean empty;
}
