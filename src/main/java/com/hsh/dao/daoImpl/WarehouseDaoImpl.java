package main.java.com.hsh.dao.daoImpl;

import main.java.com.hsh.dao.WarehouseDao;
import main.java.com.hsh.domain.vo.WarehouseVo;
import main.java.com.hsh.util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDaoImpl implements WarehouseDao {

    @Override
    public boolean save(int adminId,
                        String warehouseName,
                        String warehouseType,
                        int warehouseCapacity,
                        String warehouseStatus,
                        String warehouseAddress) {
        String sql = "CALL usp_Warehouse_Insert(?,?,?,?,?,?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            call.setInt(1, adminId);
            call.setString(2, warehouseName);
            call.setString(3, warehouseType);
            call.setInt(4, warehouseCapacity);
            call.setString(5, warehouseStatus);
            call.setString(6, warehouseAddress);

            int result = call.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int updateStatus(int warehouseId) {
        String sql = "CALL usp_Warehouse_UpdateStatus(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ){
            call.setInt(1, warehouseId);
            int result = call.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<WarehouseVo> findByLocation(String location) {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();
        String sql = "CALL usp_warehouse_selectByLocation(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            // 파라미터 세팅
            call.setString(1, location);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    WarehouseVo warehousevo = new WarehouseVo();
                    warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                    warehousevo.setAdminId(rs.getInt("admin_id"));
                    warehousevo.setAdminName(rs.getString("admin_name"));
                    warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                    warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                    warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                    warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                    warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                    warehousevo.setAddress(rs.getString("address"));

                    warehouse.add(warehousevo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouse;
    }

    @Override
    public List<WarehouseVo> findByType(String type) {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();
        String sql = "CALL usp_warehouse_selectByType(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            // 파라미터 세팅
            call.setString(1, type);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    WarehouseVo warehousevo = new WarehouseVo();
                    warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                    warehousevo.setAdminId(rs.getInt("admin_id"));
                    warehousevo.setAdminName(rs.getString("admin_name"));
                    warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                    warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                    warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                    warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                    warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                    warehousevo.setAddress(rs.getString("address"));

                    warehouse.add(warehousevo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    @Override
    public List<WarehouseVo> findByName(String name) {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();

        String sql = "CALL usp_warehouse_selectByName(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            // 파라미터 세팅
            call.setString(1, name);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    WarehouseVo warehousevo = new WarehouseVo();
                    warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                    warehousevo.setAdminId(rs.getInt("admin_id"));
                    warehousevo.setAdminName(rs.getString("admin_name"));
                    warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                    warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                    warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                    warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                    warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                    warehousevo.setAddress(rs.getString("address"));

                    warehouse.add(warehousevo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    @Override
    public List<WarehouseVo> findAll() {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();

        String sql = "CALL usp_warehouse_selectAll()";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
                ResultSet rs = call.executeQuery();
        ) {
            while (rs.next()) {
                // 컬럼 이름은 DB 프로시저 SELECT 결과에 맞춰서 작성
                WarehouseVo warehousevo = new WarehouseVo();
                warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                warehousevo.setAdminId(rs.getInt("admin_id"));
                warehousevo.setAdminName(rs.getString("admin_name"));
                warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                warehousevo.setAddress(rs.getString("address"));

                warehouse.add(warehousevo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    @Override
    public List<WarehouseVo> findByLocationByUser(String location) {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();
        String sql = "CALL usp_warehouse_selectByLocation_ByUser(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            // 파라미터 세팅
            call.setString(1, location);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    WarehouseVo warehousevo = new WarehouseVo();
                    warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                    warehousevo.setAdminId(rs.getInt("admin_id"));
                    warehousevo.setAdminName(rs.getString("admin_name"));
                    warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                    warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                    warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                    warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                    warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                    warehousevo.setAddress(rs.getString("address"));

                    warehouse.add(warehousevo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouse;
    }

    @Override
    public List<WarehouseVo> findByTypeByUser(String type) {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();

        String sql = "CALL usp_warehouse_selectByType_ByUser(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            // 파라미터 세팅
            call.setString(1, type);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    WarehouseVo warehousevo = new WarehouseVo();
                    warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                    warehousevo.setAdminId(rs.getInt("admin_id"));
                    warehousevo.setAdminName(rs.getString("admin_name"));
                    warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                    warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                    warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                    warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                    warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                    warehousevo.setAddress(rs.getString("address"));

                    warehouse.add(warehousevo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    @Override
    public List<WarehouseVo> findByNameByUser(String name) {
        List<WarehouseVo> warehouse = new ArrayList<WarehouseVo>();

        String sql = "CALL usp_warehouse_selectByName_ByUser(?)";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
        ) {
            // 파라미터 세팅
            call.setString(1, name);

            try(ResultSet rs = call.executeQuery()) {
                while(rs.next()) {
                    WarehouseVo warehousevo = new WarehouseVo();
                    warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                    warehousevo.setAdminId(rs.getInt("admin_id"));
                    warehousevo.setAdminName(rs.getString("admin_name"));
                    warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                    warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                    warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                    warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                    warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                    warehousevo.setAddress(rs.getString("address"));

                    warehouse.add(warehousevo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    @Override
    public List<WarehouseVo> findAllByUser() {
        List<WarehouseVo> warehouse = new ArrayList<>();

        String sql = "CALL usp_warehouse_selectAll_ByUser()";

        try(
                Connection conn  = DBUtil.getConnection();
                CallableStatement call = conn.prepareCall(sql);
                ResultSet rs = call.executeQuery();
        ) {
            while (rs.next()) {
                // 컬럼 이름은 DB 프로시저 SELECT 결과에 맞춰서 작성
                WarehouseVo warehousevo = new WarehouseVo();
                warehousevo.setWarehouseId(rs.getInt("warehouse_id"));
                warehousevo.setAdminId(rs.getInt("admin_id"));
                warehousevo.setAdminName(rs.getString("admin_name"));
                warehousevo.setWarehouseName(rs.getString("warehouse_name"));
                warehousevo.setWarehouseType(rs.getString("warehouse_type"));
                warehousevo.setCapacity(rs.getInt("warehouse_capacity"));
                warehousevo.setWarehouseStatus(rs.getString("warehouse_status"));
                warehousevo.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                warehousevo.setLastUpdateDate(rs.getTimestamp("latest_update_date").toLocalDateTime());
                warehousevo.setAddress(rs.getString("address"));

                warehouse.add(warehousevo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

}
