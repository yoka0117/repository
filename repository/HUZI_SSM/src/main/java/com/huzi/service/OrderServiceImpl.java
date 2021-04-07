package com.huzi.service;

import com.huzi.common.OrderStatus;
import com.huzi.dao.OrderDao;
import com.huzi.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Override
    public int insertOrder(Order order) {
//todo 1.验证sku 仓库是存在 2.设置好creatTime 状态
        order.setOrder_create_time(new Date());
        order.setOrder_state(OrderStatus.INIT.name());
        return orderDao.insertOrder(order);

    }
}
