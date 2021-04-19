package com.huzi.service.impl;

import com.huzi.domain.userOrder.Order;

import java.util.List;

public interface OrderReserveSerivce {

    public int reserveOne(Order order) ;


    public int cancelReserve(Order order);
}
