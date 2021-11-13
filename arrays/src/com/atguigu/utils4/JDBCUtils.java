package com.atguigu.utils4;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

public class JDBCUtils {

    public static Connection getConnection() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        return  cpds.getConnection();
    }
}
