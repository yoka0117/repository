package com.huzi.controller;

import com.huzi.domain.purchase.OrderDetails;
import com.huzi.domain.purchase.PurchaseOrder;

import com.huzi.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    // TODO: 2021/4/12 新增采购单（新）
    @RequestMapping("/insertPurchase.do")
    public ModelAndView insertPurchase(String skuId ,String warehouseId,String amount){
        ModelAndView mv  = new ModelAndView();
        String tip = "";
        //新建PurchaseOrder对象，在数据库中添加，并返回purchaseId
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        int num = purchaseOrderService.insertPurchase(purchaseOrder);

        if(num != 0){
            //将前端传入的String，转换成int数组，给对象赋值，传入创建订单明细表
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            String[] skuIdArray = skuId.split(",");
            String[] warehouseIdArray = warehouseId.split(",");
            String[] amountArray = amount.split(",");
            for(int i = 0 ; i < skuIdArray.length ; i++){
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setPurchaseId(num);
                orderDetails.setSkuId(Integer.parseInt(skuIdArray[i]));
                orderDetails.setWarehouseId(Integer.parseInt(warehouseIdArray[i]));
                orderDetails.setAmount(Integer.parseInt(amountArray[i]));
                orderDetailsList.add(orderDetails);
            }
            int result  =  purchaseOrderService.insertOrderDetails(orderDetailsList);

            if (result == 0){
                 tip = "创建明细表失败";
            } tip = "创建订单表/明细表成功";
        }else {
            tip = "创建订单表失败";
        }
        mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;

    }



    //查询采购单列表及详情
    @RequestMapping("/selectPurchase.do")
    public ModelAndView selectPurchase(Integer purchaseId){
        ModelAndView mv = new ModelAndView();
        PurchaseOrder purchaseOrder =  purchaseOrderService.selectPurchaseOrderAndDetails(purchaseId);
        mv.addObject("result",purchaseOrder);
        mv.setViewName("result");
        return  mv;
    }



    //作废订单
    @RequestMapping("/invalidPurchase.do")
    public ModelAndView invalidPurchase(Integer purchaseId){
        ModelAndView mv = new ModelAndView();
        String result = purchaseOrderService.invalidPurchase(purchaseId);
        mv.addObject("result",result);
        mv.setViewName("result");
        return mv;
    }



    /**3完结采购单：
     * （1）查询是否存在采购单号
     * （2）检查订单是否完结
     * （3）更改订单状态、更新时间
     * （4）库存增加
     *              查skuid+仓库id：    有，更新。          没有，新增
    **/
    @RequestMapping("/finishPurchase.do")
    public ModelAndView finishPurchase(OrderDetails orderDetails){
        ModelAndView mv = new ModelAndView();
        String result = purchaseOrderService.finishPurchase(orderDetails);
        mv.addObject("result",result);
        mv.setViewName("result");
        return  mv;
    }

}
