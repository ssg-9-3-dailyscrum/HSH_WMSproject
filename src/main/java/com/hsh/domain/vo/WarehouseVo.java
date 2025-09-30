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

}
