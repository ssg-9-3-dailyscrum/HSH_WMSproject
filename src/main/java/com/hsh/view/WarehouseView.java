package main.java.com.hsh.view;

import main.java.com.hsh.domain.vo.WarehouseVo;

import java.util.List;

public class WarehouseView {
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
}
