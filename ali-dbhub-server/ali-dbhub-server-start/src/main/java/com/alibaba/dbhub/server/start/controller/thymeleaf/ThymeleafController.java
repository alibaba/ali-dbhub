package com.alibaba.dbhub.server.start.controller.thymeleaf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 模板引擎 配置
 *
 * @author Jiaju Zhuang
 */
@Controller
@Slf4j
public class ThymeleafController {

    /**
     * 前端的模板设置
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "/**"})
    public String index(Model model) {
        return "index";
    }
}
