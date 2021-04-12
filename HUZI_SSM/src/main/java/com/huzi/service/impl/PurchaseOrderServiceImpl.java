package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.InventoryDao;
import com.huzi.dao.PurchaseOrderDao;
import com.huzi.dao.SkuDao;
import com.huzi.domain.Warehouse.Inventory;
import com.huzi.domain.Warehouse.InventoryParam;
import com.huzi.domain.purchase.OrderDetails;
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


    //todo 新增采购单（新）
    @Override
    public int insertPurchase(PurchaseOrder purchaseOrder) {
        //判断sku是否存在
        if(skuDao.selectSkuById(purchaseOrder.getSkuId() ) ==null ){
            return 0;
        }
        purchaseOrder.setPurchaseCreateTime(new Date());
        purchaseOrder.setPurchaseState(PurchaseOrderStatus.INIT.name());
        purchaseOrderDao.insertPurchase(purchaseOrder);
        OrderDetails orderDetails = new OrderDetails();
        Integer purchaseId =  purchaseOrder.getPurchaseId();
        orderDetails.setPurchaseId(purchaseId);
        orderDetails.setSkuId(purchaseOrder.getSkuId());
        orderDetails.setWarehouseId(purchaseOrder.getWarehouseId());
        orderDetails.setAmount(purchaseOrder.getPurchaseAmount());
        int result = purchaseOrderDao.insertDetails(orderDetails);

        return result ;
    }





 /*   //1新增采购单
    @Override
    public int insertOrder(PurchaseOrder purchaseOrder) {

   //todo 1.验证sku 仓库是存在 2.设置好creatTime 状态


        if(skuDao.selectSkuById(purchaseOrder.getSkuId() ) ==null ){
            return 0;
        }
        purchaseOrder.setPurchaseCreateTime(new Date());
        purchaseOrder.setPurchaseState(PurchaseOrderStatus.INIT.name());
        return purchaseOrderDao.insertPurchaseOrder(purchaseOrder);

    }*/


    //2查询所有采购单
    @Override
    public List<PurchaseOrder> selectPurchaseOrder() {

        return purchaseOrderDao.selectPurchaseOrder();
    }
    //1.采购单--> 一种库存
    //2.采购单支持多个商品 多个仓库 现有表结构不满足 -？
    // 一个采购单【1.红色37码 北京进货10个 2.红色37码 上海进货20个 3.蓝色36码北京进货5个 4.绿色40码 杭走进货1个】


    //CommonResult
    public String closePurchaseOrder(Integer orderId){
        //1.验证是否存在
        PurchaseOrder po = purchaseOrderDao.selectPurchaseOrderById(orderId);
        String x = check(po);
        if(x!= null)return x;
        po.setPurchaseUpdateTime(new Date());
        po.setPurchaseState(PurchaseOrderStatus.INVALID.name());
        if(purchaseOrderDao.finishPurchase(po)>0){
            return "作废成功";
        }
        return "作废失败";


        //2.只有INIT的作废，完成的单不能作废

    }


    //完成订单FINISH
    @Transactional
    @Override
    public String finishPurchaseState(PurchaseOrder purchaseOrder) {
        int purchaseId = purchaseOrder.getPurchaseId();
        String tip = null;
        //1
        PurchaseOrder po =purchaseOrderDao.selectPurchaseOrderById(purchaseId);
        //2
        String x = check(po);
        if (x != null) return x;
        //3
        //通过skuid+仓库id去查库存id,如果有 就增加库存，没有就新建库存
        Integer inventoryId = getAvailableInventoryId(po);
        if( inventoryId != null){
            //增加库存
            //4.1
            int updateInventory = addInventory(po, inventoryId);
            if(updateInventory>0){
                //4.2
                int num = finishPurchaseOrder(po);
                if(num > 0){
                            tip = "订单完结成功";
                   }else {
                            return "订单完结失败";
                  }
                    }else {
                        return "库存更新失败";
                    }
                }else{
            //4.3 4.4
            //todo insert - 更新 ***
                    this.insertPurchase(purchaseOrder);
                    if(addInventory(po, inventoryId)>0){
                        int num = finishPurchaseOrder(po);
                        if(num > 0){
                            tip = "订单完结成功";
                        }else {
                            return "订单完结失败";
                        }
                    }else {
                        return "库存更新失败";
                    }
                }
        return tip;
    }
    private int finishPurchaseOrder(PurchaseOrder po) {
        //将状态改为FINISH,更新时间
        po.setPurchaseUpdateTime(new Date());
        po.setPurchaseState(PurchaseOrderStatus.FINISH.name());
        return purchaseOrderDao.finishPurchase(po);
    }
    private int addInventory(PurchaseOrder po, Integer inventoryId) {
        int amount = po.getPurchaseAmount();
        InventoryParam inventoryParam = new InventoryParam();
        inventoryParam.setInventoryId(inventoryId);
        inventoryParam.setPhysicalInventoryAdd(amount);
        inventoryParam.setRealInventoryAdd(amount);
        return inventoryDao.updateInventory(inventoryParam);
    }
    private Integer getAvailableInventoryId(PurchaseOrder po) {
        int skuId = po.getSkuId();
        int  warehouseId = po.getWarehouseId();
        Inventory inventory = new Inventory();
        inventory.setSkuId(skuId);
        inventory.setWarehouseId(warehouseId);
        return inventoryDao.selectInventoryId(inventory);
    }
    private String check(PurchaseOrder po) {
        //验证单号是否存在
        //如果检查状态是否是INIT
        if(po==null){
            return "没有此单号，请重新输入";
        }
        if(PurchaseOrderStatus.FINISH.name().equals(po.getPurchaseState())){
            return  "订单已经完成，无需重复提交";
        }else if (PurchaseOrderStatus.INVALID.name().equals(po.getPurchaseState())){
            return  "订单已经作废，无需重复提交";
        }
        return null;
    }


}
