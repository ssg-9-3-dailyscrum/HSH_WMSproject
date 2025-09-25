package test;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.service.WarehouseService;
import main.java.com.hsh.service.seviceImpl.WarehouseServiceImpl;
import main.java.com.hsh.view.WarehouseView;

public class WarehouseTest {
    public static void main(String[] args) {
        WarehouseController controller = WarehouseController.getInstance();
//        controller.listWarehouse();
        controller.getWarehouseByType();
    }
}
