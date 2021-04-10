package com.huzi.dao;


import com.huzi.domain.Warehouse.Inventory;
import org.apache.ibatis.annotations.Param;

public interface InventoryDao {


    //1新建商品库存
    int insertInventory(Inventory inventory);



    //2增加库存
    int updateInventory(Inventory inventory);


    //根据skuid和warehouseid查询
    //Inventory selectBySkuWarehouse(@Param("skuId") Integer skuid, @Param("warehouseId") Integer warehouseid);
    Inventory selectBySkuWarehouse(Inventory inventory);



}