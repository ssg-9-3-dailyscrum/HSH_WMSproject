package test;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.service.WarehouseService;
import main.java.com.hsh.service.seviceImpl.WarehouseServiceImpl;
import main.java.com.hsh.view.WarehouseView;

public class WarehouseTest {
    public static void main(String[] args) {
        WarehouseService warehouseService = new WarehouseServiceImpl();
        WarehouseView warehouseView = new WarehouseView();

        WarehouseController controller = new WarehouseController(warehouseService, warehouseView);
        controller.listWarehouse();
    }
}
