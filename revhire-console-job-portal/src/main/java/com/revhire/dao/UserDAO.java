package com.revhire.dao;

import com.revhire.model.User;
import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static void register(User u) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO users(name,email,password,role,security_question,security_answer) VALUES(?,?,?,?,?,?)"
        );
        ps.setString(1, u.name);
        ps.setString(2, u.email);
        ps.setString(3, u.password);
        ps.setString(4, u.role);
        ps.setString(5, u.securityQuestion);
        ps.setString(6, u.securityAnswer);
        ps.executeUpdate();
    }

    public static User login(String email, String password) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT * FROM users WHERE email=? AND password=?"
        );
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User u = new User();
            u.id = rs.getInt("id");
            u.name = rs.getString("name");
            u.role = rs.getString("role");
            return u;
        }
        return null;
    }

    public static boolean changePassword(int userId, String oldPass, String newPass) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "UPDATE users SET password=? WHERE id=? AND password=?"
        );
        ps.setString(1, newPass);
        ps.setInt(2, userId);
        ps.setString(3, oldPass);
        return ps.executeUpdate() > 0;
    }

    public static boolean resetPassword(String email, String q, String a, String newPass) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "UPDATE users SET password=? WHERE email=? AND security_question=? AND security_answer=?"
        );
        ps.setString(1, newPass);
        ps.setString(2, email);
        ps.setString(3, q);
        ps.setString(4, a);
        return ps.executeUpdate() > 0;
    }
}
