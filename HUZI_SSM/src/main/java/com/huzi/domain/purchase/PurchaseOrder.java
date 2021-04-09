package com.huzi.domain.purchase;

import java.util.Date;

public class PurchaseOrder {


    private int purchaseId;
    private int skuId;
    private int warehouseId;
    private int purchaseAmount;
    private Date purchaseCreateTime;
    private String purchaseState;
    private Date purchaseUpdateTime;

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
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

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Date getPurchaseCreateTime() {
        return purchaseCreateTime;
    }

    public void setPurchaseCreateTime(Date purchaseCreateTime) {
        this.purchaseCreateTime = purchaseCreateTime;
    }

    public String getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(String purchaseState) {
        this.purchaseState = purchaseState;
    }

    public Date getPurchaseUpdateTime() {
        return purchaseUpdateTime;
    }

    public void setPurchaseUpdateTime(Date purchaseUpdateTime) {
        this.purchaseUpdateTime = purchaseUpdateTime;
    }
}

