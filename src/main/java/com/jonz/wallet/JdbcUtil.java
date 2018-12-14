package com.jonz.wallet;

import java.sql.*;

class JdbcUtil {

    //声明全局常量 驱动类名称，数据库连接字符串，用户名，密码
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/wallet";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123456";

    //返回数据库连接对象Connection方法getConnection
    static Connection getConnection() {
        Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    //关闭ResultSet对象
    static void closeRS(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //关闭Statement对象
    static void closeST(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //关闭数据库连接对象Connection
    static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //关闭全部
    static void closeAll(Connection conn, ResultSet rs, Statement st) {
        closeRS(rs);
        closeST(st);
        closeConnection(conn);
    }
}
