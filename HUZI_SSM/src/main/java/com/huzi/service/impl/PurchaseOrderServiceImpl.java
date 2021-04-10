package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.PurchaseOrderDao;
import com.huzi.dao.Sku_Dao;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    private Sku_Dao skuDao;


    //1新增采购单
    @Override
    public int insertOrder(PurchaseOrder purchaseOrder) {

//todo 1.验证sku 仓库是存在 2.设置好creatTime 状态
        if(skuDao.selectSkuById(purchaseOrder.getSkuId() ) ==null ){
            return 0;
        }
        purchaseOrder.setPurchaseCreateTime(new Date());
        purchaseOrder.setPurchaseState(PurchaseOrderStatus.INIT.name());
        return purchaseOrderDao.insertPurchaseOrder(purchaseOrder);

    }


    //2查询所有采购单
    @Override
    public List<PurchaseOrder> selectPurchaseOrder() {

        return purchaseOrderDao.selectPurchaseOrder();
    }


    //3查询采购单（根据订单号）
    @Override
    public PurchaseOrder selectPurchaseOrderById(Integer purchaseId) {

        return purchaseOrderDao.selectPurchaseOrderById(purchaseId);
    }



    //4查询订单状态
    @Override
    public String checkState(Integer purchaseId) {
        return purchaseOrderDao.checkState(purchaseId);
    }



    //5完成订单FINISH
    @Override
    public int finishPurchaseState(Integer purchaseId) {

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseId(purchaseId);
        purchaseOrder.setPurchaseUpdateTime(new Date());
        purchaseOrder.setPurchaseState(PurchaseOrderStatus.FINISH.name());
        int num = purchaseOrderDao.finishPurchase(purchaseOrder);
        return num;
    }



}
