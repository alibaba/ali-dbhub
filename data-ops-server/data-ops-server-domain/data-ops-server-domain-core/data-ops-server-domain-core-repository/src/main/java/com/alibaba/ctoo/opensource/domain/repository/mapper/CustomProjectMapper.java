package com.alibaba.ctoo.opensource.domain.repository.mapper;

import java.util.Date;
import java.util.List;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.CustomProjectStatisticDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.search.CustomProjectSearchParam;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义project dao接口
 *
 * @author 知闰
 * @date 2022/03/24
 */
@Mapper
public interface CustomProjectMapper {

    /**
     * 查询项目数据
     *
     * @param param
     * @return
     */
    List<Long> queryPageFromSearch(CustomProjectSearchParam param);

    /**
     * 查询项目数量
     *
     * @param param
     * @return
     */
    Long countQueryPageFromSearch(CustomProjectSearchParam param);

    /**
     * 查询展示项目统计数据 star和fork数量
     *
     * @param ids
     * @return
     */
    CustomProjectStatisticDO selectInDataProjectStatistic( @Param("ids") List<Long> ids);

    /**
     * 获取贡献者数量
     *
     * @param ids
     * @return
     */
    Integer selectInDataProjectContributorCount( @Param("ids") List<Long> ids);

    /**
     * 删除失效项目 以刷新时间作为判断标准
     * @param ltRefreshTime 小于该刷新时间
     * @return
     */
    Integer updateInvalidProject(Date ltRefreshTime);

    /**
     * 删除失效项目的关联标签数据
     * @param ltRefreshTime
     * @return
     */
    Integer updateInvalidProjectTag(Date ltRefreshTime);
}
