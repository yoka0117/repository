package com.huzi.dao;

import com.huzi.domain.purchase.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderDao {




        //1创建（新增）采购订单
        int insertPurchaseOrder(PurchaseOrder purchaseOrder);



        //2查询采购单列表
        List<PurchaseOrder> selectPurchaseOrder();




        //3查询采购单（根据订单号）
        PurchaseOrder selectPurchaseOrderById(Integer  purchaseId);


        //4查询订单状态
        String checkState(Integer  purchaseId);



        //5完成订单FINISH， update for state
        int finishPurchase(PurchaseOrder purchaseOrder);
    }


