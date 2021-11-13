package com.atguigu.lxl;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcTest {


    /**
     * 方式1
     * @throws SQLException
     */
    @Test
    public void test1() throws SQLException {
        //获取 driver 实现类对象
        Driver driver = new com.mysql.jdbc.Driver();
        // jdbc:主协议,mysql : 子协议,localhost : 主机名 , 3306:mysql默认端口号 , test : 连接的数据库
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }


    /**
     * 方式2 : 对方式1的迭代 , 在如下的操作中不出现第三方的api,使得程序有更好的移植性
     * @throws SQLException
     */
    @Test
    public void test2() throws Exception {
        //1.获取 driver 具体实现类  , 使用反射
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        //2.提供要链接的数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //3.数据库用户名密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        //4.获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    /**
     * 方式3 : 使用DriverManage
     */
    @Test
    public void test3() throws Exception {

        //1.获取 driver 具体实现类  , 使用反射
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        //2.注册驱动
        DriverManager.registerDriver(driver);
        //3.获取连接
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }


    /**
     * 方式4 : 使用DriverManage
     */
    @Test
    public void test4() throws Exception {

        //1.连接基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "root";
        //1.获取 driver 具体实现类  , 使用反射
        Class.forName("com.mysql.jdbc.Driver");
        /**相较于方式3 可以省略如下操作 注册驱动
         * 因为有如下代码 :
         * static {
         *         try {
         *             DriverManager.registerDriver(new Driver());
         *         } catch (SQLException var1) {
         *             throw new RuntimeException("Can't register driver!");
         *         }
         *     }
         */
//        Driver driver = (Driver) aClass.newInstance();
//        //2.注册驱动
//        DriverManager.registerDriver(driver);
        //3.获取连接
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }


    /**
     *
     * 方式5 : 基础信息从配置文件获取
     * 最终版 , 将数据库连接的4个基本信息声明在配置里,通过读取配置文件的方式,获取链接
     * 好处 :
     * 1. 实现了数据与代码分离 , 解耦合
     * 2.如果需要修改配置文件信息,可以避免程序重新打包
     */
    @Test
    public void test5() throws Exception {

        //1.读取配置文件基础信息
        InputStream is = JdbcTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(is);

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        //2.加载驱动
        Class.forName(driver);
        //3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);

        conn.close();
        is.close();


    }


}
