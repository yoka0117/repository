package com.huzi.service;

import com.huzi.domain.product.SKU;

import java.util.List;

public interface SkuService {


    //1新增sku***
    int insertSKU(SKU sku);


    //查找sku
    List<SKU> selectSKU();
}
