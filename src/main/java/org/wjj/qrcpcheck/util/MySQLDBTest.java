package org.wjj.qrcpcheck.util;
import org.springframework.stereotype.Component;
import org.wjj.qrcpcheck.common.CommonConsts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySQLDBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "y32163214";
    }

    public List<String> mySQLTabelsGet(String url, String user, String password){
        List<String> tablesName = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(CommonConsts.MYSQL_TABLE_QUERY_SQL);

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
}