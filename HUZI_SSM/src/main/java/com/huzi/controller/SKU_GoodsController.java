package com.huzi.controller;

import com.huzi.domain.product.SKU_Goods;
import com.huzi.service.SKU_GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sku_goods")
public class SKU_GoodsController {

    @Autowired
    private SKU_GoodsService sku_goodsService;


    @RequestMapping("/selectAll.do")
    public ModelAndView selectAll(){

        ModelAndView mv = new ModelAndView();

        List<SKU_Goods>  list = sku_goodsService.selectAll();

        mv.addObject("result",list);
        mv.setViewName("result");

        return mv;
    }
}
