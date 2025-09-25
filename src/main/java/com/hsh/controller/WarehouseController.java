package main.java.com.hsh.controller;

import main.java.com.hsh.dao.WarehouseDao;
import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.WarehouseService;
import main.java.com.hsh.service.seviceImpl.WarehouseServiceImpl;
import main.java.com.hsh.view.WarehouseView;

import java.util.List;

public class WarehouseController {

    private WarehouseService warehouseService;
    private WarehouseView warehouseView;

    public WarehouseController() {
        this.warehouseService = new WarehouseServiceImpl();
    }

    public WarehouseController(WarehouseService warehouseService, WarehouseView warehouseView) {
        this.warehouseService = warehouseService;
        this.warehouseView = warehouseView;
    }

    public void listWarehouse() {
        List<WarehouseVo> warehouseList = warehouseService.getWarehouseList();
        warehouseView.printWarehouseList(warehouseList);
    }
}
