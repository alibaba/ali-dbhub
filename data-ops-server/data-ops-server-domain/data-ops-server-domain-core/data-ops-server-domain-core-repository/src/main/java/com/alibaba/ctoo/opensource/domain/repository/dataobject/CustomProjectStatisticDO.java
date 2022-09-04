package com.alibaba.ctoo.opensource.domain.repository.dataobject;

import lombok.Data;

/**
 * 自定义project统计数据结果接收类
 * @author 知闰
 * @date 2022/03/24
 */
@Data
public class CustomProjectStatisticDO {

    /**
     * 点赞数
     */
    private Integer starCount;

    /**
     * 分支数量
     */
    private Integer forkCount;

    /**
     * 贡献者数量
     */
    private Integer contributorCount;
}
