package test;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.view.WarehouseView;
import main.java.com.hsh.view.menuView.WarehouseMenuView;

public class WarehouseTest {
    public static void main(String[] args) {
        WarehouseController controller = WarehouseController.getInstance();
        WarehouseMenuView warehouseUserMenuView = new WarehouseMenuView();
//        warehouseMenuView.warehouseMenu();
        warehouseUserMenuView.warehouseUserMenu();
    }
}
