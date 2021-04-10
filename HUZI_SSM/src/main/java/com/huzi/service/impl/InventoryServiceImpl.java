package com.huzi.service.impl;

import com.huzi.dao.InventoryDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceImpl implements InventoryService {


@Autowired
private InventoryDao inventoryDao;

    //1新建商品库存
    @Override
    public int insertInventory(Inventory inventory) {
        return inventoryDao.insertInventory(inventory);
    }

    @Override
    public int updateInventory(Inventory inventory) {
        int num = 0;
        int skuId = inventory.getSkuId();
        int warehouseId = inventory.getWarehouseId();

        Inventory inventory1 = new Inventory();
        inventory1.setSkuId(skuId);
        inventory1.setWarehouseId(warehouseId);

        if(inventoryDao.selectBySkuWarehouse(inventory1)== null){
            num = inventoryDao.insertInventory(inventory1);
        }else {
            //1.查询当前库存=10
            //2.计算采购完结后的库存 = 10 + 5 =15
            //3.更新库存 库存=15
            num =  inventoryDao.updateInventory(inventory);
        }


        return num;
    }
}


