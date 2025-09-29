package main.java.com.hsh.service.seviceImpl;

import main.java.com.hsh.dao.WarehouseDao;
import main.java.com.hsh.dao.daoImpl.WarehouseDaoImpl;
import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.service.WarehouseService;

import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    // 싱글톤 인스턴스
    private static WarehouseServiceImpl instance;

    private WarehouseDao warehouseDao;

    // private 생성자로 외부에서 new로 새로운 객체 생성 불가능하도록.
    // 기본 생성자에서 DAO 초기화
    private WarehouseServiceImpl() {
        this.warehouseDao = new WarehouseDaoImpl(); // 기본 DAO 초기화
    }

    // 테스트용/커스텀 DAO 주입 가능 생성자
//    public WarehouseServiceImpl(WarehouseDao warehouseDao) {
//        this.warehouseDao = warehouseDao;
//    }

    // 싱글톤 전역 접근 지점 getInstance는 public으로
    public static WarehouseServiceImpl getInstance() {
        if (instance == null) {
            instance = new WarehouseServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean registerWarehouse(int adminId,
                                     String warehouseName,
                                     String warehouseType,
                                     int warehouseCapacity,
                                     String warehouseStatus,
                                     String warehouseAddress) {
        return warehouseDao.save(
                adminId,
                warehouseName,
                warehouseType,
                warehouseCapacity,
                warehouseStatus,
                warehouseAddress
        );
    }

    @Override
    public boolean changeWarehouseStatus(int warehouseId) {
        // DAO에서 프로시저 호출 → 자동 토글
        int result = warehouseDao.updateStatus(warehouseId);
        return result > 0; // 성공 여부 반환
    }

    @Override
    public List<WarehouseVo> getWarehouseList() {
        return warehouseDao.findAll();
    }

    @Override
    public List<WarehouseVo> searchByType(String type) {
        return warehouseDao.findByType(type);
    }

    @Override
    public List<WarehouseVo> searchByName(String name) {
        return warehouseDao.findByName(name);
    }

    @Override
    public List<WarehouseVo> searchByLocation(String location) {
        return warehouseDao.findByLocation(location);
    }


    // 회원 메뉴
    @Override
    public List<WarehouseVo> getWarehouseListByUser() {
        return warehouseDao.findAllByUser();
    }

    @Override
    public List<WarehouseVo> searchByTypeByUser(String type) {
        return warehouseDao.findByTypeByUser(type);
    }

    @Override
    public List<WarehouseVo> searchByNameByUser(String name) {
        return warehouseDao.findByNameByUser(name);
    }

    @Override
    public List<WarehouseVo> searchByLocationByUser(String location) {
        return warehouseDao.findByLocationByUser(location);
    }

}
