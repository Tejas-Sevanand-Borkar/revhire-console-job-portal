package com.revhire.dao;

import com.revhire.model.Resume;
import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResumeDAO {
    public static void save(Resume r) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement("REPLACE INTO resumes VALUES(?,?,?,?,?,?)");
        ps.setInt(1, r.userId);
        ps.setString(2, r.objective);
        ps.setString(3, r.education);
        ps.setString(4, r.experience);
        ps.setString(5, r.skills);
        ps.setString(6, r.projects);
        ps.executeUpdate();
    }
}
