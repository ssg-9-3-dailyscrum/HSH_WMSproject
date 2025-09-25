package main.java.com.hsh.service;

import main.java.com.hsh.domain.vo.WarehouseVo;

import java.util.List;

public interface WarehouseService {

    public List<WarehouseVo> getWarehouseList();

    public List<WarehouseVo> searchByType(String type);

    public List<WarehouseVo> searchByName(String name);

    public List<WarehouseVo> searchByLocation(String location);
}
