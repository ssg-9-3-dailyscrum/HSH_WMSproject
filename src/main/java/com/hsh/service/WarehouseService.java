package main.java.com.hsh.service;

import main.java.com.hsh.domain.vo.WarehouseVo;

import java.util.List;

public interface WarehouseService {

    public List<WarehouseVo> getWarehouseList();

    public List<WarehouseVo> searchByType(String type);

    public List<WarehouseVo> searchByName(String name);

    public List<WarehouseVo> searchByLocation(String location);

    public List<WarehouseVo> getWarehouseListByUser();

    public List<WarehouseVo> searchByTypeByUser(String type);

    public List<WarehouseVo> searchByNameByUser(String name);

    public List<WarehouseVo> searchByLocationByUser(String location);

    public boolean changeWarehouseStatus(int warehouseId);

    boolean registerWarehouse(int adminId,
                              String warehouseName,
                              String warehouseType,
                              int warehouseCapacity,
                              String warehouseStatus,
                              String warehouseAddress);
}
