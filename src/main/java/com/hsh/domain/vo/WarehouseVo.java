package main.java.com.hsh.domain.vo;

import java.time.LocalDate;

import lombok.Data;
import main.java.com.hsh.library.LocalDateTime;

@Data
public class WarehouseVo {

    private int warehouseId;
    private int adminId;
    private String adminName;
    private String warehouseName;
    private String warehouseType;
    private int capacity;
    private String warehouseStatus;
    private LocalDate registrationDate;
    private java.time.LocalDateTime lastUpdateDate;
    private String address;

//    public WarehouseVo(int warehouseId, int adminId, String adminName, String warehouseName, String warehouseType, int warehouseCapacity, String warehouseStatus, LocalDate registraionDate, String address) {
//        this.warehouseId = warehouseId;
//        this.adminId = adminId;
//        this.adminName = adminName;
//        this.warehouseName = warehouseName;
//        this.warehouseType = warehouseType;
//        this.capacity = warehouseCapacity;
//        this.warehouseStatus = warehouseStatus;
//        this.registraionDate = registraionDate;
//        this.address = address;
//    }
}
