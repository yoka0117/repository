package com.huzi.dao;

import com.huzi.domain.product.SKU;

import java.util.List;

public interface SkuDao {


    //1新增sku***
    int insertSKU(SKU sku);

    //查找sku
    List<SKU> selectSKU();


    //通过skuid查找SKU【验证sku是否存在】
    SKU selectSkuById(Integer skuId);
}
