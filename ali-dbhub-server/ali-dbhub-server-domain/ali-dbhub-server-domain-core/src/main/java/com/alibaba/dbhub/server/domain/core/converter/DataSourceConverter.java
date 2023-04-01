package com.alibaba.dbhub.server.domain.core.converter;

import java.util.List;

import com.alibaba.dbhub.server.domain.api.model.DataSource;
import com.alibaba.dbhub.server.domain.api.param.DataSourceTestParam;
import com.alibaba.dbhub.server.domain.core.util.DesUtil;
import com.alibaba.dbhub.server.domain.api.param.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.api.param.ConsoleConnectParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourcePreConnectParam;
import com.alibaba.dbhub.server.domain.api.param.DataSourceUpdateParam;
import com.alibaba.dbhub.server.domain.repository.entity.DataSourceDO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author moji
 * @version DataSourceCoreConverter.java, v 0.1 2022年09月23日 15:53 moji Exp $
 * @date 2022/09/23
 */
@Slf4j
@Mapper(componentModel = "spring")
public abstract class DataSourceConverter {

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    @Mappings({
        @Mapping(target = "password", expression = "java(encryptString(param))")
    })
    public abstract DataSourceDO param2do(DataSourceCreateParam param);

    /**
     * encrypt
     *
     * @param param
     * @return
     */
    protected String encryptString(DataSourceCreateParam param) {
        String encryptStr = param.getPassword();
        if(StringUtils.isNotBlank(encryptStr)) {
            try {
                DesUtil desUtil = new DesUtil(DesUtil.DES_KEY);
                encryptStr = desUtil.encrypt(param.getPassword(), "CBC");
            } catch (Exception exception) {
                // do nothing
                log.error("encrypt error", exception);
            }
        }
        return encryptStr;
    }

    /**
     * encrypt
     *
     * @param param
     * @return
     */
    protected String encryptString(DataSourceUpdateParam param) {
        String encryptStr = param.getPassword();
        try {
            DesUtil desUtil = new DesUtil(DesUtil.DES_KEY);
            encryptStr = desUtil.encrypt(param.getPassword(), "CBC");
        } catch (Exception exception) {
            // do nothing
            log.error("encrypt error", exception);
        }
        return encryptStr;
    }

    /**
     * decrypt
     *
     * @param param
     * @return
     */
    protected String decryptString(DataSourceDO param) {
        String decryptStr = param.getPassword();
        try {
            DesUtil desUtil = new DesUtil(DesUtil.DES_KEY);
            decryptStr = desUtil.decrypt(param.getPassword(), "CBC");
        } catch (Exception exception) {
            // do nothing
            log.error("encrypt error", exception);
        }
        return decryptStr;
    }

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    @Mappings({
        @Mapping(target = "password", expression = "java(encryptString(param))")
    })
    public abstract DataSourceDO param2do(DataSourceUpdateParam param);

    /**
     * 参数转换
     *
     * @param param
     * @return
     */
    public abstract ConsoleCreateParam param2consoleParam(ConsoleConnectParam param);

    /**
     * 参数转换
     *
     * @param dataSourcePreConnectParam
     * @return
     */
    @Mappings({
        @Mapping(source = "type", target = "dbType"),
        @Mapping(source = "user", target = "username")
    })
    public abstract DataSourceTestParam param2param(
        DataSourcePreConnectParam dataSourcePreConnectParam);

    /**
     * 模型转换
     *
     * @param dataSourceDO
     * @return
     */
    @Mappings({
        @Mapping(target = "password", expression = "java(decryptString(dataSourceDO))")
    })
    public abstract DataSource do2dto(DataSourceDO dataSourceDO);

    /**
     * 模型转换
     *
     * @param dataSourceDOList
     * @return
     */
    public abstract List<DataSource> do2dto(List<DataSourceDO> dataSourceDOList);
}
