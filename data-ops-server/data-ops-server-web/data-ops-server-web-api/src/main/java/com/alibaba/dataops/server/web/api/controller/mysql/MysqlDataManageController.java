package com.alibaba.dataops.server.web.api.controller.mysql;

import com.alibaba.dataops.server.domain.core.api.param.DataSourceExecuteParam;
import com.alibaba.dataops.server.domain.core.api.service.DataSourceCoreService;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dataops.server.web.api.controller.mysql.converter.MysqlDataConverter;
import com.alibaba.dataops.server.web.api.controller.mysql.request.DataExportRequest;
import com.alibaba.dataops.server.web.api.controller.mysql.request.DataManageRequest;

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
@RequestMapping("/api/mysql/data")
@RestController
public class MysqlDataManageController {

    @Autowired
    private DataSourceCoreService dataSourceCoreService;

    @Autowired
    private MysqlDataConverter mysqlDataConverter;

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
    public DataResult<Object> manage(@RequestBody DataManageRequest request) {
        DataSourceExecuteParam param = mysqlDataConverter.request2param(request);
        return dataSourceCoreService.execute(param);
    }

}
