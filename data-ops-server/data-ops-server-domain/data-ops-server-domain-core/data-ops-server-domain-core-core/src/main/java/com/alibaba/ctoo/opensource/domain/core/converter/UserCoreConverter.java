package com.alibaba.ctoo.opensource.domain.core.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserDO;
import com.alibaba.ctoo.opensource.domain.api.model.UserDTO;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserCreateParam;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserSelector;
import com.alibaba.ctoo.opensource.domain.api.param.user.UserUpdateParam;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserDomainService;
import com.alibaba.easytools.common.util.EasyStringUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * @author qiuyuyu
 * @date 2022/03/07
 */
@Mapper(componentModel = "spring", imports = EasyStringUtils.class)
public abstract class UserCoreConverter {

    @Resource
    protected UserDomainService userDomainService;

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<UserDTO> do2dto(List<UserDO> list);

    /**
     * 转换
     *
     * @param userDO
     * @return
     */
    @Mappings({
        @Mapping(target = "showName",
            expression = "java(EasyStringUtils.buildShowName(userDO.getName(),userDO.getNickName()))")
    })
    public abstract UserDTO do2dto(UserDO userDO);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    @Mappings({
        @Mapping(target = "userId",expression = "java(EasyStringUtils.cutUserId(param.getUserId()))"),
    })
    public abstract UserDO param2do(UserCreateParam param);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract UserDO param2do(UserUpdateParam param);

    /**
     * 转换
     *
     * @param target
     * @param source
     */
    @Mappings({
        @Mapping(target = "userId", ignore = true),
    })
    protected abstract void addUser(@MappingTarget UserDTO target, UserDTO source);

    /**
     * 填充用户信息
     *
     * @param userList
     */
    public void fillUserDetail(List<UserDTO> userList, UserSelector selector) {
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        List<String> userIdList = userList.stream().map(UserDTO::getUserId).collect(Collectors.toList());
        Map<String, UserDTO> userMap = userDomainService.queryMap(userIdList, selector).getData();
        for (UserDTO userDTO : userList) {
            if (userDTO == null || StringUtils.isBlank(userDTO.getUserId())) {
                continue;
            }
            UserDTO queryUser = userMap.get(userDTO.getUserId());
            if (queryUser == null) {
                continue;
            }
            addUser(userDTO, queryUser);
        }
    }
}
