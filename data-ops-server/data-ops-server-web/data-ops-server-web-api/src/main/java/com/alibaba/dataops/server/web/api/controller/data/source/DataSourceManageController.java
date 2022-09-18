package com.alibaba.dataops.server.web.api.controller.data.source;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceCloneRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceCreateRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceQueryRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceUpdateRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.vo.DataSourceVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据库连接管理类
 *
 * @author moji
 * @version ConnectionManageController.java, v 0.1 2022年09月16日 14:07 moji Exp $
 * @date 2022/09/16
 */
@RequestMapping("/api/connection/manage")
@RestController
public class DataSourceManageController {

    /**
     * 查询我建立的数据库连接
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public PageResult<DataSourceVO> list(DataSourceQueryRequest request) {
        return null;
    }

    /**
     * 获取连接内容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DataResult<DataSourceVO> queryById(@PathVariable("id") Long id) {
        return null;
    }

    /**
     * 保存连接
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public DataResult<Long> create(@RequestBody DataSourceCreateRequest request) {
        return null;
    }


    /**
     * 更新连接
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    public ActionResult update(@RequestBody DataSourceUpdateRequest request) {
        return null;
    }

    /**
     * 克隆连接
     *
     * @param request
     * @return
     */
    @PostMapping("/clone")
    public DataResult<Long> copy(@RequestBody DataSourceCloneRequest request) {
        return null;
    }

    /**
     * 删除连接
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ActionResult delete(@PathVariable Long id) {
        return null;
    }

}
