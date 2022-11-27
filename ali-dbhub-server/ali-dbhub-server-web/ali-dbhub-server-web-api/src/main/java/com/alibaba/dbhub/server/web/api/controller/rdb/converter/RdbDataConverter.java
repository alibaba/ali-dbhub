package com.alibaba.dbhub.server.web.api.controller.rdb.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dbhub.server.domain.data.api.model.CellDTO;
import com.alibaba.dbhub.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dbhub.server.domain.data.api.model.TableColumnDTO;
import com.alibaba.dbhub.server.domain.data.api.model.TableDTO;
import com.alibaba.dbhub.server.domain.data.api.model.TableIndexDTO;
import com.alibaba.dbhub.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.data.api.param.table.TableQueryParam;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.DataManageRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableBriefQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableDetailQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableManageRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.CellVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ColumnVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ExecuteResultVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.IndexVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.TableVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.DataManageRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableBriefQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableDetailQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableManageRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.CellVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ColumnVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ExecuteResultVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.IndexVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.TableVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
    public abstract DataSourceExecuteParam tableManageRequest2param(TableManageRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract TableQueryParam tableRequest2param(TableDetailQueryRequest request);

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    public abstract TablePageQueryParam tablePageRequest2param(TableBriefQueryRequest request);

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

    /**
     * 模型转换
     *
     * @param dto
     * @return
     */
    public abstract ColumnVO columnDto2vo(TableColumnDTO dto);

    /**
     * 模型转换
     *
     * @param dtos
     * @return
     */
    public abstract List<ColumnVO> columnDto2vo(List<TableColumnDTO> dtos);

    /**
     * 模型转换
     *
     * @param dto
     * @return
     */
    @Mappings({
        @Mapping(source = "columnList", target = "columnList")
    })
    public abstract IndexVO indexDto2vo(TableIndexDTO dto);

    /**
     * 模型转换
     *
     * @param dtos
     * @return
     */
    public abstract List<IndexVO> indexDto2vo(List<TableIndexDTO> dtos);

    /**
     * 模型转换
     *
     * @param dto
     * @return
     */
    @Mappings({
        @Mapping(source = "columnList", target = "columnList"),
        @Mapping(source = "indexList", target = "indexList"),
    })
    public abstract TableVO tableDto2vo(TableDTO dto);

    /**
     * 模型转换
     *
     * @param dtos
     * @return
     */
    public abstract List<TableVO> tableDto2vo(List<TableDTO> dtos);
}
