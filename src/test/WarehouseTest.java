package test;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.view.WarehouseView;

public class WarehouseTest {
    public static void main(String[] args) {
        WarehouseController controller = WarehouseController.getInstance();
        WarehouseView view = new WarehouseView();
//        view.runListWarehouse();
//        view.printWarehouseByType();
//        view.printWarehouseByLocation();
        view.printWarehouseByName();

    }
}
