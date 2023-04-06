package com.alibaba.dbhub.server.web.api.controller.data.source.converter;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.web.api.controller.data.source.vo.DataSourceFormVO;
import com.alibaba.dbhub.server.web.api.controller.data.source.vo.FormItemVO;

import java.util.ArrayList;
import java.util.List;

public class DatasourceBuilder {

    static {
        DataSourceFormVO mysql = new DataSourceFormVO();
        List<FormItemVO> mysqlItems = new ArrayList<>();
        mysql.setType(DbTypeEnum.MYSQL.getCode());
        mysql.setIcon("mysql");
        mysql.setItems(mysqlItems);
        FormItemVO item = new FormItemVO();
        item.setName("");

    }
}
