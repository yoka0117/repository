package com.huzi.domain.purchase;

import java.util.Date;

public class PurchaseOrder {


    private int purchase_Id;
    private int sku_Id;
    private int warehouse_Id;
    private int purchase_Amount;
    private Date purchase_Create_Time;
    private String purchase_State;
    private Date purchase_Update_Time;

    public int getPurchase_Id() {
        return purchase_Id;
    }

    public void setPurchase_Id(int purchase_Id) {
        this.purchase_Id = purchase_Id;
    }

    public int getSku_Id() {
        return sku_Id;
    }

    public void setSku_Id(int sku_Id) {
        this.sku_Id = sku_Id;
    }

    public int getWarehouse_Id() {
        return warehouse_Id;
    }

    public void setWarehouse_Id(int warehouse_Id) {
        this.warehouse_Id = warehouse_Id;
    }

    public int getPurchase_Amount() {
        return purchase_Amount;
    }

    public void setPurchase_Amount(int purchase_Amount) {
        this.purchase_Amount = purchase_Amount;
    }

    public Date getPurchase_Create_Time() {
        return purchase_Create_Time;
    }

    public void setPurchase_Create_Time(Date purchase_Create_Time) {
        this.purchase_Create_Time = purchase_Create_Time;
    }

    public String getPurchase_State() {
        return purchase_State;
    }

    public void setPurchase_State(String purchase_State) {
        this.purchase_State = purchase_State;
    }

    public Date getPurchase_Update_Time() {
        return purchase_Update_Time;
    }

    public void setPurchase_Update_Time(Date purchase_Update_Time) {
        this.purchase_Update_Time = purchase_Update_Time;
    }
}

