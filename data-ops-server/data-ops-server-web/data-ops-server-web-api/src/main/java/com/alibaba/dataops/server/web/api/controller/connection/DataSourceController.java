package com.alibaba.dataops.server.web.api.controller.connection;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.web.api.controller.connection.request.DataSourceAttachRequest;
import com.alibaba.dataops.server.web.api.controller.connection.request.DataSourceTestRequest;
import com.alibaba.dataops.server.web.api.controller.connection.vo.DatabaseVO;

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
@RequestMapping("/api/connection")
@RestController
public class DataSourceController {

    /**
     * 数据库连接测试
     *
     * @param request
     * @return
     */
    @GetMapping("/test")
    public ActionResult test(DataSourceTestRequest request) {
        return null;
    }

    /**
     * 数据库连接
     *
     * @param request
     * @return
     */
    @GetMapping("/attach")
    public ListResult<DatabaseVO> attach(DataSourceAttachRequest request) {
        return null;
    }

}
