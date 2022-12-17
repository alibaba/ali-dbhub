package com.alibaba.dbhub.server.domain.support.model.support;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * 数据库源
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DbhubDataSource implements DataSource {

    /**
     * 数据库类型
     */
    private DbTypeEnum dbType;

    /**
     * 请求连接
     */
    @NotNull
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * database
     */
    private String database;

    /**
     * 端口
     */
    private Integer port;

    /**
     * url不包含database
     */
    private String urlWithOutDatabase;
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url.trim(), username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter method for property <tt>dbType</tt>.
     *
     * @return property value of dbType
     */
    public DbTypeEnum getDbType() {
        return dbType;
    }

    /**
     * Setter method for property <tt>dbType</tt>.
     *
     * @param dbType value to be assigned to property dbType
     */
    public void setDbType(DbTypeEnum dbType) {
        this.dbType = dbType;
    }

    /**
     * Getter method for property <tt>url</tt>.
     *
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     *
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
        if (!ObjectUtils.isEmpty(url)) {
            //jdbc:postgresql://localhost:5432/postgres
            String[] array = getUrl().split(":");
            if (array.length == 4) {
                String str = array[3];
                boolean isDigit = true;
                StringBuffer sb = new StringBuffer();
                StringBuffer sb1 = new StringBuffer();
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (isDigit == true) {
                        if (!Character.isDigit(c)) {
                            isDigit = false;
                        }else {
                            sb1.append(c);
                        }
                    } else {
                        sb.append(c);
                    }
                }
                String s = sb.toString();
                if (!ObjectUtils.isEmpty(s)) {
                    this.database = s;
                }
                this.port = Integer.parseInt(sb1.toString());
                this.urlWithOutDatabase = array[0]+":"+array[1]+":"+array[2]+":"+port;
            }
        }
    }

    /**
     * Getter method for property <tt>username</tt>.
     *
     * @return property value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for property <tt>username</tt>.
     *
     * @param username value to be assigned to property username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for property <tt>password</tt>.
     *
     * @return property value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for property <tt>password</tt>.
     *
     * @param password value to be assigned to property password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for property <tt>database</tt>.
     *
     * @return property value of database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Setter method for property <tt>database</tt>.
     *
     * @param database value to be assigned to property database
     */
    public void setDatabase(String database) {
        this.database = database;
        if(!ObjectUtils.isEmpty(this.urlWithOutDatabase) && !ObjectUtils.isEmpty(this.database) ) {
            this.url = this.urlWithOutDatabase + "/" + database;
        }
    }
}
