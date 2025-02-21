package org.wjj.qrcpcheck.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.wjj.qrcpcheck.common.CommonConsts;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MySQLDBTest {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "y32163214";
        String tableName = "account";
        String columnKey = "ID";
        String columnValue = "1";
        String dbType = "MYSQL";

        MySQLDBTest mySQLDBTest = new MySQLDBTest();
        mySQLDBTest.mySQLTabelColumnsGet(url, user, password, tableName, dbType);
        mySQLDBTest.mySQLTableDataToJson(url,user,password,tableName, columnKey, columnValue);
    }

    public List<String> mySQLTabelsGet(String url, String user, String password, String dbType){
        List<String> tablesName = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String tableQuerySQL = null;
            switch (dbType){
                case "MYSQL":
                    tableQuerySQL = CommonConsts.MYSQL_TABLE_QUERY_SQL;
                    break;
                case "ORALCE":
                    tableQuerySQL = CommonConsts.ORACLE_TABLE_QUERY_SQL;
                    break;
            }
            ResultSet resultSet = statement.executeQuery(tableQuerySQL);

            while (resultSet.next()) {
                tablesName.add(resultSet.getString("TABLE_NAME"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(tablesName);
        return tablesName;
    }

    public List<String> mySQLTabelColumnsGet(String url, String user, String password, String tableName, String dbType){
        List<String> tableColumn = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String queryColumnSql = null;
            switch (dbType){
                case "MYSQL":
                    queryColumnSql = CommonConsts.MYSQL_TABLE_COLUMNS_QUERY_SQL_PRE;
                    break;
                case "ORALCE":
                    queryColumnSql = CommonConsts.ORACLE_TABLE_COLUMNS_QUERY_SQL_PRE;
                    break;
            }
            PreparedStatement pstmt = connection.prepareStatement(queryColumnSql);
            pstmt.setString(1, tableName);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                tableColumn.add(resultSet.getString("COLUMN_NAME"));
            }

            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(tableColumn);
        return tableColumn;
    }

    public String mySQLTableDataToJson(String url, String user, String password, String tableName,
                                       String columnKey, String columnValue) {
        String jsonResult = null;
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String queryColumnSql = String.format(CommonConsts.TABLE_DATA_JSON_QUERY_SQL_PRE, tableName, columnKey);
            PreparedStatement pstmt = connection.prepareStatement(queryColumnSql);
            pstmt.setString(1, columnValue);

            ResultSet resultSet = pstmt.executeQuery();

            // 创建一个Map来存储结果
            Map<String, Object> resultMap = new HashMap<>();

            if (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Object columnContent = resultSet.getObject(i);
                    resultMap.put(columnName, columnContent);
                }
            }
            // 使用Jackson将Map转换为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            jsonResult = objectMapper.writeValueAsString(resultMap);
            System.out.println(jsonResult);

            // 关闭连接
            resultSet.close();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}