package com.alibaba.druid.sql.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.druid.DbType;

/**
 * 临时的sql 解析工具类
 * 已经让druid改了 但是没上线
 *
 * @author 是仪
 */
public class DbhubSQLParserUtils extends SQLParserUtils {

    /**
     * 拆分&移除注释
     *
     * @param sql
     * @param dbType
     * @return
     */
    public static List<String> splitAndRemoveComment(String sql, DbType dbType) {
        if (dbType == null) {
            dbType = DbType.other;
        }

        boolean containsCommentAndSemi = false;
        {
            Lexer lexer = createLexer(sql, dbType);
            lexer.config(SQLParserFeature.SkipComments, false);
            lexer.config(SQLParserFeature.KeepComments, true);

            while (lexer.token != Token.EOF) {
                if (lexer.token == Token.LINE_COMMENT
                    || lexer.token == Token.MULTI_LINE_COMMENT
                    || lexer.token == Token.SEMI) {
                    containsCommentAndSemi = true;
                    break;
                }
                lexer.nextToken();
            }

            if (!containsCommentAndSemi) {
                return Collections.singletonList(sql);
            }
        }

        {
            Lexer lexer = createLexer(sql, dbType);
            lexer.nextToken();

            boolean script = false;
            if (dbType == DbType.odps && lexer.token == Token.VARIANT) {
                script = true;
            }

            if (script || lexer.identifierEquals("pai") || lexer.identifierEquals("jar")) {
                return Collections.singletonList(sql);
            }
        }

        List list = new ArrayList();

        Lexer lexer = createLexer(sql, dbType);
        lexer.config(SQLParserFeature.SkipComments, false);
        lexer.config(SQLParserFeature.KeepComments, true);

        boolean set = false, paiOrJar = false;
        int start = 0;
        Token token = lexer.token;
        for (; lexer.token != Token.EOF; ) {
            if (token == Token.SEMI) {
                int len = lexer.startPos - start;
                if (len > 0) {
                    String lineSql = sql.substring(start, lexer.startPos);
                    String splitSql = set
                        ? removeLeftComment(lineSql, dbType)
                        : removeComment(lineSql, dbType
                        ).trim();
                    if (!splitSql.isEmpty()) {
                        list.add(splitSql);
                    }
                }
                start = lexer.startPos + 1;
                set = false;
            } else if (token == Token.MULTI_LINE_COMMENT) {
                int len = lexer.startPos - start;
                if (len > 0) {
                    String splitSql = removeComment(
                        sql.substring(start, lexer.startPos),
                        dbType
                    ).trim();
                    if (!splitSql.isEmpty()) {
                        list.add(splitSql);
                    }
                }
                lexer.nextToken();
                token = lexer.token;
                start = lexer.startPos;
                continue;
            } else if (token == Token.CREATE) {
                lexer.nextToken();

                if (lexer.token == Token.FUNCTION || lexer.identifierEquals("FUNCTION")) {
                    lexer.nextToken();
                    lexer.nextToken();
                    if (lexer.token == Token.AS) {
                        lexer.nextToken();
                        if (lexer.token == Token.LITERAL_CHARS) {
                            lexer.nextToken();
                            token = lexer.token;
                            continue;
                        }
                    }
                    lexer.startPos = sql.length();
                    break;
                }

                token = lexer.token;
                continue;
            } else if (set && token == Token.EQ && dbType == DbType.odps) {
                lexer.nextTokenForSet();
                token = lexer.token;
                continue;
            } else if (dbType == DbType.odps
                && lexer.ch != '.'
                && (lexer.identifierEquals("pai") || lexer.identifierEquals("jar"))) {
                paiOrJar = true;
            }

            if (lexer.identifierEquals("USING")) {
                lexer.nextToken();
                if (lexer.identifierEquals("jar")) {
                    lexer.nextToken();
                }
            }

            if (lexer.token == Token.SET) {
                set = true;
            }

            if (lexer.identifierEquals("ADD") && (dbType == DbType.hive || dbType == DbType.odps)) {
                lexer.nextToken();
                if (lexer.identifierEquals("JAR")) {
                    lexer.nextPath();
                }
            } else {
                lexer.nextToken();
            }
            token = lexer.token;
        }

        if (start != sql.length() && token != Token.SEMI) {
            int end = lexer.startPos;
            if (end > sql.length()) {
                end = sql.length();
            }
            String splitSql = sql.substring(start, end).trim();
            if (!paiOrJar) {
                splitSql = removeComment(splitSql, dbType).trim();
            } else {
                if (splitSql.endsWith(";")) {
                    splitSql = splitSql.substring(0, splitSql.length() - 1).trim();
                }
            }
            if (!splitSql.isEmpty()) {
                list.add(splitSql);
            }
        }

        return list;
    }


    public static String format(DbTypeEnum dbType, String tableName) {
        if (DbTypeEnum.MYSQL.equals(dbType)) {
            return "`" + tableName + "`";
        } else if (DbTypeEnum.ORACLE.equals(dbType)) {
            return "\"" + tableName + "\"";
        } else if (DbTypeEnum.POSTGRESQL.equals(dbType)) {
            return "\"" + tableName + "\"";
        } else if (DbTypeEnum.SQLITE.equals(dbType)) {
            return "\"" + tableName + "\"";
        } else if (DbTypeEnum.SQLSERVER.equals(dbType)) {
            return "[" + tableName + "]";
        } else if (DbTypeEnum.H2.equals(dbType)) {
            return "\"" + tableName + "\"";
        } else {
            return "\"" + tableName + "\"";
        }
    }

    public static String format(String dbType, String tableName) {
        if (DbTypeEnum.MYSQL.getCode().equalsIgnoreCase(dbType)) {
            return "`" + tableName + "`";
        } else if (DbTypeEnum.ORACLE.getCode().equalsIgnoreCase(dbType)) {
            return "\"" + tableName + "\"";
        } else if (DbTypeEnum.POSTGRESQL.getCode().equalsIgnoreCase(dbType)) {
            return "\"" + tableName + "\"";
        } else if (DbTypeEnum.SQLITE.getCode().equalsIgnoreCase(dbType)) {
            return "\"" + tableName + "\"";
        } else if (DbTypeEnum.SQLSERVER.getCode().equalsIgnoreCase(dbType)) {
            return "[" + tableName + "]";
        } else if (DbTypeEnum.H2.getCode().equalsIgnoreCase(dbType)) {
            return "\"" + tableName + "\"";
        } else {
            return "\"" + tableName + "\"";
        }
    }
}
