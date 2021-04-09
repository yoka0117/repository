package com.huzi.service;


import com.huzi.domain.purchase.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {


    //新增采购单
    int insertOrder(PurchaseOrder purchaseOrder);

    //查询采购单
    List<PurchaseOrder> selectPurchaseOrder();

    //查询采购单（根据订单号查）
    PurchaseOrder selectPurchaseOrderById(Integer purchaseId);


    //查询采购单是否完单
    String checkState(Integer purchaseId);
}
