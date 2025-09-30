package main.java.com.hsh;


import main.java.com.hsh.view.LoginTypeSelectView;

import java.io.IOException;

public class HshMain {
    public static void main(String[] args) throws IOException {
        LoginTypeSelectView start = new LoginTypeSelectView();
        start.inputMainMenu();
    }
}