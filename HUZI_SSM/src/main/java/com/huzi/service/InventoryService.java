package com.huzi.service;


import com.huzi.domain.Warehouse.Inventory;

public interface InventoryService {


    //1新建商品库存
    int insertInventory(Inventory inventory);



    //管理员管理**
    String finishPurchaseOrderByUser(Integer purchaseId,Integer orderDetailsId);



}



