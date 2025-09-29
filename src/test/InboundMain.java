package test;

import main.java.com.hsh.view.menuView.InboundMenuView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InboundMain {
    public static void main(String[] args) {
        InboundMenuView menu = new InboundMenuView();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("테스트 사용자 권한을 입력하세요:");
            System.out.println("1. 총관리자");
            System.out.println("2. 창고관리자");
            System.out.println("3. 회원");
            System.out.print("선택: ");
            int choice = Integer.parseInt(reader.readLine().trim());

            String role = switch (choice) {
                case 1 -> "총관리자";
                case 2 -> "창고관리자";
                case 3 -> "회원";
                default -> "회원";
            };

            System.out.print("사용자 ID 입력 (테스트용): ");
            int userId = Integer.parseInt(reader.readLine().trim());

            // 메뉴 실행
            menu.inboundMenu(role, userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}