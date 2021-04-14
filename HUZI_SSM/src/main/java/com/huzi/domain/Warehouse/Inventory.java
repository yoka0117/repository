package com.huzi.domain.Warehouse;

public class Inventory {

    private int inventoryId;
    private int skuId;
    private int warehouseId;
    private int physicalInventory;
    private int realInventory;


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

    public int getPhysicalInventory() {
        return physicalInventory;
    }

    public void setPhysicalInventory(int physicalInventory) {
        this.physicalInventory = physicalInventory;
    }

    public int getRealInventory() {
        return realInventory;
    }

    public void setRealInventory(int realInventory) {
        this.realInventory = realInventory;
    }



}
