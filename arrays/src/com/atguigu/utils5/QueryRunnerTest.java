package com.atguigu.utils5;

import com.atguigu.preparedstatement.entity.Customers;
import com.atguigu.util.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class QueryRunnerTest {

    @Test
    public void testInsert()  {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            conn = JDBCUtils.getConnection2();
            int insert = runner.update(conn, sql, "徐坤蔡", "caixukun@126.com", "1996-01-01");
            System.out.println("添加了"+insert+"条记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null,null);
        }
    }


    @Test
    public void query1(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection2();
            String sql = " select id,name,email,birth from customers where id = ?";
            BeanHandler<Customers> handler = new BeanHandler<Customers>(Customers.class);
            Customers customers = runner.query(conn, sql, handler, 22);
            System.out.println(customers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            com.atguigu.util.JDBCUtils.closeResource(conn,null,null);
        }
    }

    @Test
    public void query2(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection2();
            String sql = " select id,name,email,birth from customers where id < ?";
            BeanListHandler<Customers> handler = new BeanListHandler<Customers>(Customers.class);
            List<Customers> query = runner.query(conn, sql, handler, 22);
            query.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            com.atguigu.util.JDBCUtils.closeResource(conn,null,null);
        }
    }

    @Test
    public void query3(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection2();
            String sql = " select count(1) from customers where id < ?";
            ScalarHandler handler = new ScalarHandler();
            Long count = (Long) runner.query(conn, sql, handler, 22);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            com.atguigu.util.JDBCUtils.closeResource(conn,null,null);
        }
    }

    @Test
    public void query4(){
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection2();
            String sql = " select MAX(birth) from customers";
            ScalarHandler handler = new ScalarHandler();
            Date date = (Date) runner.query(conn, sql, handler);
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            com.atguigu.util.JDBCUtils.closeResource(conn,null,null);
        }
    }

    @Test
    public void query5(){
        Connection conn = null;
        QueryRunner runner = null;
        try {
            runner = new QueryRunner();
            conn = JDBCUtils.getConnection2();
            String sql = " select id,name,email,birth from customers where id = ?";


            ResultSetHandler<Customers> handler = new ResultSetHandler<Customers>() {
                @Override
                public Customers handle(ResultSet rs) throws SQLException {

                    if (rs.next()){
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        Date birth = rs.getDate("birth");
                        return new Customers(id,name,email,birth);
                    }

                    return null;
                }
            };


            Customers query = runner.query(conn, sql, handler, 22);
            System.out.println(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn,null,null);
        }
    }
}
