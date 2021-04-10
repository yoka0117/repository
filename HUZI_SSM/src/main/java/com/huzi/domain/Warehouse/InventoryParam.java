package com.huzi.domain.Warehouse;

public class InventoryParam {
    private int inventoryId;
    private int skuId;
    private int warehouseId;
    private int physicalInventoryAdd;
    private int realInventoryAdd;

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

    public int getPhysicalInventoryAdd() {
        return physicalInventoryAdd;
    }

    public void setPhysicalInventoryAdd(int physicalInventoryAdd) {
        this.physicalInventoryAdd = physicalInventoryAdd;
    }

    public int getRealInventoryAdd() {
        return realInventoryAdd;
    }

    public void setRealInventoryAdd(int realInventoryAdd) {
        this.realInventoryAdd = realInventoryAdd;
    }
}
