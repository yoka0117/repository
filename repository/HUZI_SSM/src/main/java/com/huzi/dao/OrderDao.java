package com.huzi.dao;


import com.huzi.domain.Order;

public interface OrderDao {

    //创建（新增）采购订单
    int insertOrder(Order order);
}
