package com.common.base;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.common.base.sql.SQLUtil;
import com.sun.org.apache.xpath.internal.operations.And;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 *
 * @author hasee
 * @create 2020-06-06
 */

public class Main {
    public static void main(String[] args) {
        //String sql ="CREATE TABLE groups(gid INT PRIMARY KEY AUTO_INCREMENT COMMENT '设置主键自增' ,gname VARCHAR(200) COMMENT '列注释')COMMENT='表注释'";
        String sql = "select * from user_table where id='1'update user_table set name='tome' where id = '1'sELECT * FROM JJJ WHERE LI=1 And moile='13261' And id In(selecT jj_id from jk where l=1 group by j having count(jj_id)>1) LIMIT 10,33 order by moile";
        String formatsql= SQLUtil.formatSQL(sql,SQLUtil.DBTYPE_MYSQL,false);
//        List<SQLStatement> sqlStatements = SQLUtil.verifySQL(sql, SQLUtil.DBTYPE_MYSQL);
        Set<String> tables = SQLUtil.parseSQL(sql, SQLUtil.DBTYPE_MYSQL);
        System.out.println(formatsql);
        System.out.println(tables);
    }
}
