package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.userOrder.OrderDao;
import com.huzi.dao.userOrder.SaleDao;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.userOrder.Order;
import com.huzi.domain.userOrder.Sale;
import com.huzi.service.BusinessException;
import com.huzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderReserveSerivceImpl implements OrderReserveSerivce{
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private InventoryDao inventoryDao;

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


    //取消预订
    @Override
    public int cancelReserve(Order order) {

        Order od = orderDao.selectOrder(order);
        //判断order对象是否为空
        if ( od == null){return 1;}
        Integer warehouseId = od.getWarehouseId();


        //判断order对象，状态是否为success 或者  init
        if (PurchaseOrderStatus.INIT.name().equals(od.getOrderState())){
            return 2;
        }else if(PurchaseOrderStatus.SUCCESS.name().equals(od.getOrderState())){
            od.setOrderState(PurchaseOrderStatus.INIT.name());
            orderDao.updateOrder(od);

            //通过orderId，查找对应的产品数量amount
            Sale sale = new Sale();
            sale.setOrderId(od.getOrderId());
            List<Sale> saleList = saleDao.selectSale(sale);

            for (Sale sales : saleList){
                Integer skuId =  sales.getSkuId();
                Integer amount = sales.getAmount();
                InventoryParam inventoryParam = new InventoryParam();
                inventoryParam.setSkuId(skuId);
                inventoryParam.setWarehouseId(warehouseId);
                inventoryParam.setRealInventoryAdd(amount);
                inventoryDao.updateInventoryAdd(inventoryParam);
            }


        }       return 3;

    }
}
