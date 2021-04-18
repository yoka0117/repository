package com.huzi.service;

import com.huzi.domain.Warehouse.WarehouseRegionId;
import com.huzi.domain.userOrder.Order;

import java.util.List;

public interface OrderService {


    int reserve(Order order) throws BusinessException;


    //添加订单表
    int addOrder(WarehouseRegionId warehouseRegionIds);
}
