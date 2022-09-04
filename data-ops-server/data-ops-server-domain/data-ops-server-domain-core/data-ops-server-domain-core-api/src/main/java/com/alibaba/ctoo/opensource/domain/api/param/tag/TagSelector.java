package com.alibaba.ctoo.opensource.domain.api.param.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tag查询选择器
 * @author 知闰
 * @date 2022/03/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagSelector {

    private Boolean empty;
}
