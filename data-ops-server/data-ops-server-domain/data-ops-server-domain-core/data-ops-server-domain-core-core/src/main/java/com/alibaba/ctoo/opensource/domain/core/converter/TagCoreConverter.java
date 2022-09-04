package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.TagDTO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.TagDO;

import org.mapstruct.Mapper;

/**
 * @author 知闰
 * @date 2022/03/23
 */
@Mapper(componentModel = "spring")
public abstract class TagCoreConverter {

    /**
     * do转dto
     * @param tagDO
     * @return
     */
    public abstract TagDTO do2dto(TagDO tagDO);


    /**
     * do转dto
     * @param tagDoList
     * @return
     */
    public abstract List<TagDTO> do2dto(List<TagDO> tagDoList);
}
