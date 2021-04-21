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
    Inventory selectInventory(Inventory inventory);




    //扣除库存(real)
    int updateInventoryCutReal(InventoryParam inventoryParam);

    //扣除库存（物理）
    int updateInventoryCutPhysical(InventoryParam inventoryParam);


    //增加库存
    int updateInventoryAdd(InventoryParam inventoryParam);


    //订单出库管理
    Inventory selectInventoryBy(Inventory inventory);

}