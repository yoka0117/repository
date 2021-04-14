package com.huzi.service;


import com.huzi.domain.purchase.OrderDetails;
import com.huzi.domain.purchase.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {



//todo 新增采购单 （新）
    int insertPurchase(PurchaseOrder purchaseOrder);

    int insertOrderDetails(List<OrderDetails> orderDetailsList);





    //2查询采购单列表及其详情
    PurchaseOrder selectPurchaseOrderAndDetails(Integer purchaseId);



    //作废订单
    String invalidPurchase(Integer purchaseId);



    //完成订单FINISH
    String finishPurchase(OrderDetails orderDetails);




}
