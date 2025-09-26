package main.java.com.hsh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("main.java.com.hsh.util.dbinfo");

        try{
            Class.forName(bundle.getString("driver"));
            /*
            * 2025-09-26 드라이버 loaded시에는 메시지 뜨지 않도록 수정 - 엄현석
            System.out.println(":: Driver has been loaded ::");
            */
        } catch (ClassNotFoundException e) {
            System.out.println(":: Driver not found ::");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    bundle.getString("url"),
                    bundle.getString("username"),
                    bundle.getString("password")
            );
        } catch (SQLException e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
        return null;
    }
}