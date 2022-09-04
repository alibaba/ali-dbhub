package com.alibaba.ctoo.opensource.domain.repository.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义OperationBannerMapper
 *
 * @author hyh
 */
@Mapper
public interface CustomOperationBannerMapper {

    /**
     * 根据主键选择性更新
     *
     * @param id       id
     * @param gmtStart gmtStart
     * @param gmtEnd   gmtEnd
     * @return
     */
    int updateGmtStartEndByPrimaryKey(@Param("id") Long id, @Param("gmtStart") Date gmtStart,
        @Param("gmtEnd") Date gmtEnd);
}
