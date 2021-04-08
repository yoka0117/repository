package com.huzi.domain.product;

public class SKU {
    private int skuId;
    private int skuSize;
    private String skuColor;
    private int goodsId;

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(int skuSize) {
        this.skuSize = skuSize;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
