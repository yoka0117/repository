package com.huzi.dao;

import com.huzi.domain.purchase.OrderDetails;
import com.huzi.domain.purchase.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderDao {

        // TODO: 2021/4/12 创建采购单
        int insertPurchase(PurchaseOrder purchaseOrder);

        //TODO  创建采购单详情
        int insertDetails(OrderDetails orderDetails);



        //查询采购单（根据订单号）
        PurchaseOrder selectPurchaseOrderById(Integer  purchaseId);
        List<OrderDetails> selectOrderDetailsByPurchaseId(Integer  purchaseId);


        //更改订单状态， update for state
        int updatePurchase(PurchaseOrder purchaseOrder);



        //4查询订单状态
        String checkState(Integer  purchaseId);

    }


