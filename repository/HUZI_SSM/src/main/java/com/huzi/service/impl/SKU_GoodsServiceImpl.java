package com.huzi.service.impl;

import com.huzi.dao.SKU_GoodsDao;
import com.huzi.domain.product.SKU_Goods;
import com.huzi.service.SKU_GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SKU_GoodsServiceImpl implements SKU_GoodsService {

    @Autowired
    private  SKU_GoodsDao sku_goodsDao;


    @Override
    public List<SKU_Goods> selectAll() {
        return sku_goodsDao.selectSKU_GoodsAll();
    }
}
