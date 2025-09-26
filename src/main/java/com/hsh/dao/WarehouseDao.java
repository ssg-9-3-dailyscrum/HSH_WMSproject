package main.java.com.hsh.dao;

import main.java.com.hsh.domain.vo.WarehouseVo;

import java.util.List;

public interface WarehouseDao {

    void save();

    int updateStatus(int warehouseId);

    List<WarehouseVo> findByLocation(String location);

    List<WarehouseVo> findByName(String name);

    List<WarehouseVo> findByType(String type);

    List<WarehouseVo> findAll();

}
