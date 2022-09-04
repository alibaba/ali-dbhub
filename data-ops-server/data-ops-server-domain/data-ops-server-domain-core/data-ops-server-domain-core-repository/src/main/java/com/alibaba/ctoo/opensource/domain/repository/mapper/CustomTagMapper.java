package com.alibaba.ctoo.opensource.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * tag 自定义mapper
 * @author 知闰
 * @date 2022/03/29
 */
@Mapper
public interface CustomTagMapper {

    /**
     * 批量修改tag标签的展示或者隐藏状态
     * @param tagShow
     * @param ids
     */
    void updateTagShowStatus(@Param("tagShow") String tagShow,@Param("ids") List<Long> ids);
}
