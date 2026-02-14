package com.revhire.dao;

import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ApplicationDAO {

    public static void apply(int userId, int jobId, String coverLetter) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO applications(userId,jobId,status,coverLetter) VALUES(?,?,?,?)"
        );
        ps.setInt(1, userId);
        ps.setInt(2, jobId);
        ps.setString(3, "Applied");
        ps.setString(4, coverLetter);
        ps.executeUpdate();
    }

    public static ResultSet findByUser(int userId) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT a.id, j.title, a.status FROM applications a JOIN jobs j ON a.jobId=j.id WHERE a.userId=?"
        );
        ps.setInt(1, userId);
        return ps.executeQuery();
    }

    public static void withdraw(int appId, String reason) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "UPDATE applications SET status='Withdrawn', withdrawReason=? WHERE id=?"
        );
        ps.setString(1, reason);
        ps.setInt(2, appId);
        ps.executeUpdate();
    }

    public static ResultSet findApplicantsByJob(int jobId) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT a.id, u.name, a.status FROM applications a JOIN users u ON a.userId=u.id WHERE a.jobId=?"
        );
        ps.setInt(1, jobId);
        return ps.executeQuery();
    }

    public static void updateStatus(int appId, String status, String comment) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "UPDATE applications SET status=?, comment=? WHERE id=?"
        );
        ps.setString(1, status);
        ps.setString(2, comment);
        ps.setInt(3, appId);
        ps.executeUpdate();
    }
}
