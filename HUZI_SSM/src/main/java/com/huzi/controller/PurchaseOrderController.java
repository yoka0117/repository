package com.huzi.controller;
import com.huzi.common.PurchaseOrderStatus;
import com.huzi.domain.purchase.PurchaseOrder;
import com.huzi.service.PurchaseOrderService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    //1新建采购订单
    @RequestMapping("/insertPurchase.do")
    public ModelAndView insertOrder(PurchaseOrder purchaseOrder){
        ModelAndView mv = new ModelAndView();
        int num = 0;
        String tip = "失败";
        //todo 验证非空 数量>0
        if(purchaseOrder.getSkuId() !=0
                && purchaseOrder.getPurchaseAmount() > 0
                && purchaseOrder.getWarehouseId() != 0 ) {
            num = purchaseOrderService.insertOrder(purchaseOrder);
            if (num > 0){
                tip = "成功";
            }
        }

        mv.addObject("result",tip);
        mv.setViewName("result");
        return  mv;
    }


    //2查询采购单列表 **
    @RequestMapping("/selectPurchase.do")
    public ModelAndView selectPurchase(){
        ModelAndView mv = new ModelAndView();
        List<PurchaseOrder> list =  purchaseOrderService.selectPurchaseOrder();

        mv.addObject("result",list);
        mv.setViewName("result");
        return  mv;
    }

    /**3完结采购单：
     * （1）查询是否存在单号
     * （2）检查订单是否完结
     * （3）更改订单状态、更新时间
     * （4）库存增加
     *              查skuid+仓库id：    有，更新。          没有，新增
    **/
    @RequestMapping("/finishPurchaseState.do")
    public ModelAndView finishPurchaseState(PurchaseOrder purchaseOrder){
        ModelAndView mv = new ModelAndView();

        String result = purchaseOrderService.finishPurchaseState(purchaseOrder);


        mv.addObject("result",result);
        mv.setViewName("result");
        return  mv;
    }
}
