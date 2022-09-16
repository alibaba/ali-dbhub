package com.alibaba.dataops.server.web.api.controller.redis;

import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.web.api.controller.redis.request.KeyValueManageRequest;
import com.alibaba.dataops.server.web.api.controller.redis.request.KeyQueryRequest;
import com.alibaba.dataops.server.web.api.controller.redis.request.ValueUpdateRequest;
import com.alibaba.dataops.server.web.api.controller.redis.vo.KeyVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis数据运维类
 *
 * @author moji
 * @version MysqlDataManageController.java, v 0.1 2022年09月16日 17:37 moji Exp $
 * @date 2022/09/16
 */
@RequestMapping("/api/redis/kv")
@RestController
public class RedisKeyValueManageController {

    /**
     * redis ddl命令执行
     *
     * @param request
     * @return
     */
    @PutMapping("/manage")
    public DataResult<Object> manage(@RequestBody KeyValueManageRequest request) {
        return null;
    }

    /**
     * 获取缓存key详情
     *
     * @param request
     * @return
     */
    @GetMapping("/query")
    public DataResult<KeyVO> query(KeyQueryRequest request) {
        return null;
    }

    /**
     * 更新key值
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    public ActionResult update(@RequestBody ValueUpdateRequest request) {
        return null;
    }

}
