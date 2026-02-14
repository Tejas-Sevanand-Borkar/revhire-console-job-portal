package com.revhire.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/revhire",
            "root",
            "Tej10@Sid"
        );
    }
}
