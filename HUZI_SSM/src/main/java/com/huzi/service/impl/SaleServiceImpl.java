package com.huzi.service.impl;

import com.huzi.common.PurchaseOrderStatus;
import com.huzi.dao.userOrder.OrderDao;
import com.huzi.dao.userOrder.SaleDao;
import com.huzi.domain.Warehouse.Region;
import com.huzi.domain.userOrder.Order;
import com.huzi.domain.userOrder.Sale;
import com.huzi.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    public int addSale(List<Sale> saleList) {
        for (Sale sale : saleList) {
            //添加销售表
            int result = saleDao.addSale(sale);
            if(result == 0){
                return 0;
            }
        }
        return 1;
    }
}
