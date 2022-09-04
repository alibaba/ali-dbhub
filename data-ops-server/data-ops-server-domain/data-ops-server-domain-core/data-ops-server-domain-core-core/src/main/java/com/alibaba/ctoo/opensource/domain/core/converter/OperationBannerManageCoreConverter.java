package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;

import com.alibaba.ctoo.opensource.domain.api.model.OperationBannerDTO;
import com.alibaba.ctoo.opensource.domain.api.param.banner.BannerCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.banner.BannerUpdateParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.OperationBannerDO;
import com.alibaba.easytools.base.enums.DeletedIdEnum;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * banner转换器
 *
 * @author hyh
 */
@Mapper(componentModel = "spring", imports = DeletedIdEnum.class)
public abstract class OperationBannerManageCoreConverter {

    /**
     * 转换
     *
     * @param list list
     * @return
     */
    public abstract List<OperationBannerDTO> do2dto(List<OperationBannerDO> list);

    /**
     * 转换
     *
     * @param operationBannerDO operationBannerDO
     * @return
     */
    @Mappings({
        @Mapping(target = "creator.userId", source = "creator"),
        @Mapping(target = "modifier.userId", source = "modifier"),
    })
    public abstract OperationBannerDTO do2dto(OperationBannerDO operationBannerDO);

    /**
     * 转换
     *
     * @param param
     * @param tenantId
     * @param creator
     * @param modifier
     * @return
     */
    @Mappings({
        @Mapping(target = "deletedId", expression = "java(DeletedIdEnum.NOT_DELETED.getCode())"),
    })
    public abstract OperationBannerDO param2do(BannerCreateParam param, String tenantId, String creator,
        String modifier);

    /**
     * 转换
     *
     * @param param    param
     * @param modifier modifier
     * @return
     */
    public abstract OperationBannerDO param2do(BannerUpdateParam param, String modifier);
}
