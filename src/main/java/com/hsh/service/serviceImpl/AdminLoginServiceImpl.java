package main.java.com.hsh.service.serviceImpl;

import main.java.com.hsh.Dao.AdminLoginDao;
import main.java.com.hsh.Dao.daoImpl.AdminLoginDaoImpl;
import main.java.com.hsh.service.AdminLoginService;

public class AdminLoginServiceImpl implements AdminLoginService {

    // 싱글톤 인스턴스
    private static AdminLoginServiceImpl instance = new AdminLoginServiceImpl();

    private AdminLoginDao adminLoginDao;

    // private 생성자로 외부에서 new로 새로운 객체 생성 불가능하도록.
    // 기본 생성자에서 DAO 초기화
    private AdminLoginServiceImpl() {
        this.adminLoginDao = new AdminLoginDaoImpl();
    }

    public static AdminLoginServiceImpl getInstance() {
        return instance;
    }


    @Override
    public boolean adminLogin(String adminLoginId, String adminLoginPwd) {
        boolean result = adminLoginDao.adminLogin(adminLoginId, adminLoginPwd);
        return result;
    }
}
