package com.huzi.service;

import com.huzi.dao.OrderDao;
import com.huzi.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Override
    public int insertOrder(Order order) {

        return orderDao.insertOrder(order);

    }
}
