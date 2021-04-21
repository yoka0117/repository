package com.huzi.dao.userOrder;
import com.huzi.domain.userOrder.Sale;

import java.util.List;

public interface SaleDao {

    //添加销售表
    int addSale(Sale sale);

    //查找Sale表
    List<Sale> selectSaleByOrderId(Sale sale);

    //改变销售表状态
    int updateSaleState(Sale  sale);

    //出库的时候，查看是否与订单相同
    Sale selectSaleBySkuIdAmount(Sale sale);
}
