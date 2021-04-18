package com.huzi.dao.userOrder;
import com.huzi.domain.userOrder.Sale;

import java.util.List;

public interface SaleDao {

    //添加销售表
    int addSale(Sale sale);

    //查找Sale表
    List<Sale> selectSale(Sale sale);

}
