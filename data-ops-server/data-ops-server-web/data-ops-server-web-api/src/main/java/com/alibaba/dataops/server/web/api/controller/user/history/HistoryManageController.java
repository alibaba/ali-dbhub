package com.alibaba.dataops.server.web.api.controller.user.history;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.model.UserExecutedDdlDTO;
import com.alibaba.dataops.server.domain.core.api.param.UserExecutedDdlCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.UserExecutedDdlPageQueryParam;
import com.alibaba.dataops.server.domain.core.api.service.UserExecutedDdlCoreService;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.web.WebPageResult;
import com.alibaba.dataops.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dataops.server.web.api.controller.user.history.converter.HistoryWebConverter;
import com.alibaba.dataops.server.web.api.controller.user.history.request.HistoryCreateRequest;
import com.alibaba.dataops.server.web.api.controller.user.history.request.HistoryQueryRequest;
import com.alibaba.dataops.server.web.api.controller.user.history.vo.HistoryVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 历史记录服务类
 *
 * @author moji
 * @version HistoryManageController.java, v 0.1 2022年09月18日 10:55 moji Exp $
 * @date 2022/09/18
 */
@BusinessExceptionAspect
@RequestMapping("/api/history")
@RestController
public class HistoryManageController {

    @Autowired
    private UserExecutedDdlCoreService userExecutedDdlCoreService;

    @Autowired
    private HistoryWebConverter historyWebConverter;

    /**
     * 查询执行记录
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public WebPageResult<HistoryVO> list(HistoryQueryRequest request) {
        UserExecutedDdlPageQueryParam param = historyWebConverter.req2param(request);
        PageResult<UserExecutedDdlDTO> result = userExecutedDdlCoreService.queryPage(param);
        List<HistoryVO> historyVOList = historyWebConverter.dto2vo(result.getData());
        return WebPageResult.of(historyVOList, result.getTotal(), result.getPageNo(), result.getPageSize());
    }

    /**
     * 新增历史记录
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public DataResult<Long> create(@RequestBody HistoryCreateRequest request) {
        UserExecutedDdlCreateParam param = historyWebConverter.createReq2param(request);
        return userExecutedDdlCoreService.create(param);
    }

}
