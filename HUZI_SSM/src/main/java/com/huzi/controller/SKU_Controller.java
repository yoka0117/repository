package com.huzi.controller;

import com.huzi.domain.product.SKU;
import com.huzi.service.SKU_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/SKU")
public class SKU_Controller {


@Autowired
private SKU_Service sku_service;


    @RequestMapping("/insertSKU")
    public boolean insertSKU(SKU sku){
        boolean tip = false;
        if(sku.getGoodsId() == 0 || sku.getSkuColor() ==null || sku.getSkuSize() == 0){
            return  tip;
        }
        if(sku_service.insertSKU(sku) > 0){
            tip = true;
        }
        return tip;
    }
}
