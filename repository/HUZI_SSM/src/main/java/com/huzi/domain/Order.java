package com.huzi.domain;

import java.util.Date;

public class Order {


    private int order_id;
    private int sku_id;
    private int warehouse_id;
    private int order_amount;
    private Date order_create_time;
    private String order_state;
    private Date order_update_time;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public int getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(int order_amount) {
        this.order_amount = order_amount;
    }

    public Date getOrder_create_time() {
        return order_create_time;
    }

    public void setOrder_create_time(Date order_create_time) {
        this.order_create_time = order_create_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public Date getOrder_update_time() {
        return order_update_time;
    }

    public void setOrder_update_time(Date order_update_time) {
        this.order_update_time = order_update_time;
    }
}
