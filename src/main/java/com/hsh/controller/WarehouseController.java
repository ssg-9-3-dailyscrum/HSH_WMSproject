package main.java.com.hsh.controller;

import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.WarehouseService;
import main.java.com.hsh.service.serviceImpl.WarehouseServiceImpl;

import java.util.List;

public class WarehouseController {

    // 싱글톤 인스턴스를 담을 static 변수
    // (클래스 내부에 자기 자신을 담을 정적 변수 선언)
    // Eager Singletone 방식
    private static WarehouseController instance = new WarehouseController();
    private WarehouseService warehouseService;

    // private 생성자 -> 외부에서 new WarehouseController() 객체 생성이 불가능
    private WarehouseController() {
        // 싱글톤 서비스 구현체 가져오기
        this.warehouseService = WarehouseServiceImpl.getInstance();
    }

    // 전역 접근 지점 (항상 같은 인스턴스를 반환)
    public static WarehouseController getInstance() {
        return instance; // 이미 있으면 기존 객체 반환
    }

    public List<WarehouseVo> listWarehouse() {
        return warehouseService.getWarehouseList();
    }

    public List<WarehouseVo> getWarehouseByType(String type) {
        return warehouseService.searchByType(type);
    }

    public List<WarehouseVo> getWarehouseByName(String name) {
        return warehouseService.searchByName(name);
    }

    public List<WarehouseVo> getWarehouseByLocation(String location) {
        return warehouseService.searchByLocation(location);
    }

//    public int updateWarehouseStatus() {
//        warehouseService.searchByLocation(warehouseLocation);
//    }

}
