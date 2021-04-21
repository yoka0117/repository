package com.huzi.service;

import com.huzi.domain.Warehouse.WarehouseRegionId;
import com.huzi.domain.userOrder.Order;
import com.huzi.domain.userOrder.OrderDelivery;

import java.util.List;

public interface OrderService {


    int reserve(Integer orderId) throws BusinessException;


    //添加订单表
    int addOrder(WarehouseRegionId warehouseRegionIds);



    //订单出库确认
    int orderDelivery(Integer orderId , List<OrderDelivery> orderDelivery);
}
