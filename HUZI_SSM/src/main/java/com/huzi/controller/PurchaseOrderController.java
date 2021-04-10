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


    //2查询采购单 **
    @RequestMapping("/selectPurchase.do")
    public ModelAndView selectPurchase(){
        ModelAndView mv = new ModelAndView();
        List<PurchaseOrder> list =  purchaseOrderService.selectPurchaseOrder();

        mv.addObject("result",list);
        mv.setViewName("result");
        return  mv;
    }

    //3按订单号查询采购单 **
    @RequestMapping("/selectPurchaseById.do")
    public ModelAndView selectPurchaseById(Integer purchaseId){
        ModelAndView mv = new ModelAndView();
        PurchaseOrder purchaseOrder =  purchaseOrderService.selectPurchaseOrderById(purchaseId);

        mv.addObject("result",purchaseOrder);
        mv.setViewName("result");
        return  mv;
    }


    //4查询订单状态 **
    @RequestMapping("/checkPurchaseState.do")
    public ModelAndView checkPurchaseState(Integer purchaseId){
        ModelAndView mv = new ModelAndView();
        String tip = "完单";
        String state = purchaseOrderService.checkState(purchaseId);
        if (PurchaseOrderStatus.INIT.equals(state)){
            tip = "没有完单";
        }
        mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;
    }


    //5完成订单FINISH**
    @RequestMapping("/finishPurchaseState.do")
    public ModelAndView finishPurchaseState(Integer purchaseId){
        ModelAndView mv = new ModelAndView();
        String tip = null;

        int num = purchaseOrderService.finishPurchaseState(purchaseId);
        if(num > 0){
            tip = "已设置成完结";
        }

        mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;
    }






}
