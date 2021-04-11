package com.huzi.service.impl;

import com.huzi.dao.SkuGoodsDao;
import com.huzi.domain.product.SkuGoods;
import com.huzi.service.SkuGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuGoodsServiceImpl implements SkuGoodsService {

    @Autowired
    private SkuGoodsDao sku_goodsDao;

    //1查看所有商品 sku + goods 详情 ***
    @Override
    public List<SkuGoods> selectAll() {
        return sku_goodsDao.selectSKU_GoodsAll();
    }
}
