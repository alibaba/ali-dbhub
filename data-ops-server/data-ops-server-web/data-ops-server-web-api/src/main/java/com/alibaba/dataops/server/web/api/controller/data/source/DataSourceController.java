package com.alibaba.dataops.server.web.api.controller.data.source;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.dataops.server.domain.core.api.param.DataSourceTestParam;
import com.alibaba.dataops.server.domain.core.api.service.DataSourceCoreService;
import com.alibaba.dataops.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dataops.server.web.api.controller.data.source.converter.DataSourceWebConverter;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceAttachRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceTestRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.vo.DatabaseVO;

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
        ListResult<DatabaseDTO> databaseDTOListResult = dataSourceCoreService.attach(request.getId());
        List<DatabaseVO> databaseVOS = dataSourceWebConverter.databaseDto2vo(databaseDTOListResult.getData());
        return ListResult.of(databaseVOS);
    }

}
