package com.atguigu.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Test {

    //方式1
    @Test
    public void testGetConnection() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&tcpRcvBuf=2024000&rewriteBatchedStatements=true" );
        cpds.setUser("root");
        cpds.setPassword("root");
        //初始时数据库连接池的连接数
        cpds.setInitialPoolSize(10);
        System.out.println(cpds);
    }


    @Test
    public void test2() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }
}
