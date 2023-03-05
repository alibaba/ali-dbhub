package com.alibaba.dbhub.server.tools.common.exception;

import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.common.enums.ErrorEnum;

import lombok.Getter;

/**
 * 用户登录异常
 *
 * @author Jiaju Zhuang
 */
@Getter
public class NeedLoggedInBizException extends BusinessException {

    public NeedLoggedInBizException() {
        super(ErrorEnum.NEED_LOGGED_IN);
    }
}