package com.huzi.service;


import com.huzi.domain.purchase.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {


    //1新增采购单
    int insertOrder(PurchaseOrder purchaseOrder);

    //2查询采购单列表
    List<PurchaseOrder> selectPurchaseOrder();

   /* //3查询采购单（根据订单号查）
    PurchaseOrder selectPurchaseOrderById(Integer purchaseId);


    //4查询订单状态
    String checkState(Integer purchaseId);*/


    //完成订单FINISH
    String finishPurchaseState(PurchaseOrder purchaseOrder);

}
