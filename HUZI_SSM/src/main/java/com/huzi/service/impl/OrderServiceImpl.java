package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.userOrder.OrderDao;
import com.huzi.dao.userOrder.SaleDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.Warehouse.WarehouseRegionId;
import com.huzi.domain.userOrder.Order;
import com.huzi.domain.userOrder.Sale;
import com.huzi.service.BusinessException;
import com.huzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private InventoryDao inventoryDao;



    @Override
    @Transactional
    public int reserve(Order order) throws BusinessException {
            //判断order是否存在
            Order od = orderDao.selectOrder(order);
            if (od == null) return 1;
            //判断order状态是否是未预定
        if(PurchaseOrderStatus.SUCCESS.name().equals(orderDao.selectOrder(od).getOrderState())) return 2;
            //t_order表中 获取仓库id
            int warehouseId = od.getWarehouseId();

            //t_sale表中，通过orderId，找到所有的sale
        Sale sale = new Sale();
        sale.setOrderId(od.getOrderId());
        List<Sale> saleList = saleDao.selectSale(sale);
        //判断sale是否存在
        if(saleList.size() == 0 )return 3;
        for(Sale sales : saleList){

            //获取skuId
            int skuId = sales.getSkuId();
            int amount = sales.getAmount();
            //查询库存是否存在
            Inventory inventory = new Inventory();
            inventory.setWarehouseId(warehouseId);
            inventory.setSkuId(skuId);
            if(inventoryDao.selectInventoryId(inventory) == null ) return 4;


            //将skuId和warehouseId传入库存映射对象中，并扣除库存amount
            InventoryParam ip = new InventoryParam();
            ip.setSkuId(skuId);
            ip.setWarehouseId(warehouseId);
            ip.setRealInventoryAdd(amount);
            //扣除并判断
            if (inventoryDao.updateInventoryCut(ip)> 0){
                od.setOrderState(PurchaseOrderStatus.SUCCESS.name());
                orderDao.updateOrder(od);
            }else {
                throw new BusinessException("SHORTEGY","库存不足");
            }
        }
        return 5;
    }



    //添加订单
    @Override
    public int addOrder(WarehouseRegionId warehouseRegionIds) {
        Order order = new Order();
        order.setWarehouseId(warehouseRegionIds.getWarehouseId());
        order.setRegionId(warehouseRegionIds.getRegionId());
        order.setOrderState(PurchaseOrderStatus.INIT.name());
        order.setOrderCreateTime(new Date());
        if (orderDao.addOrder(order)>0){
            return order.getOrderId();
        }

        return 0;
    }
}
