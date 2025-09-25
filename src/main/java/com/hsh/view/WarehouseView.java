package main.java.com.hsh.view;

import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.seviceImpl.WarehouseServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.io.IOException;


public class WarehouseView {

    // 싱글톤 서비스 인스턴스
    private WarehouseServiceImpl warehouseService = WarehouseServiceImpl.getInstance();

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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

    public void printWarehouseList(List<WarehouseVo> warehouseList) {
        String format = "| %-3s | %-8s | %-10s | %-20s | %-6s | %-6s | %-4s | %-12s | %-20s |%n";
        String line = "+-----+----------+------------+----------------------+--------+--------+------+--------------+----------------------+";

        System.out.println();
        System.out.println("========================================== 창고 목록 ==========================================");
        System.out.println(line);
        System.out.printf(format, "ID", "관리자ID", "관리자명", "창고명", "유형", "용량", "상태", "등록일", "주소");
        System.out.println(line);

        for (WarehouseVo w : warehouseList) {
            System.out.printf(format,
                    w.getWarehouseId(),
                    w.getAdminId(),
                    w.getAdminName(),
                    w.getWarehouseName(),
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

    /*
    public void runSearchByType() {
        String type = inputWarehouseType();                 // 사용자 입력
        List<WarehouseVo> warehouseList = warehouseService.searchByType(type);  // 서비스 호출
        printWarehouseList(warehouseList);                 // 출력
    }
    */

}
