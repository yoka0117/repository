package com.huzi.controller;

import com.huzi.domain.product.Goods;
import com.huzi.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //1添加Goods商品***
    @RequestMapping("/insertGoods.do" )
        public ModelAndView insertGoods(Goods goods){
        ModelAndView mv = new ModelAndView();
        String tip = "失败";
        if(goodsService.insertGoods(goods) > 0 ){
            tip = "成功";
        }
        mv.addObject("result",tip);
        mv.setViewName("result");
        return  mv;

    }

}
