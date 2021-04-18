package com.huzi.controller.userOrder;


import com.huzi.domain.Warehouse.WarehouseRegionId;
import com.huzi.domain.userOrder.Order;
import com.huzi.domain.userOrder.Sale;
import com.huzi.service.*;
import com.huzi.service.impl.OrderReserveSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Sale")
public class SaleController {

@Autowired
private  SaleService saleService;

@Autowired
private OrderService orderService;

@Autowired
private OrderReserveSerivce orderReserveSerivce;

@Autowired
private WarehouseService warehouseService;

    //添加销售表，订单表
    @RequestMapping("/addSale.do")
    public ModelAndView addSale(String skuId,String amount,Integer regionId,Integer userId,Integer shopId) {
        ModelAndView mv = new ModelAndView();
        String tip = "";
        //将拿到的数据，转换成String数组
        String[] skuIdList = skuId.split(",");
        String[] amountList = amount.split(",");
        //String[] shopIdList = shopId.split(",");
        //通过shopId和regionId找到合适的仓库Id
        WarehouseRegionId wrId = new WarehouseRegionId();
        wrId.setRegionId(regionId);
        wrId.setShopId(shopId);
        WarehouseRegionId warehouseRegionIds = warehouseService.selectWarehouseRegionId(wrId);
        //创建Order订单,并返回orderId
        Integer orderId = orderService.addOrder(warehouseRegionIds);
        if (orderId != 0) {
            //创建Sale对象
            //创建一个List对象装Sale对象
            List<Sale> saleList = new ArrayList<>();
            //给每个sale赋值
            for (int a = 0; a < skuIdList.length; a++) {
                Sale sale = new Sale();
                sale.setSkuId(Integer.parseInt(skuIdList[a]));
                sale.setAmount(Integer.parseInt(amountList[a]));
                sale.setShopId(shopId);
                sale.setOrderId(orderId);
                sale.setUserId(userId);
                saleList.add(sale);
            }

            //将List<Sale>丢给service
            int result = saleService.addSale(saleList);
            if (result == 1) {
                tip = "成功";
            } else {
                tip = "失败";
            }

        }
        mv.addObject("result", tip);
        mv.setViewName("result");
        return mv;
    }







    //预定库存
    @RequestMapping("/reserve.do")
    public ModelAndView reserve(Integer orderId){
        ModelAndView mv = new ModelAndView();
        String tip = null;
        int result = 0;
            Order order = new Order();
            order.setOrderId(orderId);
            result = orderReserveSerivce.reserveOne(order);

            if (result == 1){
            tip = "order不存在";
             }else if (result == 2){
                tip = "order已经为预定状态";
             }else if (result == 3){
                tip = "sale不存在";
            }else if (result == 4){
                tip = "库存不存在";
            }else if(result == 5 ){
                tip = "预定成功";
            }
        mv.addObject("result" , tip);
        mv.setViewName("result");
        return mv;

    }
}
