package com.alibaba.dbhub.server.web.api.controller.data.source;

import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.controller.data.source.vo.EnvironmentVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台应用/环境打标服务类
 *
 * @author moji
 * @version EnvController.java, v 0.1 2022年09月18日 14:04 moji Exp $
 * @date 2022/09/18
 */
@BusinessExceptionAspect
@RequestMapping("/api/web/environment")
@RestController
public class EnvironmentController {

    /**
     * 查询环境列表
     * <h4>1.1.0</h4>
     * <ul>
     *     <li>新增接口</li>
     * </ul>
     *
     * @return
     * @version 1.1.0
     */
    @GetMapping("/list")
    public ListResult<EnvironmentVO> list() {
        return null;
    }

}
