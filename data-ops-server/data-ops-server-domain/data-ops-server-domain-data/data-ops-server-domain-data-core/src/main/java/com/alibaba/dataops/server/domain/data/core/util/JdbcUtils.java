package com.alibaba.dataops.server.domain.data.core.util;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.alibaba.dataops.server.domain.data.api.enums.CellTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.CellDTO;
import com.alibaba.dataops.server.tools.common.util.EasyOptionalUtils;

/**
 * jdbc工具类
 *
 * @author Jiaju Zhuang
 */
public class JdbcUtils {
    /**
     * 获取一个返回值
     *
     * @param rs
     * @param index
     * @return
     * @throws SQLException
     */
    public static CellDTO getResultSetValue(ResultSet rs, int index) throws SQLException {
        CellDTO cell = new CellDTO();
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
            cell.setType(CellTypeEnum.DATA.getCode());
            cell.setDateValue(EasyOptionalUtils.mapTo(timestamp, Timestamp::getTime));
            return cell;
        }

        String className = obj.getClass().getName();
        if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
            cell.setType(CellTypeEnum.DATA.getCode());
            cell.setDateValue(EasyOptionalUtils.mapTo(rs.getTimestamp(index), Timestamp::getTime));
            return cell;
        }
        if (className.startsWith("oracle.sql.DATE")) {
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            cell.setType(CellTypeEnum.DATA.getCode());
            if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
                cell.setDateValue(EasyOptionalUtils.mapTo(rs.getTimestamp(index), Timestamp::getTime));
            } else {
                cell.setDateValue(EasyOptionalUtils.mapTo(rs.getDate(index), Date::getTime));
            }
            return cell;
        }
        if (obj instanceof java.sql.Date) {
            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
                cell.setType(CellTypeEnum.DATA.getCode());
                cell.setDateValue(EasyOptionalUtils.mapTo(rs.getDate(index), Date::getTime));
                return cell;
            }
        }
        cell.setType(CellTypeEnum.STRING.getCode());
        cell.setStringValue(obj.toString());
        return cell;
    }
}
