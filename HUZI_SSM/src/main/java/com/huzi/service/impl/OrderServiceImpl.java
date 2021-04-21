package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.userOrder.OrderDao;
import com.huzi.dao.userOrder.SaleDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.Warehouse.WarehouseRegionId;
import com.huzi.domain.userOrder.Order;
import com.huzi.domain.userOrder.OrderDelivery;
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
    public int reserve(Integer orderId) throws BusinessException {
        //通过orderId，查Order
        Order od = new Order();
        od.setOrderId(orderId);
        Order order = orderDao.selectOrder(od);
        //判断order是否存在
        if(order == null) return 1;
        //判断order的状态 , INIT , SUCCESS , LACK
        String orderState =  order.getOrderState();
        //（1）如果order的状态是SUCCESS
        if (PurchaseOrderStatus.SUCCESS.name().equals(orderState)){ return 2; }
        //（2）如果order的状态是INIT
        if(PurchaseOrderStatus.INIT.name().equals(orderState)){
            //在t_order表中获取warehouseId
            Integer warehouseId = order.getWarehouseId();
            //在t_sale表中通过orderId，获取所有有关的SALE对象,List<Sale>
            Sale sale = new Sale();
            sale.setOrderId(orderId);
            List<Sale> saleList = saleDao.selectSaleByOrderId(sale);
            //判断一下，Sale是否存在
            if (saleList.size() == 0 ) return  3;
            //获取所有的skuId和amount
            for (Sale sales : saleList){
                Integer skuId = sales.getSkuId();
                Integer amount = sales.getAmount();
                //查询有关库存是否存在
                Inventory inventory = new Inventory();
                inventory.setSkuId(skuId);
                inventory.setWarehouseId(warehouseId);
                Inventory it = inventoryDao.selectInventory(inventory);
                if (it == null) return 4;
                //**准备进行库存操作**
                //根据skuId和warehouseId，先判断real库存数量是否为0。如果为0，则直接更新sale表中的状态
                Integer realInventory = it.getRealInventory();
                if (realInventory == 0 ) {
                    sales.setSaleState(PurchaseOrderStatus.LACK.name());
                    saleDao.updateSaleState(sales);
                }
                //将sale属性，装进InventoryParam对象中
                InventoryParam inventoryParam = new InventoryParam();
                inventoryParam.setWarehouseId(warehouseId);
                inventoryParam.setSkuId(skuId);
                inventoryParam.setRealInventoryAdd(amount);
                //扣减数据,返回成功数
                int result = inventoryDao.updateInventoryCutReal(inventoryParam);
                //查看是否成功
                if (result > 0 ){
                    //如果成功,将sale表中对应的状态改为success
                    sales.setSaleState(PurchaseOrderStatus.SUCCESS.name());
                    sales.setAlready(amount);
                    saleDao.updateSaleState(sales);
                }else {
                    //如果不成功，证明real库存不够扣
                    //1.此时应当先扣除已有的部分
                    inventoryParam.setRealInventoryAdd(realInventory);
                    inventoryDao.updateInventoryCutReal(inventoryParam);
                    //2.再将sale表中的状态改为LACK，并标注已经预约的数量
                    sales.setSaleState(PurchaseOrderStatus.LACK.name());
                    sales.setAlready(realInventory);
                    saleDao.updateSaleState(sales);
                }
            }
        }
        //(3)如果order的状态是LACK
        if (PurchaseOrderStatus.LACK.name().equals(orderState)) {
            //在t_order表中获取warehouseId
            Integer warehouseId = order.getWarehouseId();
            //在t_sale表中通过orderId，获取所有有关的SALE对象,List<Sale>
            Sale sale = new Sale();
            sale.setOrderId(orderId);
            List<Sale> saleList = saleDao.selectSaleByOrderId(sale);
            //循环遍历
            for (Sale sales : saleList){
                //看一下哪个sale的状态是LACK
                if(PurchaseOrderStatus.LACK.name().equals(sales.getSaleState())){
                    //拿到此sale的skuId，amount，already的值
                    Integer skuId = sales.getSkuId();
                    Integer amount = sales.getAmount();
                    Integer already = sales.getAlready();
                    //查询有关库存是否存在
                    Inventory inventory = new Inventory();
                    inventory.setSkuId(skuId);
                    inventory.setWarehouseId(warehouseId);
                    Inventory it = inventoryDao.selectInventory(inventory);
                    if (it == null) return 4;
                    //此时取出real的值，看看是否为0
                    Integer realInventory =it.getRealInventory();
                    if(realInventory == 0) return 6;
                    //如果不为0，则开始扣库存
                    //将sale属性，装进InventoryParam对象中
                    InventoryParam inventoryParam = new InventoryParam();
                    inventoryParam.setWarehouseId(warehouseId);
                    inventoryParam.setSkuId(skuId);
                    inventoryParam.setRealInventoryAdd(amount - already);
                    int result = inventoryDao.updateInventoryCutReal(inventoryParam);
                    //判断结果
                    if (result > 0){
                        //如果成功,将sale表中对应的状态改为success
                        sales.setSaleState(PurchaseOrderStatus.SUCCESS.name());
                        sales.setAlready(amount);
                        saleDao.updateSaleState(sales);
                    }else {
                        //如果失败
                        //1.此时应当先扣除已有的部分
                        inventoryParam.setRealInventoryAdd(realInventory);
                        inventoryDao.updateInventoryCutReal(inventoryParam);
                        //2.更新已经预约的数量
                        sales.setAlready(realInventory + already);
                        saleDao.updateSaleState(sales);
                    }
                }
            }
        }
        //检查所有的sale状态，如果都为success，则改变order订单的状态
        Sale sale = new Sale();
        sale.setOrderId(orderId);
        List<Sale> saleList = saleDao.selectSaleByOrderId(sale);
        for (Sale sales : saleList){
            if (!PurchaseOrderStatus.SUCCESS.name().equals(sales.getSaleState())){
                order.setOrderState(PurchaseOrderStatus.LACK.name());
                orderDao.updateOrder(order);
                return 7;
            }
        }
        //走到这里，所有sale状态都为success了
        order.setOrderState(PurchaseOrderStatus.SUCCESS.name());
        orderDao.updateOrder(order);
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


    //订单出库
    @Override
    public int orderDelivery(Integer orderId, List<OrderDelivery> orderDelivery) {
        //根据orderId找出相关的详情信息
        Order order = new Order();
        order.setOrderId(orderId);
        Order order1 = orderDao.selectOrder(order);
        //验证订单是否存在
        if(order1 == null ) return 1;
        //验证订单状态是不是属于预订成功
        if (!PurchaseOrderStatus.SUCCESS.name().equals(order1.getOrderState())){
            return 2;
        }
        //验证出库详情跟预订详情是否完全一致
        for(OrderDelivery orderDelivery1 : orderDelivery){
            Sale sale = new Sale();
            sale.setSkuId(orderDelivery1.getSkuId());
            sale.setAmount(orderDelivery1.getAmount());
            sale.setOrderId(orderId);
            if (saleDao.selectSaleBySkuIdAmount(sale) ==null ){
                return 3;
            }
        }


        //验证出库详情跟预订详情是否完全一致,第二种方法
        List<String> orderDeliveryList = new ArrayList<>();
        List<String> salesList = new ArrayList<>();
        for (OrderDelivery orderDelivery1 :orderDelivery){
            String skuId = String.valueOf(orderDelivery1.getSkuId());
            String amount = String.valueOf(orderDelivery1.getAmount());
            String orderId1 = String.valueOf(orderId);
            orderDeliveryList.add(skuId);
            orderDeliveryList.add(amount);
            orderDeliveryList.add(orderId1);


            Sale sale = new Sale();
            sale.setOrderId(orderId);
            List<Sale> sales = saleDao.selectSaleByOrderId(sale);
            for (Sale sale1 : sales){
                String saleSkuId = String.valueOf(sale1.getSkuId());
                String saleAmount = String.valueOf(sale1.getSkuId());
                String saleOrderId = String.valueOf(sale1.getOrderId());
                salesList.add(saleSkuId);
                salesList.add(saleAmount);
                salesList.add(saleOrderId);

                if (!orderDeliveryList.contains(salesList)){
                    return 3;
                }
            }
        }


        //库存扣减物理库存
        Sale sale = new Sale();
        sale.setOrderId(orderId);
        List<Sale> saleList = saleDao.selectSaleByOrderId(sale);
        for (Sale sales : saleList){
            InventoryParam ip = new InventoryParam();
            ip.setSkuId(sales.getSkuId());
            ip.setPhysicalInventoryAdd(sales.getAmount());
            ip.setWarehouseId(order1.getWarehouseId());
            inventoryDao.updateInventoryCutPhysical(ip);
        }
        //订单改成出库成功。
        order1.setOrderState(PurchaseOrderStatus.DELIVERY.name());
        orderDao.updateOrder(order1);

        return 0;
    }
}
