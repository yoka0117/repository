package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.userOrder.OrderDao;
import com.huzi.domain.userOrder.Order;
import com.huzi.service.BusinessException;
import com.huzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderReserveSerivceImpl implements OrderReserveSerivce{
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDao orderDao;

    public int reserveOne(Order order)  {
        int result =0;
        try {

             result = orderService.reserve(order);

        } catch (BusinessException e) {
            if("SHORTEGY".equals(e.code)){
                //订单修改为缺货
                order.setOrderState(PurchaseOrderStatus.LACK.name());
                orderDao.updateOrder(order);
            }
        }
        return result;
    }
}
