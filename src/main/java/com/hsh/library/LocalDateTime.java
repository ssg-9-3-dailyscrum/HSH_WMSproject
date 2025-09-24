package main.java.com.hsh.library;

import java.time.format.DateTimeFormatter;

public class LocalDateTime {
    public static String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return java.time.LocalDateTime.now().format(formatter);
    }
}
