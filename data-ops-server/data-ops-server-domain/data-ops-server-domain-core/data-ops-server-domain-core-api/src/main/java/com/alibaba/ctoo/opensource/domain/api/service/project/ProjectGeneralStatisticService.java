package com.alibaba.ctoo.opensource.domain.api.service.project;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectGeneralStatisticDTO;
import com.alibaba.easytools.base.wrapper.result.DataResult;

/**
 * 项目数据服务
 *
 * @author 知闰
 * @date 2022/03/23
 */
public interface ProjectGeneralStatisticService {

    /**
     * 根据ID获取统计数据
     *
     * @param id 主键ID
     * @return
     */
    DataResult<ProjectGeneralStatisticDTO> get(Long id);

}
