package com.atguigu.preparedstatement.crud;

import com.atguigu.preparedstatement.entity.Customers;
import com.atguigu.preparedstatement.entity.Order;
import com.atguigu.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class QueryTest {

    /**
     * 针对表的字段名和属性名不相同的情况:
     * 1.必须声明SQL时, 使用类的属性名来命名字段的别名
     * 2.使用ResultSetMetaData时, 需要使用getColumnLabel()来替换 getColumnName()获取列的别名
     *
     * 补充说明: 如果SQL中没有给字段取别名, getColumnLabel()获取的就是列名
     *
     *
     */


    public <T> List<T>  commonFindList(Class<T> clazz, String sql, Object... args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //返回结果的列数
            int columnCount = rsmd.getColumnCount();
            List<T> list = new ArrayList<>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object val = rs.getObject(i + 1);
                    //获取列名
//                    String key = rsmd.getColumnName(i + 1);
                    //获取别名
                    String label = rsmd.getColumnLabel(i + 1);
                    //给cust对象指定的列名 通过反射 赋值
                    Field field = clazz.getDeclaredField(label);
                    field.setAccessible(true);
                    field.set(t,val);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps,rs);
        }
        return null;
    }


    public <T> T commonFindOne(Class<T> clazz,String sql,Object... args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtils.getConnection2();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //返回结果的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object val = rs.getObject(i + 1);
                    //获取列名
//                    String key = rsmd.getColumnName(i + 1);
                    //获取别名
                    String label = rsmd.getColumnLabel(i + 1);

                    //给cust对象指定的列名 通过反射 赋值
                    Field field = clazz.getDeclaredField(label);
                    field.setAccessible(true);
                    field.set(t,val);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps,rs);
        }
        return null;
    }


    public Customers findOne(String sql,Object... args){

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //返回结果的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                Customers cust = new Customers();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object val = rs.getObject(i + 1);
                    //获取列名
//                    String key = rsmd.getColumnName(i + 1);
                    //获取别名
                    String label = rsmd.getColumnLabel(i + 1);

                    //给cust对象指定的列名 通过反射 赋值
                    Field field = Customers.class.getDeclaredField(label);
                    field.setAccessible(true);
                    field.set(cust,val);
                }
                return cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps,rs);
        }
        return null;
    }


    @Test
    public void testQuery() throws Exception {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "SELECT id,name,email,birth FROM customers where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1,1);
            rs = ps.executeQuery();
            if (rs.next()){
                Customers cust = new Customers(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4));
                System.out.println(cust);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps,rs);
        }
    }


    @Test
    public void test2(){
        String sql = "SELECT id,name,email,birth FROM customers where id = ?";
        Customers customers = findOne(sql, 1);
        System.out.println(customers);
    }


    @Test
    public void test3(){
        String sql = "SELECT id,name,email,birth FROM customers where id = ?";
        Customers customers = commonFindOne(Customers.class,sql, 1);
        System.out.println(customers);

        String sql1 = "SELECT order_id orderId,order_name orderName,order_date orderDate FROM `order` where order_id = ?";
        Order order = commonFindOne(Order.class,sql1, 1);
        System.out.println(order);
    }


    @Test
    public void test4(){
        String sql = "SELECT id,name,email,birth FROM customers where id < 12";
        List<Customers> list = commonFindList(Customers.class, sql);
        list.forEach(System.out::println);


        String sql1 = "SELECT order_id orderId,order_name orderName,order_date orderDate FROM `order`";
        List<Order> orders = commonFindList(Order.class, sql1);
        orders.forEach(System.out::println);
    }


}
