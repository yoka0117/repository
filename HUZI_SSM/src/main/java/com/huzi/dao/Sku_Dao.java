package com.huzi.dao;

import com.huzi.domain.product.SKU;

import java.util.List;

public interface Sku_Dao {


    //新增sku
    int insertSKU(SKU sku);

    //查找sku
    List<SKU> selectSKU();

    SKU selectSkuById(Integer skuId);
}
