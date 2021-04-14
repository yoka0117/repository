package com.huzi.controller;

import com.huzi.domain.product.SKU;
import com.huzi.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/SKU")
public class SkuController {


@Autowired
private SkuService skuService;

        //1新增sku***ok
        @RequestMapping("/insertSKU.do")
        public ModelAndView insertSKU(SKU sku){
            ModelAndView mv = new ModelAndView();
            String tip = "失败";
            if(sku.getGoodsId() != 0 && sku.getSkuColor() !=null && sku.getSkuSize() != 0){
                if(skuService.insertSKU(sku) > 0){
                    tip = "成功";
                }
            }
            mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;
    }
}
