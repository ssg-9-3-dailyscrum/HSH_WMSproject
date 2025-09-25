package main.java.com.hsh.dao;

import main.java.com.hsh.domain.vo.WarehouseVo;

import java.util.List;

public interface WarehouseDao {

    void save();

    void updateStatus();

    void findByLocation();

    void findByName();

    void findByType();

    List<WarehouseVo> findAll();

}
