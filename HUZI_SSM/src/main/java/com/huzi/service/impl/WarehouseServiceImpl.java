package com.huzi.service.impl;

import com.huzi.dao.WarehouseRegionIdDao;
import com.huzi.domain.Warehouse.WarehouseRegionId;
import com.huzi.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRegionIdDao warehouseRegionIdDao;

    @Override
    public WarehouseRegionId selectWarehouseRegionId(WarehouseRegionId warehouseRegionId) {
        return warehouseRegionIdDao.selectWarehouseRegionId(warehouseRegionId);
    }
}
