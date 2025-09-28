package main.java.com.hsh.dao;

import main.java.com.hsh.domain.vo.WarehouseVo;

import java.util.List;

public interface WarehouseDao {

    boolean save(int adminId,
                 String warehouseName,
                 String warehouseType,
                 int warehouseCapacity,
                 String warehouseStatus,
                 String warehouseAddress);

    int updateStatus(int warehouseId);

    List<WarehouseVo> findByLocation(String location);

    List<WarehouseVo> findByName(String name);

    List<WarehouseVo> findByType(String type);

    List<WarehouseVo> findAll();

    List<WarehouseVo> findByLocationByUser(String location);

    List<WarehouseVo> findByNameByUser(String name);

    List<WarehouseVo> findByTypeByUser(String type);

    List<WarehouseVo> findAllByUser();

}
