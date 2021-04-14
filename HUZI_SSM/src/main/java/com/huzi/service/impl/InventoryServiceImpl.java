package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
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
        inventory.setInventoryState(PurchaseOrderStatus.INIT.name());
        return inventoryDao.insertInventory(inventory);
    }




}


