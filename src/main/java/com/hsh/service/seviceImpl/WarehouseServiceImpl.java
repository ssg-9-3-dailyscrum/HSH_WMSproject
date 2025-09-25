package main.java.com.hsh.service.seviceImpl;

import main.java.com.hsh.dao.WarehouseDao;
import main.java.com.hsh.dao.daoImpl.WarehouseDaoImpl;
import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.WarehouseService;

import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private WarehouseDao warehouseDao;

    // 기본 생성자에서 DAO 초기화
    public WarehouseServiceImpl() {
        this.warehouseDao = new WarehouseDaoImpl();
    }

    // DAO 주입 생성자
    public WarehouseServiceImpl(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    @Override
    public List<WarehouseVo> getWarehouseList() {
        return warehouseDao.findAll();
    }
}
