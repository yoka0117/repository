package com.huzi.dao.userOrder;

import com.huzi.domain.Warehouse.Region;
import com.huzi.domain.userOrder.Order;

public interface OrderDao {

    //添加订单
    int addOrder(Order order);


    //选仓
    Region selectRegionById(Integer regionId);
    //根据地区Id 选择可用仓库，把仓库Id填充到订单表
    //int addOrder(Order order);

    //查order对象
    Order selectOrder(Order order);


    //更改order属性
    int updateOrder(Order order);
}
