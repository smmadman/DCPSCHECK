package org.wjj.qrcpcheck.common;

public class CommonConsts {

    public static final String ORACLE_TABLE_QUERY_SQL = "SELECT table_name " +
            "FROM user_tab_columns";

    public static final String ORALCE_TABLE_COLUMNS_QUERY_SQL_PRE = "SELECT COLUMN_NAME " +
            "FROM ALL_TAB_COLUMNS " +
            "WHERE TABLE_NAME = ?";

    public static final String MYSQL_TABLE_QUERY_SQL = "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema = DATABASE()";

    public static final String MYSQL_TABLE_COLUMNS_QUERY_SQL_PRE = "SELECT COLUMN_NAME FROM " +
            "information_schema.COLUMNS " +
            "WHERE TABLE_NAME = ?";

    public static final String TABLE_DATA_JSON_QUERY_SQL_PRE = "SELECT * FROM %s WHERE %s = ?";
}
