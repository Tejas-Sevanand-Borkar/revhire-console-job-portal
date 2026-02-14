package com.revhire.dao;

import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NotificationDAO {
    public static void notifyUser(int userId, String msg) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO notifications(userId,message) VALUES(?,?)"
        );
        ps.setInt(1, userId);
        ps.setString(2, msg);
        ps.executeUpdate();
        System.out.println("🔔 " + msg);
    }
}
