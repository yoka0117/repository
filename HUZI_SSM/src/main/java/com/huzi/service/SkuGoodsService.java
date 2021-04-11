package com.huzi.service;

import com.huzi.domain.product.SkuGoods;

import java.util.List;

public interface SkuGoodsService {

    //1查看所有商品 sku + goods 详情 ***
    List<SkuGoods>  selectAll();
}
