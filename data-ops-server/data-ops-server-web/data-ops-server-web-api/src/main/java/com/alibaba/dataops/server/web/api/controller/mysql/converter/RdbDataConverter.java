package com.alibaba.dataops.server.web.api.controller.mysql.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dataops.server.domain.data.api.model.CellDTO;
import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dataops.server.web.api.controller.mysql.request.DataManageRequest;
import com.alibaba.dataops.server.web.api.controller.mysql.request.TableManageRequest;
import com.alibaba.dataops.server.web.api.controller.mysql.vo.CellVO;
import com.alibaba.dataops.server.web.api.controller.mysql.vo.ExecuteResultVO;

import org.mapstruct.Mapper;

/**
 * @author moji
 * @version MysqlDataConverter.java, v 0.1 2022年10月14日 14:04 moji Exp $
 * @date 2022/10/14
 */
@Mapper(componentModel = "spring")
public abstract class RdbDataConverter {

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract DataSourceExecuteParam request2param(DataManageRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract DataSourceExecuteParam tableRequest2param(TableManageRequest request);

    /**
     * 模型转换
     *
     * @param cellDTO
     * @return
     */
    public abstract CellVO cellDto2vo(CellDTO cellDTO);

    /**
     * 模型转换
     *
     * @param cellDTOS
     * @return
     */
    public abstract List<CellVO> cellDto2vo(List<CellDTO> cellDTOS);

    /**
     * 模型转换
     *
     * @param dto
     * @return
     */
    public abstract ExecuteResultVO dto2vo(ExecuteResultDTO dto);

    /**
     * 模型转换
     *
     * @param dtos
     * @return
     */
    public abstract List<ExecuteResultVO> dto2vo(List<ExecuteResultDTO> dtos);
}
