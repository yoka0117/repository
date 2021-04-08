package com.huzi.domain.Warehouse;

public class Inventory {

    private int inventoryId;
    private int skuId;
    private int warehouseId;
    private int Physical_Inventory;
    private int Real_Inventory;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getPhysical_Inventory() {
        return Physical_Inventory;
    }

    public void setPhysical_Inventory(int physical_Inventory) {
        Physical_Inventory = physical_Inventory;
    }

    public int getReal_Inventory() {
        return Real_Inventory;
    }

    public void setReal_Inventory(int real_Inventory) {
        Real_Inventory = real_Inventory;
    }
}
