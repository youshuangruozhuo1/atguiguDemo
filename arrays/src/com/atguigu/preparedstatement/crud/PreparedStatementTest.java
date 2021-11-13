package com.atguigu.preparedstatement.crud;

import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PreparedStatementTest {


    /**
     * 通用增删改
     *
     * @param sql
     * @param args
     */
    public static int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取链接
            conn = JDBCUtils.getConnection();
            //2. 预编译Sql语句, 返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            //4.执行操作
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.closeResource(conn, ps);
        }

        return 0;
    }


    /**
     * 通用增删改 2.0 (考虑上事务)
     *
     * @param sql
     * @param args
     */
    public static int update(Connection conn , String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            //1.. 预编译Sql语句, 返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //2.填充占位符
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            //3.执行操作
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }

    //向customers表中添加一条记录
    @Test
    public void insert() throws Exception {

        Connection conn = JDBCUtils.getConnection();

        //4. 预编译Sql语句, 返回PreparedStatement的实例
        String sql = " insert into customers (name,email,birth) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        //5.填充占位符
        ps.setString(1, "那托111");
        ps.setString(2, "natuo@qq.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1000-01-01");
        ps.setDate(3, new java.sql.Date(date.getTime()));

        //6.执行操作
        ps.execute();

        JDBCUtils.closeResource(conn, ps);

    }


    @Test
    public void testUpdate() throws Exception {
        //1.获取连接
        Connection conn = JDBCUtils.getConnection();
        //2.预编译SQL语句
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        //3.填充占位符
        ps.setObject(1, "朗朗");
        ps.setObject(2, 18);
        //4.执行
        ps.execute();
        //5.关闭资源
        JDBCUtils.closeResource(conn, ps);
    }

    @Test
    public void testUpdate2() throws Exception {
        String sql = "update customers set name = ? where id = ?";
        int result = update(sql, "王家卫", 19);
        if (result > 0) {
            System.out.println("修改成功!!");
        } else {
            System.out.println("修改失败!!");
        }
    }


    @Test
    public void testUpdate3() throws Exception {

        Connection conn = JDBCUtils.getConnection();

        String sql = " insert into customers (name,email,birth,photo) values(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, "亡命之徒");
        ps.setObject(2, "mmzt@126.com");
        ps.setObject(3, "1992-2-1");

        FileInputStream is = new FileInputStream("wallhaven-5wo7j1.png");

        ps.setBlob(4, is);
        int i = ps.executeUpdate();

        if (i > 0) {
            System.out.println("新增成功!!");
        } else {
            System.out.println("新增失败!!");
        }


        JDBCUtils.closeResource(conn, ps);
        is.close();

    }

    @Test
    public void test5() {
        int k = 0;
        for (int i = 0, j = 0; i < 15 && j < 9; i++, j++) {
            k = i + j;
        }
        System.out.println(k);
    }


    /**
     * 方式二 , 用PreparedStatement
     */
    @Test
    public void testInsertBatch() {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long s = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 20000; i++) {
                ps.setString(1, "name_" + i);
                ps.executeUpdate();
            }
            long e = System.currentTimeMillis();
            System.out.println("耗时:" + (e - s));       //10000条数据 30秒
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }


    /**
     * 方式三 , 用PreparedStatement 仅执行一次
     */
    @Test
    public void testInsertBatch2() {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long s = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            StringBuilder sb = new StringBuilder("insert into goods (name) values");
            //1000000  Packet for query is too large
            for (int i = 0; i < 1000000; i++) {
                if (i == 1000000 - 1) {
                    sb.append("('name_" + i + "')");
                } else {
                    sb.append("('name_" + i + "'),");
                }
            }
            ps = conn.prepareStatement(sb.toString());
            ps.executeUpdate();
            long e = System.currentTimeMillis();
            System.out.println("耗时:" + (e - s));   //20000条  588
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    /**
     * 方式四 , 用PreparedStatement
     * 1.addBatch(),clearBatch(),executeBatch()
     * 2.mysql服务器默认是关闭批处理的,我们需要通过一个参数,让MySQL开启批处理支持
     * rewriteBatchedStatements=true
     * 3.使用更新的MySQL驱动 mysql-connector-java-5.1.37-bin.jar
     */
    @Test
    public void testInsertBatch3() {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long s = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 1000000; i++) {
                ps.setString(1, "name_" + i);
                //1.攒SQL
                ps.addBatch();

                if (i % 1000 == 0) {
                    //2.执行SQL
                    ps.executeBatch();
                    //3.清空SQL
                    ps.clearBatch();
                }
            }
            long e = System.currentTimeMillis();
            System.out.println("耗时:" + (e - s));       //20000条数据 651
            //1000000条  10685
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }


    /**
     * 方式五 : 设置不自动提交
     */
    @Test
    public void testInsertBatch5() {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long s = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into goods (name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 1000000; i++) {
                ps.setString(1, "name_" + i);
                //1.攒SQL
                ps.addBatch();
            }
            //2.执行SQL
            ps.executeBatch();
            //3.清空SQL
            ps.clearBatch();
            conn.commit();
            long e = System.currentTimeMillis();
            System.out.println("耗时:" + (e - s));       //20000条数据 651
                                                        //1000000条  11399
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入名字:");
        String name = scanner.next();
        System.out.println("请输入邮箱:");
        String email = scanner.next();
        System.out.println("请输入生日");
        String birthday = scanner.next();
        String sql = "insert into customers(name,email,birth) values (?,?,?)";
        int result = update(sql, name, email, birthday);
        if (result > 0) {
            System.out.println("添加成功!!");
        } else {
            System.out.println("添加失败!!");
        }
    }


}
