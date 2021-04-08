package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.PurchaseOrderDao;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;


    @Override
    public int insertOrder(PurchaseOrder purchaseOrder) {
//todo 1.验证sku 仓库是存在 2.设置好creatTime 状态

        purchaseOrder.setPurchase_Create_Time(new Date());
        purchaseOrder.setPurchase_State(PurchaseOrderStatus.INIT.name());
        return purchaseOrderDao.insertPurchaseOrder(purchaseOrder);

    }
}
