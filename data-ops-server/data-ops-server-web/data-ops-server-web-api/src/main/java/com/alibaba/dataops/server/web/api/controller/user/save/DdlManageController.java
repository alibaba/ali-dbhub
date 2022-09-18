package com.alibaba.dataops.server.web.api.controller.user.save;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.web.api.controller.user.save.request.DdlCreateRequest;
import com.alibaba.dataops.server.web.api.controller.user.save.request.DdlQueryRequest;
import com.alibaba.dataops.server.web.api.controller.user.save.request.DdlUpdateRequest;
import com.alibaba.dataops.server.web.api.controller.user.save.vo.DdlVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的保存服务类
 *
 * @author moji
 * @version DdlManageController.java, v 0.1 2022年09月16日 19:59 moji Exp $
 * @date 2022/09/16
 */
@RequestMapping("/api/ddl")
@RestController
public class DdlManageController {

    /**
     * 查询我的保存
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public PageResult<DdlVO> list(DdlQueryRequest request) {
        return null;
    }

    /**
     * 新增我的保存
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public DataResult<Long> create(@RequestBody DdlCreateRequest request) {
        return null;
    }

    /**
     * 更新我的保存
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    public ActionResult update(@RequestBody DdlUpdateRequest request) {
        return null;
    }


    /**
     * 删除我的保存
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ActionResult delete(@PathVariable("id") Long id) {
        return null;
    }
}
