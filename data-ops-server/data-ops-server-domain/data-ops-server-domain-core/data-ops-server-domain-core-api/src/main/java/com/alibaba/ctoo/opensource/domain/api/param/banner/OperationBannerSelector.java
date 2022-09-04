package com.alibaba.ctoo.opensource.domain.api.param.banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * banner选择器
 *
 * @author hyh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationBannerSelector {

    /**
     * 创建人
     */
    private Boolean creatorDetail;

    /**
     * 修改人
     */
    private Boolean modifierDetail;

}
