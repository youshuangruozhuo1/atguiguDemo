package com.atguigu.dao;

import com.atguigu.preparedstatement.entity.Customers;

import java.sql.Connection;
import java.util.List;

/**
 * 此接口用于规范customer表的常用操作
 */
public interface CustomerDAO {

    /**
     * 将customer添加到数据库
     * @param conn
     * @param customers
     */
    void insert(Connection conn, Customers customers);


    void deleteById(Connection conn,int id);


    void updateById(Connection conn,int id , Customers cust);


    Customers getOneById(Connection conn,int id);


    List<Customers> getAll(Connection conn);


    Long getCount(Connection conn);


}
