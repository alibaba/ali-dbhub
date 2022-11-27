package com.alibaba.dbhub.server.web.api.controller.user.save;

import java.util.List;
import java.util.Objects;

import com.alibaba.dbhub.server.domain.core.api.model.UserSavedDdlDTO;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlCreateParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlPageQueryParam;
import com.alibaba.dbhub.server.domain.core.api.param.UserSavedDdlUpdateParam;
import com.alibaba.dbhub.server.domain.core.api.service.UserSavedDdlCoreService;
import com.alibaba.dbhub.server.tools.base.enums.StatusEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.web.WebPageResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.controller.user.save.converter.DdlManageWebConverter;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlUpdateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.vo.DdlVO;
import com.alibaba.dbhub.server.web.api.controller.user.save.converter.DdlManageWebConverter;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlCreateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.request.DdlUpdateRequest;
import com.alibaba.dbhub.server.web.api.controller.user.save.vo.DdlVO;

import org.apache.commons.lang3.StringUtils;
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
 * 我的保存服务类
 *
 * @author moji
 * @version DdlManageController.java, v 0.1 2022年09月16日 19:59 moji Exp $
 * @date 2022/09/16
 */
@BusinessExceptionAspect
@RequestMapping("/api/ddl")
@RestController
public class DdlManageController {

    @Autowired
    private UserSavedDdlCoreService userSavedDdlCoreService;

    @Autowired
    private DdlManageWebConverter ddlManageWebConverter;

    /**
     * 查询我的保存
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public WebPageResult<DdlVO> list(DdlQueryRequest request) {
        UserSavedDdlPageQueryParam param = ddlManageWebConverter.queryReq2param(request);
        if (StringUtils.isNotBlank(request.getDatabaseName()) && Objects.nonNull(request.getDataSourceId())) {
            // 如果db不为空，则只查询db下面关联的临时保存
            param.setStatus(StatusEnum.DRAFT.getCode());
        } else {
            param.setStatus(StatusEnum.RELEASE.getCode());
        }
        PageResult<UserSavedDdlDTO> dtoPageResult = userSavedDdlCoreService.queryPage(param);
        List<DdlVO> ddlVOS = ddlManageWebConverter.dto2vo(dtoPageResult.getData());
        return WebPageResult.of(ddlVOS, dtoPageResult.getTotal(), request.getPageNo(), request.getPageSize());
    }

    /**
     * 新增我的保存
     *
     * @param request
     * @return
     */
    @PostMapping("/create")
    public DataResult<Long> create(@RequestBody DdlCreateRequest request) {
        UserSavedDdlCreateParam param = ddlManageWebConverter.req2param(request);
        return userSavedDdlCoreService.create(param);
    }

    /**
     * 更新我的保存
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    public ActionResult update(@RequestBody DdlUpdateRequest request) {
        UserSavedDdlUpdateParam param = ddlManageWebConverter.updateReq2param(request);
        return userSavedDdlCoreService.update(param);
    }

    /**
     * 删除我的保存
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ActionResult delete(@PathVariable("id") Long id) {
        return userSavedDdlCoreService.delete(id);
    }
}
