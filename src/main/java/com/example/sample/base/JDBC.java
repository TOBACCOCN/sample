package com.example.sample.base;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class JDBC {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&rewriteBatchedStatements=true";
            String user = "test";
            String password = "test";
            connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO t5 (`name`,text) values(?, ?)";
            statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (int i = 1000001; i <= 3000000; ++i) {
                statement.setString(1, "god-jiang666");
                statement.setString(2, "text" + i);
                statement.addBatch();
                if(i % 100000 == 0) {
                    statement.executeBatch();
                    log.info("insert i: " + i);
                    connection.commit();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
