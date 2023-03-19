package com.alibaba.dbhub.server.tools.common.exception;

import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.common.enums.ErrorEnum;

import lombok.Getter;

/**
 * 需要重定向的业务异常
 *
 * @author Jiaju Zhuang
 */
@Getter
public class RedirectBusinessException extends BusinessException {

    private final String redirect;

    public RedirectBusinessException(String redirect) {
        super(ErrorEnum.REDIRECT);
        this.redirect = redirect;
    }
}