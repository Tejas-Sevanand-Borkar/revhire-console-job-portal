package com.revhire.dao;

import com.revhire.model.Job;
import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    public static void add(Job j) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO jobs(companyId,title,description,skills,education,location,salary,jobType,deadline,status,minExp,maxExp) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"
        );
        ps.setInt(1, j.companyId);
        ps.setString(2, j.title);
        ps.setString(3, j.description);
        ps.setString(4, j.skills);
        ps.setString(5, j.education);
        ps.setString(6, j.location);
        ps.setInt(7, j.salary);
        ps.setString(8, j.jobType);
        ps.setString(9, j.deadline);
        ps.setString(10, "OPEN");
        ps.setInt(11, j.minExp);
        ps.setInt(12, j.maxExp);
        ps.executeUpdate();
    }

    public static List<Job> search(String loc) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT * FROM jobs WHERE location LIKE ? AND status='OPEN'"
        );
        ps.setString(1, "%" + loc + "%");
        ResultSet rs = ps.executeQuery();
        List<Job> list = new ArrayList<>();
        while (rs.next()) {
            Job j = new Job();
            j.id = rs.getInt("id");
            j.title = rs.getString("title");
            j.location = rs.getString("location");
            j.salary = rs.getInt("salary");
            list.add(j);
        }
        return list;
    }

    public static void close(int id) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE jobs SET status='CLOSED' WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
