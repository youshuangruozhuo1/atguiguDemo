package com.atguigu.dao;

import com.atguigu.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {


    private  Class<T> clazz = null;

     {
        //获取当前BaseDAO子类继承对象
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];
    }

    /**
     * 通用增删改 2.0 (考虑上事务)
     *
     * @param sql
     * @param args
     */
    public static int update(Connection conn, String sql, Object... args) {
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


    public  List<T> commonFindList(Connection conn, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //返回结果的列数
            int columnCount = rsmd.getColumnCount();
            List<T> list = new ArrayList<>();
            while (rs.next()) {
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
                    field.set(t, val);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    public <E> E getValue(Connection conn,String sql, Object... args) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return (E) rs.getObject(1);
        }

        JDBCUtils.closeResource(null,ps,rs);


        return null;

    }


}
