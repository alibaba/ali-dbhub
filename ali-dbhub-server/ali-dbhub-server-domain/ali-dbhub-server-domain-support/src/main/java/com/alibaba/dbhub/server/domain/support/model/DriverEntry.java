/** alibaba.com Inc. Copyright (c) 2004-2023 All Rights Reserved. */
package com.alibaba.dbhub.server.domain.support.model;

import com.alibaba.dbhub.server.domain.support.enums.DriverTypeEnum;
import java.sql.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jipengfei
 * @version : DriverEntry.java
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntry {

    private DriverTypeEnum driverTypeEnum;

    private Driver driver;
}
