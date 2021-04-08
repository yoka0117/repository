package com.huzi.controller;

import com.huzi.domain.product.Goods;
import com.huzi.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //添加
    @RequestMapping("/insertGoods")
    public boolean insertGoods(Goods goods){
      boolean tip = false;

        goodsService.insertGoods(goods);

        return tip;

    }

}
