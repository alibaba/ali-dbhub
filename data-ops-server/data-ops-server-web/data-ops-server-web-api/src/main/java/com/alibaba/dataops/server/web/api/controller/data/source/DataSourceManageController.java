package com.alibaba.dataops.server.web.api.controller.data.source;

import java.util.List;

import com.alibaba.dataops.server.domain.core.api.model.DataSourceDTO;
import com.alibaba.dataops.server.domain.core.api.service.DataSourceCoreService;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourcePageQueryParam;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceSelector;
import com.alibaba.dataops.server.domain.core.api.param.DataSourceUpdateParam;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.web.WebPageResult;
import com.alibaba.dataops.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dataops.server.web.api.controller.data.source.converter.DataSourceWebConverter;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceCloneRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceCreateRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceQueryRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.request.DataSourceUpdateRequest;
import com.alibaba.dataops.server.web.api.controller.data.source.vo.DataSourceVO;

import org.springframework.beans.factory.annotation.Autowired;
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
@BusinessExceptionAspect
@RequestMapping("/api/connection/manage")
@RestController
public class DataSourceManageController {

    @Autowired
    private DataSourceCoreService dataSourceCoreService;

    @Autowired
    private DataSourceWebConverter dataSourceWebConverter;

    /**
     * 查询我建立的数据库连接
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public WebPageResult<DataSourceVO> list(DataSourceQueryRequest request) {
        DataSourcePageQueryParam param = dataSourceWebConverter.queryReq2param(request);
        PageResult<DataSourceDTO> result = dataSourceCoreService.queryPage(param, new DataSourceSelector());
        List<DataSourceVO> dataSourceVOS = dataSourceWebConverter.dto2vo(result.getData());
        return WebPageResult.of(dataSourceVOS, result.getTotal(), result.getPageNo(), result.getPageSize());
    }

    /**
     * 获取连接内容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DataResult<DataSourceVO> queryById(@PathVariable("id") Long id) {
        DataResult<DataSourceDTO> dataResult = dataSourceCoreService.queryById(id);
        DataSourceVO dataSourceVO = dataSourceWebConverter.dto2vo(dataResult.getData());
        return DataResult.of(dataSourceVO);
    }

    /**
     * 保存连接
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public DataResult<Long> create(@RequestBody DataSourceCreateRequest request) {
        DataSourceCreateParam param = dataSourceWebConverter.createReq2param(request);
        return dataSourceCoreService.create(param);
    }

    /**
     * 更新连接
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    public ActionResult update(@RequestBody DataSourceUpdateRequest request) {
        DataSourceUpdateParam param = dataSourceWebConverter.updateReq2param(request);
        return dataSourceCoreService.update(param);
    }

    /**
     * 克隆连接
     *
     * @param request
     * @return
     */
    @PostMapping("/clone")
    public DataResult<Long> copy(@RequestBody DataSourceCloneRequest request) {
        return dataSourceCoreService.copyById(request.getId());
    }

    /**
     * 删除连接
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ActionResult delete(@PathVariable Long id) {
        return dataSourceCoreService.delete(id);
    }

}
