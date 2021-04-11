package com.huzi.controller;

import com.huzi.domain.product.SkuGoods;
import com.huzi.service.SkuGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sku_goods")
public class SkuGoodsController {

    @Autowired
    private SkuGoodsService skuGoodsService;

        //1查看所有商品 sku + goods 详情 ***
    @RequestMapping("/selectAll.do")
    public ModelAndView selectAll(){

        ModelAndView mv = new ModelAndView();

        List<SkuGoods>  list = skuGoodsService.selectAll();

        mv.addObject("result",list);
        mv.setViewName("result");

        return mv;
    }
}
