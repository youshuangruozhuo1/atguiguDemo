package com.atguigu.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库工具类
 */
public class JDBCUtils {

    /**
     * 获取链接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        //2.加载驱动
        Class.forName(driver);
        //3.获取连接
        return DriverManager.getConnection(url, user, password);
    }

    private static DataSource dataSource = null;

    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection2() throws Exception {
        return dataSource.getConnection();
    }

    public static void closeResource(Connection conn, Statement statement) {
        //7.关闭资源
        try {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }


    public static void closeResource(Connection conn, Statement statement, ResultSet rs) {
        closeResource(conn, statement);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }


}
