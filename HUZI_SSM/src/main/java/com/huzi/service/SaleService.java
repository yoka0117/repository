package com.huzi.service;

import com.huzi.domain.userOrder.Sale;

import java.util.List;

public interface SaleService {


    //添加销售表
    int addSale(List<Sale> saleList);
}
