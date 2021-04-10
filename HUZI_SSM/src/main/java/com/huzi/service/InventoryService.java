package com.huzi.service;


import com.huzi.domain.Warehouse.Inventory;

public interface InventoryService {


    //1新建商品库存
    int insertInventory(Inventory inventory);



    //2增加库存
    int updateInventory(Inventory inventory);




}



