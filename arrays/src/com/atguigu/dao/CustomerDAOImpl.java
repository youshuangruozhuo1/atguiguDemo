package com.atguigu.dao;

import com.atguigu.preparedstatement.entity.Customers;

import java.sql.Connection;
import java.util.List;

public class CustomerDAOImpl extends BaseDAO<Customers> implements  CustomerDAO{
    @Override
    public void insert(Connection conn, Customers customers) {

    }

    @Override
    public void deleteById(Connection conn, int id) {

    }

    @Override
    public void updateById(Connection conn, int id, Customers cust) {

    }

    @Override
    public Customers getOneById(Connection conn, int id) {
        return null;
    }

    @Override
    public List<Customers> getAll(Connection conn) {
        return null;
    }

    @Override
    public Long getCount(Connection conn) {
        return null;
    }
}
