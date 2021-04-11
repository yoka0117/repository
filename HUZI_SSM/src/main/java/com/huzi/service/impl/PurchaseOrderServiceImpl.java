package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.PurchaseOrderDao;
import com.huzi.dao.SkuDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private InventoryDao inventoryDao;


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


    //完成订单FINISH
    @Transactional
    @Override
    public String finishPurchaseState(PurchaseOrder purchaseOrder) {
        int purchaseId = purchaseOrder.getPurchaseId();
        String tip = null;
        //验证单号是否存在
        if(purchaseOrderDao.selectPurchaseOrderById(purchaseId)!=null){
            //如果检查状态是否是INIT
            String state = purchaseOrderDao.checkState(purchaseId);
            if(state.equals(PurchaseOrderStatus.INIT.name())){
                //通过skuid+仓库id去查库存id,如果有 就增加库存，没有就新建库存
                int skuId = purchaseOrder.getSkuId();
                int  warehouseId = purchaseOrder.getWarehouseId();
                Inventory inventory = new Inventory();
                inventory.setSkuId(skuId);
                inventory.setWarehouseId(warehouseId);
                int inventoryId = inventoryDao.selectInventoryId(inventory);
                if( inventoryId != 0){
                    //增加库存
                    int amount = purchaseOrder.getPurchaseAmount();
                    InventoryParam inventoryParam = new InventoryParam();
                    inventoryParam.setInventoryId(inventoryId);
                    inventoryParam.setPhysicalInventoryAdd(amount);
                    inventoryParam.setRealInventoryAdd(amount);
                    if(inventoryDao.updateInventory(inventoryParam)>0){
                        //将状态改为FINISH,更新时间
                        purchaseOrder.setPurchaseUpdateTime(new Date());
                        purchaseOrder.setPurchaseState(PurchaseOrderStatus.FINISH.name());
                        int num = purchaseOrderDao.finishPurchase(purchaseOrder);
                        if(num > 0){
                            tip = "订单完结成功";
                        }else {
                            return "订单完结失败";
                        }
                    }else {
                        return "库存更新失败";
                    }
                }



            }else {
                return  "订单已经完成，无需重复提交";
            }
        }else {
            return "没有此单号，请重新输入";
        }
        return tip;
    }


}
