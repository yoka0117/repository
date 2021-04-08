package com.huzi.service.impl;

import com.huzi.dao.GoodsDao;
import com.huzi.domain.product.Goods;
import com.huzi.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public int insertGoods(Goods goods) {
        return goodsDao.insertGoods(goods);
    }
}
