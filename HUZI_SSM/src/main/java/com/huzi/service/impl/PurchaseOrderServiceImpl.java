package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.PurchaseOrderDao;
import com.huzi.dao.SkuDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.purchase.OrderDetails;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;



@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryDao inventoryDao;


    //---(新增订单)----------------------------------------------
    //todo 新增采购单（新）
    @Override
    public int insertPurchase(PurchaseOrder purchaseOrder) {
        purchaseOrder.setPurchaseState(PurchaseOrderStatus.INIT.name());
        purchaseOrder.setPurchaseCreateTime(new Date());
        if (purchaseOrderDao.insertPurchase(purchaseOrder)>0){
            return purchaseOrder.getPurchaseId();
        }else {
            return 0;
        }
    }

    @Override
    public int insertOrderDetails(List<OrderDetails> orderDetailsList) {
        int num ;
        for ( OrderDetails  orderDetails :orderDetailsList){
            num =  purchaseOrderDao.insertDetails(orderDetails);
            if (num == 0 ){
                    return 0;
            }
        }
        return 1;
    }


    //-------------------------------------------------------------




    //-----------查询采购单及其详情(根据订单id)-----------------------------------------
    @Override
    public PurchaseOrder selectPurchaseOrderAndDetails(Integer purchaseId) {
        PurchaseOrder purchaseOrder =  purchaseOrderDao.selectPurchaseOrderById(purchaseId);

        if (purchaseOrder != null) {
            List<OrderDetails> orderDetailsList = purchaseOrderDao.selectOrderDetailsByPurchaseId(purchaseId);
            purchaseOrder.setOrderDetails(orderDetailsList);
        }
        return purchaseOrder;

    }
    //---------------------------------------------------------------------







    //-------------作废订单（CommonResult）----------------------
    public String invalidPurchase(Integer purchaseId){
        //1.验证是否存在
        PurchaseOrder purchaseOrder = purchaseOrderDao.selectPurchaseOrderById(purchaseId);
        String x = check(purchaseOrder);
        //2.只有INIT的作废，完成的单不能作废
        if(x!= null)return x;
        purchaseOrder.setPurchaseUpdateTime(new Date());
        purchaseOrder.setPurchaseState(PurchaseOrderStatus.INVALID.name());
        if(purchaseOrderDao.updatePurchase(purchaseOrder)>0){
            return "作废成功";
        }
        return "作废失败";


    }
    //-------------------------------------------------------------------

    //完成订单FINISH
    @Transactional
    @Override
    public String finishPurchase(OrderDetails orderDetails) {
        String tip = null;
        int purchaseId = orderDetails.getPurchaseId();
        //1通过订单编号，查询
        PurchaseOrder po =purchaseOrderDao.selectPurchaseOrderById(purchaseId);
        //2查询订单状态及是否为null
        String x = check(po);
        if (x != null) return x;
        //3状态为INIT走以下程序：
        //通过skuId+仓库id去查库存id,如果有就增加库存，没有就新建库存
        Integer inventoryId = getAvailableInventoryId(orderDetails);
        if( inventoryId != null){
            //增加库存
            //4.1
            int updateInventory = addInventory(orderDetails, inventoryId);
            if(updateInventory>0){
                //4.2
                int num = finishPurchaseOrder(po);
                if(num > 0){
                   tip = "订单完结成功";
                }else {
                   return "订单完结失败";
                }
            }else {
                return "库存更新失败";
            }
        } else{
            //4.3 4.4
            //todo insert - 更新 ***
            Inventory inventory = new Inventory();
            inventory.setSkuId(orderDetails.getSkuId());
            inventory.setWarehouseId(orderDetails.getWarehouseId());
            inventoryDao.insertInventory(inventory);
            addInventory(orderDetails,inventory.getInventoryId());
            finishPurchaseOrder(po);
            tip = "订单完结成功";
        }
        return tip;
    }



    //更改采购单状态
    private int finishPurchaseOrder(PurchaseOrder po) {
        //将状态改为FINISH,更新时间
        po.setPurchaseUpdateTime(new Date());
        po.setPurchaseState(PurchaseOrderStatus.FINISH.name());
        return purchaseOrderDao.updatePurchase(po);
    }

    //添加库存
    private int addInventory(OrderDetails orderDetails, Integer inventoryId) {
        int amount = orderDetails.getAmount();
        InventoryParam inventoryParam = new InventoryParam();
        inventoryParam.setInventoryId(inventoryId);
        inventoryParam.setPhysicalInventoryAdd(amount);
        inventoryParam.setRealInventoryAdd(amount);
        return inventoryDao.updateInventory(inventoryParam);
    }

    //获取库存id
    private Integer getAvailableInventoryId(OrderDetails orderDetails) {
        int skuId = orderDetails.getSkuId();
        int warehouseId = orderDetails.getWarehouseId();
        Inventory inventory = new Inventory();
        inventory.setSkuId(skuId);
        inventory.setWarehouseId(warehouseId);
        return inventoryDao.selectInventoryId(inventory);
    }

    //检查采购单状态
    private String check(PurchaseOrder po) {
        //验证单号是否存在
        //如果检查状态是否是INIT
        if(po==null){
            return "没有此单号，请重新输入";
        }
        if(PurchaseOrderStatus.FINISH.name().equals(po.getPurchaseState())){
            return  "订单已经完成，操作无效";
        }else if (PurchaseOrderStatus.INVALID.name().equals(po.getPurchaseState())){
            return  "订单已经作废，操作无效";
        }
        return null;
    }


}
