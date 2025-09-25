package main.java.com.hsh.controller;

import main.java.com.hsh.dao.WarehouseDao;
import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.WarehouseService;
import main.java.com.hsh.service.seviceImpl.WarehouseServiceImpl;
import main.java.com.hsh.view.WarehouseView;

import java.util.List;

public class WarehouseController {

    // 싱글톤 인스턴스를 담을 static 변수
    // (클래스 내부에 자기 자신을 담을 정적 변수 선언)
    private static WarehouseController instance;

    private static String type;

    private WarehouseService warehouseService;
    private WarehouseView warehouseView;

    // private 생성자 -> 외부에서 new WarehouseController() 객체 생성이 불가능
    private WarehouseController() {
        // 싱글톤 서비스 구현체 가져오기
        this.warehouseService = WarehouseServiceImpl.getInstance();
        this.warehouseView = new WarehouseView();
    }

    public void init(WarehouseService warehouseService, WarehouseView warehouseView) {
        this.warehouseService = warehouseService;
        this.warehouseView = warehouseView;
    }

    // 전역 접근 지점 (항상 같은 인스턴스를 반환)
    // 필요할 떄 인스턴스를 생성하고, 이미 있으면 같은 인스턴스 반환
    public static WarehouseController getInstance() {
        if (instance == null) { // 없으면 생성
            instance = new WarehouseController();
        }
        return instance; // 이미 있으면 기존 객체 반환
    }

    public void listWarehouse() {
        List<WarehouseVo> warehouseList = warehouseService.getWarehouseList();
        warehouseView.printWarehouseList(warehouseList);
    }

    public void getWarehouseByType() {
        type = warehouseView.inputWarehouseType();
        List<WarehouseVo> warehouseList = warehouseService.searchByType(type);
        warehouseView.printWarehouseList(warehouseList);
    }


    /*
    public void getType(){
        type = warehouseView.inputWarehouseType();
    }
    */

}
