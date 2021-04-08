package com.huzi.service.impl;

import com.huzi.dao.SkuDao;
import com.huzi.domain.product.SKU;
import com.huzi.service.SKU_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SKU_ServiceImpl implements SKU_Service {

    @Autowired
    private SkuDao sku_dao;

    @Override
    public int insertSKU(SKU sku) {
        return sku_dao.insertSKU(sku);
    }

    @Override
    public List<SKU> selectSKU() {
        return sku_dao.selectSKU();
    }
}
