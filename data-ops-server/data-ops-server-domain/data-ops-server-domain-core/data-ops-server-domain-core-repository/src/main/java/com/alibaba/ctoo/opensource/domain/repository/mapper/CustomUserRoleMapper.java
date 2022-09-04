package com.alibaba.ctoo.opensource.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author qiuyuyu
 * @date 2022/03/22
 */
@Mapper
public interface CustomUserRoleMapper {

    /**
     * 逻辑删除用户角色
     *
     * @param id
     */
    void logicDelete(@Param("id") Long id);

}
