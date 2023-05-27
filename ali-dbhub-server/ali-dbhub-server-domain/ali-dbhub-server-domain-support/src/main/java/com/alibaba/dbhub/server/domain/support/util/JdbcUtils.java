package com.alibaba.dbhub.server.domain.support.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Map;

import com.alibaba.dbhub.server.domain.support.enums.CellTypeEnum;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.enums.DriverTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Cell;
import com.alibaba.dbhub.server.domain.support.model.DataSourceConnect;
import com.alibaba.dbhub.server.domain.support.model.SSHInfo;
import com.alibaba.dbhub.server.domain.support.sql.IDriverManager;
import com.alibaba.dbhub.server.domain.support.sql.SSHManager;
import com.alibaba.dbhub.server.tools.common.util.EasyOptionalUtils;
import com.alibaba.druid.DbType;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * jdbc工具类
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class JdbcUtils {

    /**
     * 获取德鲁伊的的数据库类型
     *
     * @param dbType
     * @return
     */
    public static DbType parse2DruidDbType(DbTypeEnum dbType) {
        if (dbType == null) {
            return null;
        }
        return DbType.valueOf(dbType.getCode().toLowerCase());
    }

    /**
     * 获取一个返回值
     *
     * @param rs
     * @param index
     * @return
     * @throws SQLException
     */
    public static Cell getResultSetValue(ResultSet rs, int index) throws SQLException {
        Cell cell = new Cell();
        Object obj = rs.getObject(index);
        if (obj == null) {
            cell.setType(CellTypeEnum.EMPTY.getCode());
            return cell;
        }

        if (obj instanceof Blob) {
            Blob blob = (Blob)obj;
            cell.setType(CellTypeEnum.BYTE.getCode());
            cell.setByteValue(blob.getBytes(1, (int)blob.length()));
            return cell;
        }
        if (obj instanceof Clob) {
            Clob clob = (Clob)obj;
            cell.setType(CellTypeEnum.STRING.getCode());
            cell.setStringValue(clob.getSubString(1, (int)clob.length()));
            return cell;
        }
        if (obj instanceof Timestamp) {
            Timestamp timestamp = (Timestamp)obj;
            cell.setType(CellTypeEnum.DATE.getCode());
            cell.setDateValue(EasyOptionalUtils.mapTo(timestamp, Timestamp::getTime));
            return cell;
        }

        String className = obj.getClass().getName();
        if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
            cell.setType(CellTypeEnum.DATE.getCode());
            cell.setDateValue(EasyOptionalUtils.mapTo(rs.getTimestamp(index), Timestamp::getTime));
            return cell;
        }
        if (className.startsWith("oracle.sql.DATE")) {
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            cell.setType(CellTypeEnum.DATE.getCode());
            if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
                cell.setDateValue(EasyOptionalUtils.mapTo(rs.getTimestamp(index), Timestamp::getTime));
            } else {
                cell.setDateValue(EasyOptionalUtils.mapTo(rs.getDate(index), Date::getTime));
            }
            return cell;
        }
        if (obj instanceof java.sql.Date) {
            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
                cell.setType(CellTypeEnum.DATE.getCode());
                cell.setDateValue(EasyOptionalUtils.mapTo(rs.getDate(index), Date::getTime));
                return cell;
            }
            Date date = (Date)obj;
            cell.setType(CellTypeEnum.DATE.getCode());
            cell.setDateValue(date.getTime());
            return cell;
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime)obj;
            cell.setType(CellTypeEnum.DATE.getCode());
            cell.setDateValue(localDateTime.toInstant(ZoneOffset.ofHours(+8)).toEpochMilli());
            return cell;
        }
        if (obj instanceof LocalDate) {
            LocalDate localDate = (LocalDate)obj;
            cell.setType(CellTypeEnum.DATE.getCode());
            cell.setDateValue(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
            return cell;
        }
        if (obj instanceof Number) {
            cell.setType(CellTypeEnum.BIG_DECIMAL.getCode());
            cell.setBigDecimalValue(new BigDecimal(obj.toString()));
            return cell;
        }
        cell.setType(CellTypeEnum.STRING.getCode());
        cell.setStringValue(obj.toString());
        return cell;
    }

    /**
     * 测试数据库连接
     *
     * @param url      数据库连接
     * @param userName 用户名
     * @param password 密码
     * @param dbType   数据库类型
     * @return
     */
    public static DataSourceConnect testConnect(String url, String host, String port,
        String userName, String password, DbTypeEnum dbType,
        String jdbc, SSHInfo ssh, Map<String, Object> properties) {
        DataSourceConnect dataSourceConnect = DataSourceConnect.builder()
            .success(Boolean.TRUE)
            .build();
        Session session = null;
        Connection connection = null;
        // 加载驱动
        try {
            if (ssh.isUse()) {
                session = SSHManager.getSSHSession(ssh);
                url = url.replace(host, "127.0.0.1").replace(port, ssh.getLocalPort());
            }
            // 创建连接
            connection = IDriverManager.getConnection(url, userName, password,
                DriverTypeEnum.getDriver(dbType, jdbc), properties);
        } catch (Exception e) {
            log.error("connection fail:", e);
            dataSourceConnect.setSuccess(Boolean.FALSE);
            // 获取最后一个异常的信息给前端
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            dataSourceConnect.setMessage(t.getMessage());
            return dataSourceConnect;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // ignore
                }
            }if(session!=null){
                try {
                    session.delPortForwardingL(Integer.parseInt(ssh.getLocalPort()));
                } catch (JSchException e) {
                }
                session.disconnect();
            }
        }
        dataSourceConnect.setDescription("成功");
        return dataSourceConnect;
    }
}
