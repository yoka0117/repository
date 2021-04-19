package com.huzi.dao;


import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryDao {


    //1新建商品库存
    int insertInventory(Inventory inventory);



    //增加库存
    int updateInventory(InventoryParam inventoryParam);


    //通过skuid+仓库id查库存表id
    Integer selectInventoryId(Inventory inventory);



    //扣除库存
    int updateInventoryCut(InventoryParam inventoryParam);


    //增加库存
    int updateInventoryAdd(InventoryParam inventoryParam);




}