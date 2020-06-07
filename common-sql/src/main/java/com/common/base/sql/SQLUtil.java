package com.common.base.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SQL解析工具
 *
 * @author hasee
 * @create 2020-06-06
 */

public class SQLUtil {

    public static final String DBTYPE_MYSQL = "mysql";
    public static final String DBTYPE_ORACLE = "oracle";

    /**
     * 校验sql语法
     *
     * @param sql    待校验sql
     * @param dbType 数据库类型
     * @return sql语句集合
     */
    public static List<SQLStatement> verifySQL(String sql, String dbType) {
        List<SQLStatement> sqlStatements;
        try {
            sqlStatements = SQLUtils.parseStatements(sql, dbType);
        } catch (Exception e) {
            throw new RuntimeException("SQL表达式语法错误。");
        }
        return sqlStatements;
    }

    /**
     * @param sql           待格式化sql
     * @param dbType        数据库类型
     * @param isLcaseFormat 格式是否小写化
     * @return 格式化sql
     */
    public static String formatSQL(String sql, String dbType, Boolean isLcaseFormat) {
        String formatSql;
        if (StringUtils.isEmpty(sql) || StringUtils.isEmpty(dbType)) {
            return null;
        }

        // 校验sql语法
        verifySQL(sql, dbType);

        if (isLcaseFormat) {
            formatSql = SQLUtils.format(sql, dbType, SQLUtils.DEFAULT_LCASE_FORMAT_OPTION);
        } else {
            formatSql = SQLUtils.format(sql, dbType);
        }

        return formatSql;
    }

    /**
     * 解析SQLStatement获取表名
     *
     * @param sqlStatement sql表达式
     * @param dbType       数据库类型
     * @return 表名集合
     */
    public static Set<String> parseSQLStatement(SQLStatement sqlStatement, String dbType) {
        if (sqlStatement == null || StringUtils.isEmpty(dbType)) {
            return null;
        }

        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(dbType);
        sqlStatement.accept(visitor);
        return visitor.getTables().keySet().stream().map(TableStat.Name::getName).collect(Collectors.toSet());
    }

    /**
     * 解析SQL获取所有表名
     *
     * @param sql    sql语句
     * @param dbType 数据库类型
     * @return 表名集合
     */
    public static Set<String> parseSQL(String sql, String dbType) {
        Set<String> tableNames = new HashSet<>();
        //校验SQL语法，获取SQLStatement集
        List<SQLStatement> sqlStatements = verifySQL(sql, dbType);
        sqlStatements.stream().map(sqlStatement -> parseSQLStatement(sqlStatement, dbType)).forEach(
                tableNames::addAll);
        return tableNames;
    }
}
