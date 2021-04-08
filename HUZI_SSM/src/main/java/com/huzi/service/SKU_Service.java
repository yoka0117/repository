package com.huzi.service;

import com.huzi.domain.product.SKU;

import java.util.List;

public interface SKU_Service {


    //新增sku
    int insertSKU(SKU sku);


    //查找sku
    List<SKU> selectSKU();
}
