package com.huzi.dao;

import com.huzi.domain.purchase.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderDao {




        //创建（新增）采购订单
        int insertPurchaseOrder(PurchaseOrder purchaseOrder);



        //查询所有采购单
        List<PurchaseOrder> selectPurchaseOrder();


        //查询采购单（根据订单号）
        PurchaseOrder selectPurchaseOrderById(Integer  purchaseId);


        //查看采购单是否完单
        String checkState(Integer  purchaseId);



        //完成订单，update for state
        int finishPurchaseById(Integer purchaseId);
    }


