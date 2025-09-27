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

    // 창고 등록
    public boolean addWarehouse(int adminId,
                                String warehouseName,
                                String warehouseTypeChoice,
                                String warehouseCapacityStr,
                                String warehouseStatus,
                                String warehouseAddress) {

        // 창고 유형 검증/변환
        String warehouseType;
        switch (warehouseTypeChoice) {
            case "1" -> warehouseType = "대형창고";
            case "2" -> warehouseType = "중형창고";
            default -> {
                System.out.println(":: 창고 유형 선택이 잘못되었습니다. ::");
                return false;
            }
        }

        int capacity;
        // capacity 검증
        try {
            capacity = Integer.parseInt(warehouseCapacityStr);
            if (capacity <= 0) {
                System.out.println(":: 용량은 1 이상의 숫자여야 합니다. ::");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(":: 용량은 숫자만 입력 가능합니다. ::");
            return false;
        }

        // status 검증
        warehouseStatus = warehouseStatus.toUpperCase();
        if (!"Y".equals(warehouseStatus) && !"N".equals(warehouseStatus)) {
            System.out.println(":: 상태 값은 Y 또는 N만 입력 가능합니다. ::");
            return false;
        }

        return warehouseService.registerWarehouse(
                adminId,
                warehouseName,
                warehouseType,
                capacity,
                warehouseStatus,
                warehouseAddress);
    }

    public boolean updateWarehouseStatus(int warehouseId) {
        // ID 검증 정도만 컨트롤러에서 수행
        if (warehouseId <= 0) {
            System.out.println(":: 잘못된 창고 ID입니다. ::");
            return false;
        }
        // 서비스는 자동으로 Y ↔ N 토글
        return warehouseService.changeWarehouseStatus(warehouseId);
    }

    public List<WarehouseVo> listWarehouse() {
        return warehouseService.getWarehouseList();
    }

    public List<WarehouseVo> searchWarehouseByType(String typeChoice) {
        String type;
        switch (typeChoice) {
            case "1" -> type = "대형창고";
            case "2" -> type = "중형창고";
            default -> {
                System.out.println(":: 창고 유형 선택이 잘못되었습니다. ::");
                return null;
            }
        }
        return warehouseService.searchByType(type);
    }

    public List<WarehouseVo> getWarehouseByName(String name) {
        return warehouseService.searchByName(name);
    }

    public List<WarehouseVo> getWarehouseByLocation(String location) {
        return warehouseService.searchByLocation(location);
    }


}

