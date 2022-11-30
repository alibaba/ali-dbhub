package com.alibaba.dbhub.server.web.api.controller.rdb;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.param.DataSourceExecuteParam;
import com.alibaba.dbhub.server.domain.api.service.DataSourceCoreService;
import com.alibaba.dbhub.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.controller.rdb.converter.RdbDataConverter;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.DataExportRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.DataManageRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ExecuteResultVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mysql数据运维类
 *
 * @author moji
 * @version MysqlDataManageController.java, v 0.1 2022年09月16日 17:37 moji Exp $
 * @date 2022/09/16
 */
@BusinessExceptionAspect
@RequestMapping("/api/rdb/data")
@RestController
public class RdbDataManageController {

    @Autowired
    private DataSourceCoreService dataSourceCoreService;

    @Autowired
    private RdbDataConverter rdbDataConverter;

    /**
     * 导出结果集Excel
     *
     * @param request
     * @return
     */
    @GetMapping("/export/excel")
    public ActionResult export(DataExportRequest request) {
        return null;
    }

    /**
     * 导出结果集Insert
     *
     * @param request
     * @return
     */
    @GetMapping("/export/insert")
    public ActionResult exportInsert(DataExportRequest request) {
        return null;
    }

    /**
     * 导出选中行Insert
     *
     * @param request
     * @return
     */
    @GetMapping("/export/insert/selected")
    public ActionResult exportInsertSelected(DataExportRequest request) {
        return null;
    }

    /**
     * 增删改查等数据运维
     *
     * @param request
     * @return
     */
    @PutMapping("/manage")
    public ListResult<ExecuteResultVO> manage(@RequestBody DataManageRequest request) {
        DataSourceExecuteParam param = rdbDataConverter.request2param(request);
        ListResult<ExecuteResultDTO> resultDTOListResult = dataSourceCoreService.execute(param);
        List<ExecuteResultVO> resultVOS = rdbDataConverter.dto2vo(resultDTOListResult.getData());
        return ListResult.of(resultVOS);
    }

}
