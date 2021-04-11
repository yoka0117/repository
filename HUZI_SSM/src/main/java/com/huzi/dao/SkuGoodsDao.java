package com.huzi.dao;

import com.huzi.domain.product.SkuGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuGoodsDao {

    //1查看所有商品 sku + goods 详情 ***
    List<SkuGoods> selectSKU_GoodsAll();


    //
    Integer selectByGoodsId(@Param("goodsId") Integer goodsId);


}
