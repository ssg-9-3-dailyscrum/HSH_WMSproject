package main.java.com.hsh.service.serviceImpl;

import main.java.com.hsh.dao.AdminLoginDao;
import main.java.com.hsh.dao.daoImpl.AdminLoginDaoImpl;
import main.java.com.hsh.domain.vo.AdminVo;
import main.java.com.hsh.service.AdminLoginService;
import main.java.com.hsh.session.AdminSession;

import java.util.List;

public class AdminLoginServiceImpl implements AdminLoginService {

    private static final AdminLoginServiceImpl instance = new AdminLoginServiceImpl();
    private final AdminLoginDao adminLoginDao;

    private AdminLoginServiceImpl() {
        this.adminLoginDao = new AdminLoginDaoImpl();
    }

    public static AdminLoginServiceImpl getInstance() {
        return instance;
    }

    @Override
    public AdminVo adminLogin(String adminLoginId, String adminLoginPwd) {
        AdminVo admin = adminLoginDao.adminLogin(adminLoginId, adminLoginPwd);
        if (admin != null) {
            AdminSession.getInstance().login(admin.getAdminId(), admin.getRole(), admin.getAdminName());
        }
        return admin;
    }

    @Override
    public int createAdmin(AdminVo newAdmin) {
        return adminLoginDao.insertAdmin(newAdmin);
    }

    @Override
    public int updateAdmin(AdminVo updatedAdmin) {
        return adminLoginDao.updateAdmin(updatedAdmin);
    }

    @Override
    public List<AdminVo> selectAllExceptSuperAdmin() {
        return ((AdminLoginDaoImpl) adminLoginDao).selectAllExceptSuperAdmin();
    }

    @Override
    public AdminVo getAdminById(Integer adminId) {
        return adminLoginDao.getAdminById(adminId);
    }

    @Override
    public int deactivateAdmin(String adminLoginId) {
        return adminLoginDao.deactivateAdmin(adminLoginId);
    }
}
