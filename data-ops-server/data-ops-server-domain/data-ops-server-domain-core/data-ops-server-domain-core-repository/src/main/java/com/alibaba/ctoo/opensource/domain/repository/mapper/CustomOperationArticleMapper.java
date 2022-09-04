package com.alibaba.ctoo.opensource.domain.repository.mapper;

import java.util.Date;
import java.util.List;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomOperationArticleSearchParam;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义OperationArticleMapper
 *
 * @author hyh
 */
@Mapper
public interface CustomOperationArticleMapper {

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

    /**
     * 根据条件查询数据
     *
     * @param param param
     * @return
     */
    List<Long> queryPageByParam(CustomOperationArticleSearchParam param);

    /**
     * 根据条件查询数量
     *
     * @param param param
     * @return
     */
    long countQueryPageByParam(CustomOperationArticleSearchParam param);

}
