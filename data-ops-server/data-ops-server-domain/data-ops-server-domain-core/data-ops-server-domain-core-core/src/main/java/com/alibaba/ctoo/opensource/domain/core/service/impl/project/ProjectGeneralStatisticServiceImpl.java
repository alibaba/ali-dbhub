package com.alibaba.ctoo.opensource.domain.core.service.impl.project;

import com.alibaba.ctoo.opensource.domain.api.model.ProjectGeneralStatisticDTO;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectGeneralStatisticService;
import com.alibaba.ctoo.opensource.domain.core.converter.ProjectGeneralStatisticConverter;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectGeneralStatisticDO;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectGeneralStatisticMapper;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.DataResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Service
public class ProjectGeneralStatisticServiceImpl implements ProjectGeneralStatisticService {

    @Autowired
    private ProjectGeneralStatisticMapper projectGeneralStatisticMapper;

    @Autowired
    private ProjectGeneralStatisticConverter projectGeneralStatisticConverter;

    @Override
    public DataResult<ProjectGeneralStatisticDTO> get(Long id) {
        ProjectGeneralStatisticDO projectGeneralStatisticDO = projectGeneralStatisticMapper.selectByPrimaryKey(id);
        if (null == projectGeneralStatisticDO) {
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR);
        }
        return DataResult.of(projectGeneralStatisticConverter.doToDto(projectGeneralStatisticDO));
    }

}
