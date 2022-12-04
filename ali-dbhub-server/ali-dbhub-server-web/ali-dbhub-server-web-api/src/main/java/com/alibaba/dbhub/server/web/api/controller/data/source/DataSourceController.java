package com.alibaba.dbhub.server.web.api.controller.data.source;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.support.model.Database;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceTestParam;
import com.alibaba.dbhub.server.domain.api.service.DataSourceCoreService;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.controller.data.source.converter.DataSourceWebConverter;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.ConsoleCloseRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.ConsoleConnectRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceAttachRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceCloseRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.request.DataSourceTestRequest;
import com.alibaba.dbhub.server.web.api.controller.data.source.vo.DatabaseVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据库连接类
 *
 * @author moji
 * @version ConnectionController.java, v 0.1 2022年09月16日 14:07 moji Exp $
 * @date 2022/09/16
 */
@BusinessExceptionAspect
@RequestMapping("/api/connection")
@RestController
public class DataSourceController {

    @Autowired
    private DataSourceCoreService dataSourceCoreService;

    @Autowired
    private DataSourceWebConverter dataSourceWebConverter;

    /**
     * 数据库连接测试
     *
     * @param request
     * @return
     */
    @GetMapping("/test")
    public ActionResult test(DataSourceTestRequest request) {
        DataSourceTestParam param = dataSourceWebConverter.testRequest2param(request);
        return dataSourceCoreService.test(param);
    }

    /**
     * 数据库连接
     *
     * @param request
     * @return
     */
    @GetMapping("/attach")
    public ListResult<DatabaseVO> attach(@Valid @NotNull DataSourceAttachRequest request) {
        ListResult<Database> databaseDTOListResult = dataSourceCoreService.attach(request.getId());
        List<DatabaseVO> databaseVOS = dataSourceWebConverter.databaseDto2vo(databaseDTOListResult.getData());
        return ListResult.of(databaseVOS);
    }

    /**
     * 关闭数据库连接
     *
     * @param request
     * @return
     */
    @GetMapping("/close")
    public ActionResult close(@Valid @NotNull DataSourceCloseRequest request) {
        return dataSourceCoreService.close(request.getId());
    }

    /**
     * Console连接
     *
     * @param request
     * @return
     */
    @GetMapping("/console/connect")
    public ActionResult connect(@Valid @NotNull ConsoleConnectRequest request) {
        ConsoleConnectParam consoleConnectParam = dataSourceWebConverter.request2connectParam(request);
        return dataSourceCoreService.createConsole(consoleConnectParam);
    }

    /**
     * 关闭Console连接
     *
     * @param request
     * @return
     */
    @GetMapping("/console/close")
    public ActionResult closeConsole(@Valid @NotNull ConsoleCloseRequest request) {
        ConsoleCloseParam closeParam = dataSourceWebConverter.request2closeParam(request);
        return dataSourceCoreService.closeConsole(closeParam);
    }

}
