package com.huzi.dao;

import com.huzi.domain.product.SKU_Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SKU_GoodsDao {

    //查找所有商品及详情
    List<SKU_Goods> selectSKU_GoodsAll();


    Integer selectByGoodsId(@Param("goodsId") Integer goodsId);


}
