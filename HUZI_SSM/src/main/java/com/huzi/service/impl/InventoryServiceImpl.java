package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.PurchaseOrderDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.purchase.OrderDetails;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {


@Autowired
private InventoryDao inventoryDao;

@Autowired
private PurchaseOrderDao purchaseOrderDao;

    //1新建商品库存ok
    @Override
    public int insertInventory(Inventory inventory) {
        return inventoryDao.insertInventory(inventory);
    }



    @Override
    public String finishPurchaseOrderByUser(Integer purchaseId, Integer orderDetailsId) {
        //验证采购单/详情表是否存在
        OrderDetails orderDetails = purchaseOrderDao.selectOrderDetails(orderDetailsId);
        PurchaseOrder purchaseOrder = purchaseOrderDao.selectPurchaseOrderById(purchaseId);

        if (orderDetails == null){return "采购详情表单不存在";}
        if(purchaseOrder==null){return  "采购表单不存在";}

        //详情单是否属于采购单
        if(orderDetails.getPurchaseId() != purchaseOrder.getPurchaseId()){
            return "采购单与详情表单不对应";
        }

        //查看详情单是否是INIT
        if(PurchaseOrderStatus.INIT.name().equals(purchaseOrder.getPurchaseState())) {
            if (PurchaseOrderStatus.INIT.name().equals(orderDetails.getOrderDetailsState())) {
                //判断
                Integer inventoryId = getAvailableInventoryId(orderDetails);
                if (inventoryId != null) {
                    //完结详情单
                    orderDetails.setOrderDetailsState(PurchaseOrderStatus.FINISH.name());
                    purchaseOrderDao.updateDetails(orderDetails);
                    //加库存
                    InventoryParam inventoryParam = new InventoryParam();
                    inventoryParam.setSkuId(orderDetails.getSkuId());
                    inventoryParam.setWarehouseId(orderDetails.getWarehouseId());
                    inventoryParam.setPhysicalInventoryAdd(orderDetails.getAmount());
                    inventoryParam.setRealInventoryAdd(orderDetails.getAmount());
                    inventoryDao.updateInventory(inventoryParam);
                }else {
                    //todo insert - 更新 ***
                    Inventory inventory = new Inventory();
                    inventory.setSkuId(orderDetails.getSkuId());
                    inventory.setWarehouseId(orderDetails.getWarehouseId());
                    inventoryDao.insertInventory(inventory);
                    addInventory(orderDetails,inventory.getInventoryId());
                    finishPurchaseOrder(purchaseOrder);
                }
            }

        }




        //查询采购单下面所有详情，如果都完结了，则完结采购单
        List<OrderDetails>  orderDetailsList = purchaseOrderDao.selectOrderDetailsByPurchaseId(purchaseId);
            for (OrderDetails od : orderDetailsList){
                if (!PurchaseOrderStatus.FINISH.name().equals(od.getOrderDetailsState())){
                    return "成功完结";
                }
                //完结采购单
                purchaseOrder.setPurchaseState(PurchaseOrderStatus.FINISH.name());
                purchaseOrder.setPurchaseUpdateTime(new Date());
                purchaseOrderDao.updatePurchase(purchaseOrder);
            }

        return null;
    }

    //获取库存id
    private Integer getAvailableInventoryId(OrderDetails orderDetails) {
        int skuId = orderDetails.getSkuId();
        int warehouseId = orderDetails.getWarehouseId();
        Inventory inventory = new Inventory();
        inventory.setSkuId(skuId);
        inventory.setWarehouseId(warehouseId);
        Inventory inventory1 = inventoryDao.selectInventory(inventory);
        return inventory1.getInventoryId();

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
    //更改采购单状态
    private int finishPurchaseOrder(PurchaseOrder po) {
        //将状态改为FINISH,更新时间
        po.setPurchaseUpdateTime(new Date());
        po.setPurchaseState(PurchaseOrderStatus.FINISH.name());
        return purchaseOrderDao.updatePurchase(po);
    }
}


