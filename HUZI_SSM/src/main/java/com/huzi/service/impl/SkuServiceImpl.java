package com.huzi.service.impl;

import com.huzi.dao.SkuDao;
import com.huzi.domain.product.SKU;
import com.huzi.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;


    //1新增sku***
    @Override
    public int insertSKU(SKU sku) {
        return skuDao.insertSKU(sku);
    }



    //查找所有sku
    @Override
    public List<SKU> selectSKU() {

        return skuDao.selectSKU();
    }
}
