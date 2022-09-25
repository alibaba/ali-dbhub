package com.alibaba.dataops.server.web.api.controller.user.history;

import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.web.WebPageResult;
import com.alibaba.dataops.server.web.api.controller.user.history.request.HistoryCreateRequest;
import com.alibaba.dataops.server.web.api.controller.user.history.request.HistoryQueryRequest;
import com.alibaba.dataops.server.web.api.controller.user.history.vo.HistoryVO;

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
@RequestMapping("/api/history")
@RestController
public class HistoryManageController {

    /**
     * 查询执行记录
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public WebPageResult<HistoryVO> list(HistoryQueryRequest request) {
        return null;
    }

    /**
     * 新增历史记录
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public DataResult<Long> create(@RequestBody HistoryCreateRequest request) {
        return null;
    }

}
