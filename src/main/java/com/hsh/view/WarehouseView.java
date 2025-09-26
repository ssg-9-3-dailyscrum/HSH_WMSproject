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

    public void runListWarehouse(){
        List<WarehouseVo> warehouseList = warehouseController.listWarehouse();
        printWarehouseList(warehouseList);
    }

    public void printWarehouseByType() {
        String type = inputWarehouseType();                         // 입력
        List<WarehouseVo> warehouseList = warehouseController.getWarehouseByType(type); // Controller 호출
        printWarehouseList(warehouseList);                          // 출력
    }

    // 매개변수 받아서 조회 1. 사용자로부터 창고 유형 입력 받기
    public String inputWarehouseType() {
        System.out.print("검색할 창고 유형을 입력하세요: ");
        try {
            return reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // 입력 오류 시 빈 문자열 반환
        }
    }

    public void printWarehouseByLocation() {
        String location = inputWarehouseLocation();                         // 입력
        List<WarehouseVo> warehouseList = warehouseController.getWarehouseByLocation(location); // Controller 호출
        printWarehouseList(warehouseList);                          // 출력
    }

    public String inputWarehouseLocation() {
        System.out.print("검색할 창고 주소를 입력하세요: ");
        try {
            return reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // 입력 오류 시 빈 문자열 반환
        }
    }

    public void printWarehouseByName() {
        String name = inputWarehouseName();                         // 입력
        List<WarehouseVo> warehouseList = warehouseController.getWarehouseByName(name); // Controller 호출
        printWarehouseList(warehouseList);                          // 출력
    }

    public String inputWarehouseName() {
        System.out.print("검색할 창고 이름을 입력하세요: ");
        try {
            return reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // 입력 오류 시 빈 문자열 반환
        }
    }

    public int updateWarehouseStatus() {
        System.out.print("운용 상태를 수정할 창고의 번호를 입력하세요: ");
        try {
            return Integer.parseInt(reader.readLine().trim());
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("잘못 입력하셨습니다.");
            return 0; // 입력 오류 시 0 반환
        } catch (IOException e) {
            e.printStackTrace();
            return 0; // 입력 오류 시 0 반환
        }
    }

    public void printWarehouseList(List<WarehouseVo> warehouseList) {
//        String format1 = "| %-3s | %-5s | %-10s | %-18s | %-7s | %-5s | %-3s | %-10s | %-50s |%n";
        String format1 = "| %-3s | %-18s | %-7s | %-7s | %-5s | %-3s | %-10s | %-50s |%n";
//        String format2 = "| %-3s | %-7s | %-10s | %-15s | %-6s | %-6s | %-4s | %-12s | %-40s |%n";
        String format2 = "| %-3s | %-15s | %-7s | %-7s | %6s | %4s | %12s | %-39s |%n";
//        String line = "+-----+---------+--------------+----------------------+----------+--------+-------+--------------+-----------------------------------------------------+";
        String line = "+-----+----------------------+-----------+-----------+--------+------+--------------+-----------------------------------------------------+";

        System.out.println();
        System.out.println("================================================================= 창고 목록 =================================================================");
        System.out.println(line);
//        System.out.printf(format1, "ID", "관리자ID", "관리자명", "창고명", "유형", "용량", "상태", "등록일", "주소");
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
