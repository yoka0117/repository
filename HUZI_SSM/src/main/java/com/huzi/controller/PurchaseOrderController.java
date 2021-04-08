package com.huzi.controller;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @RequestMapping("/insertPurchase.do")
    public boolean insertOrder(PurchaseOrder purchaseOrder){

        boolean tip = false;
        //todo 验证非空 数量>0
        if(purchaseOrder.getSku_Id() !=0 && purchaseOrder.getPurchase_Amount() > 0 && purchaseOrder.getWarehouse_Id() != 0 ) {
            int num = purchaseOrderService.insertOrder(purchaseOrder);
            if (num > 0){
                tip = true;
                return tip;
            }
        }
        return tip;
    }

}
