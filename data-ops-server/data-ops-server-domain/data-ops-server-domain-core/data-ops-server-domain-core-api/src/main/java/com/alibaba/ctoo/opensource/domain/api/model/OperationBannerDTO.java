package com.alibaba.ctoo.opensource.domain.api.model;

import java.util.Date;

import lombok.Data;

/**
 * banner运营
 *
 * @author hyh
 */
@Data
public class OperationBannerDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 图片地址
     */
    private String pictureUrl;

    /**
     * 跳转地址
     */
    private String jumpUrl;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 开始时间
     */
    private Date gmtStart;

    /**
     * 结束时间
     */
    private Date gmtEnd;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private UserDTO creator;

    /**
     * 修改人
     */
    private UserDTO modifier;

}
