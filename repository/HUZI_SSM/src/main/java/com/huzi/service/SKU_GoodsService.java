package com.huzi.service;

import com.huzi.domain.product.SKU_Goods;

import java.util.List;

public interface SKU_GoodsService {

    //查看当前所有商品及详情
    List<SKU_Goods>  selectAll();
}
