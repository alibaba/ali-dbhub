package com.alibaba.ctoo.opensource.domain.core.converter;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectGeneralStatisticDTO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectGeneralStatisticDO;

import org.mapstruct.Mapper;

/**
 * 项目统计结果转换器
 * @author 知闰
 * @date 2022/03/23
 */
@Mapper(componentModel = "spring")
public abstract class ProjectGeneralStatisticConverter {

    /**
     * 转换器
     * @param projectGeneralStatisticDO
     * @return
     */
    public abstract ProjectGeneralStatisticDTO doToDto(ProjectGeneralStatisticDO projectGeneralStatisticDO);
}
