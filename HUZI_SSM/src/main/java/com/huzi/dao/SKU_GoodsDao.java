package com.huzi.dao;

import com.huzi.domain.product.SKU_Goods;

import java.util.List;

public interface SKU_GoodsDao {

    //查找所有商品及详情
    List<SKU_Goods> selectSKU_GoodsAll();
}
