package main.java.com.hsh.view;

import main.java.com.hsh.controller.WarehouseController;
import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.seviceImpl.WarehouseServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.io.IOException;


public class WarehouseView {

    // 싱글톤 서비스 인스턴스
    private WarehouseController warehouseController = WarehouseController.getInstance();

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    
    // 창고 등록
    public void registerWarehouse() {
        try {
            System.out.println("============== 창고 등록 ==============");
            System.out.print("창고명: ");
            String name = reader.readLine().trim();
            String type;
            // type 검증
            while(true) {
                System.out.println("창고 유형을 선택하세요:");
                System.out.println("1. 대형창고");
                System.out.println("2. 중형창고");
                System.out.print("선택: ");
                String typeChoice = reader.readLine().trim();
                if("1".equals(typeChoice)) {
                    type = "대형창고";
                    break;
                } else if("2".equals(typeChoice)) {
                    type = "중형창고";
                    break;
                }
                System.out.println(":: 잘못된 입력입니다. 1 또는 2를 선택하세요. ::");
            }
            
            // capacity 검증
            int capacity;
            while (true) {
                try {
                    System.out.print("창고 용량: ");
                    capacity = Integer.parseInt(reader.readLine().trim());
                    if (capacity <= 0) {
                        System.out.println(":: 용량은 1 이상의 숫자여야 합니다. ::");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(":: 용량은 숫자만 입력 가능합니다. ::");
                }
            }

            // status 검증
            String status;
            while(true){
                System.out.print("창고 상태 (Y/N): ");
                status = reader.readLine().trim().toUpperCase();
                if("Y".equals(status) || "N".equals(status)){
                    break;
                }
                System.out.println(":: 상태 값은 Y 또는 N만 입력 가능합니다. ::");
            }
            System.out.print("창고 주소: ");
            String address = reader.readLine().trim();

            // 로그인한 관리자 ID를 세션에서 가져온다고 가정
//            int adminId = UserSession.getInstance().getLoggedInAdmin().getAdminId();
            int adminId = 1;

            boolean result = warehouseController.addWarehouse(
                    adminId, name, type, capacity, status, address
            );

            if(result){
                System.out.println(":: 성공적으로 창고가 등록되었습니다. ::");
            } else {
                System.out.println(":: 창고 등록에 실패하였습니다. 다시 시도해주세요. ::");
            }

        } catch (IOException e) {
            System.out.println(":: 입력 오류 발생: " + e.getMessage());
        }
    }


    // 창고 리스트 조회
    public void runListWarehouse(){
        List<WarehouseVo> warehouseList = warehouseController.listWarehouse();
        printWarehouseList(warehouseList);
    }

    // 창고 운용상태 수정, warehouseid만 입력받으면 자동으로 상태 수정
    public void updateWarehouseStatus() {
        try {
            System.out.print("운용 상태를 변경할 창고 번호를 입력하세요 (현재 Y면 N으로, N이면 Y로 자동 변경됩니다): ");
            int warehouseId = Integer.parseInt(reader.readLine().trim());

            boolean success = warehouseController.updateWarehouseStatus(warehouseId);
            if (success) System.out.println(":: 창고 상태 변경 성공! ::\n");
            else System.out.println(":: 창고 상태 변경 실패! ::\n");

        } catch (IOException | NumberFormatException e) {
            System.out.println(":: 잘못된 입력입니다. ::");
        }
    }

    // 창고 유형별 조회
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

            List<WarehouseVo> list = warehouseController.searchWarehouseByType(typeChoice);
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
            List<WarehouseVo> list = warehouseController.getWarehouseByLocation(location);
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
            List<WarehouseVo> list = warehouseController.getWarehouseByName(name);
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
