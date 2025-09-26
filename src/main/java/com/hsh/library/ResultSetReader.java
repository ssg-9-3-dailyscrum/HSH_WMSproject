package main.java.com.hsh.library;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ResultSetReader는 JDBC ResultSet에서 값을 안전하게 추출할 수 있도록 도와주는 유틸리티 클래스.
 * - 컬럼 값이 null인 경우도 안전하게 처리하게 해주는 기능
 * - 타입을 제네릭으로 받아 다양한 타입(String, Integer 등)을 처리할 수 있습니다.
 */

/**
 * 제네릭은 : 코드를 여러 타입에 대해 재사용할 수 있게 해주는 문법
 * 클래스나 메소드가 특정 타입에만 고정되지 않고 다양한 타입에 대해서 동작할 수 있게 해주는 문법
* */

public class ResultSetReader {
    public  <T> T getColumnValue(ResultSet rs, String value, Class<T> type) {
        //"<T>"는 String이 될 수도 있고 ,Integer가 될 수도 있고 ,Double도 가능하다.

        try {
            rs.findColumn(value);
            if (type.equals(String.class)) {
                return type.cast(rs.getString(value));
            } else if (type.equals(Integer.class)) {
                int i = rs.getInt(value);
                return rs.wasNull() ? null : type.cast(i);
            }
            /// 어떤 타입이 들어올지 몰라서 -> 일단 타입 T라고 해두고 , 나중에 호출 할 때 알려줘

        } catch (SQLException e) {/// 예외처리 해줌.
            return null;
        }
        return null;
    }

}
