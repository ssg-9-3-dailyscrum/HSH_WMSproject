package main.java.com.hsh.domain.dto.response;

import lombok.Data;

@Data
public class WarehouseStatusResponse {
    private int warehouseId; // 창고ID
    private String warehouseName; // 창고명
    private String warehouseType; // 창고종류
    private double warehouseCapacity; // 수용한도(m³)
    private double currentOccupancy; // 현재점유량(m³)
    private double remainingCapacity; // 남은수용량(m³)
    private double occupancyRate; // 점유율(%)
    private String warehouseStatus; // 운용현황
    private int totalCount; // 총재고수량
}
