package com.revhire.dao;

import com.revhire.model.Profile;
import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProfileDAO {
    public static void save(Profile p) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement("REPLACE INTO profiles VALUES(?,?,?,?,?)");
        ps.setInt(1, p.userId);
        ps.setString(2, p.education);
        ps.setString(3, p.experience);
        ps.setString(4, p.skills);
        ps.setString(5, p.certifications);
        ps.executeUpdate();
    }
}
