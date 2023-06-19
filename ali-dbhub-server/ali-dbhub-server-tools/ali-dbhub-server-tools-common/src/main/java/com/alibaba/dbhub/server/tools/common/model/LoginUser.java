package com.alibaba.dbhub.server.tools.common.model;

import com.alibaba.dbhub.server.tools.base.constant.EasyToolsConstant;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 登录的用户信息
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {
    @Serial private static final long serialVersionUID = EasyToolsConstant.SERIAL_VERSION_UID;

    /** 用户id */
    private Long id;

    /** 昵称 */
    private String nickName;
}
