package org.wjj.qrcpcheck.common;

public class CommonConsts {
    public static final String MYSQL_TABLE_QUERY_SQL = "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema = DATABASE()";
}
