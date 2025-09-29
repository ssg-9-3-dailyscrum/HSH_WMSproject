package main.java.com.hsh.view;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.domain.vo.WarehouseVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class WarehouseUserView {

    // 싱글톤 서비스 인스턴스
    private WarehouseController warehouseController = WarehouseController.getInstance();

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // 회원 창고 리스트 조회
    public void runListWarehouse(){
        List<WarehouseVo> warehouseList = warehouseController.listWarehouseByUser();
        printWarehouseList(warehouseList);
    }

    // 회원 창고 유형별 조회
    public void printWarehouseByType() {
        try {
            String typeChoice;
            while (true) {
                System.out.println("창고 유형 선택:");
                System.out.println("1. 대형창고");
                System.out.println("2. 중형창고");
                System.out.print("선택: ");
                typeChoice = reader.readLine().trim();
                if ("1".equals(typeChoice) || "2".equals(typeChoice)) break;
                System.out.println(":: 잘못된 입력입니다. 1 또는 2 선택 ::");
            }

            List<WarehouseVo> list = warehouseController.searchWarehouseByTypeByUser(typeChoice);
            if (list != null && !list.isEmpty()) printWarehouseList(list);
            else System.out.println(":: 검색 결과가 없습니다. ::");

        } catch (IOException e) {
            System.out.println(":: 입력 오류 발생 ::");
        }
    }

    // 창고 주소 검색 조회
    public void printWarehouseByLocation() {
        try {
            System.out.print("검색할 창고 주소 입력: ");
            String location = reader.readLine().trim();
            List<WarehouseVo> list = warehouseController.getWarehouseByLocationByUser(location);
            if (list != null && !list.isEmpty()) printWarehouseList(list);
            else System.out.println(":: 검색 결과가 없습니다. ::");
        } catch (IOException e) {
            System.out.println(":: 입력 오류 발생 ::");
        }
    }

    // 창고 이름 검색 조회
    public void printWarehouseByName() {
        try {
            System.out.print("검색할 창고 이름 입력: ");
            String name = reader.readLine().trim();
            List<WarehouseVo> list = warehouseController.getWarehouseByNameByUser(name);
            if (list != null && !list.isEmpty()) printWarehouseList(list);
            else System.out.println(":: 검색 결과가 없습니다. ::");
        } catch (IOException e) {
            System.out.println(":: 입력 오류 발생 ::");
        }
    }

    // 창고 리스트 출력
    public void printWarehouseList(List<WarehouseVo> warehouseList) {
        String format1 = "| %-3s | %-18s | %-7s | %-7s | %-5s | %-3s | %-10s | %-50s |%n";
        String format2 = "| %-3s | %-15s | %-7s | %-7s | %6s | %4s | %12s | %-39s |%n";
        String line = "+-----+----------------------+-----------+-----------+--------+------+--------------+-----------------------------------------------------+";

        System.out.println();
        System.out.println("================================================================= 창고 목록 =================================================================");
        System.out.println(line);
        System.out.printf(format1, "ID", "창고명", "관리자명", "유형", "용량", "상태", "등록일", "주소");
        System.out.println(line);

        for (WarehouseVo w : warehouseList) {
            System.out.printf(format2,
                    w.getWarehouseId(),
                    w.getWarehouseName(),
                    w.getAdminName(),
//                    w.getAdminId(),
                    w.getWarehouseType(),
                    w.getCapacity(),
                    w.getWarehouseStatus(),
                    w.getRegistrationDate(),
                    w.getAddress() == null ? "-" : w.getAddress()
            );
        }

        System.out.println(line);
        System.out.println();
    }
}
