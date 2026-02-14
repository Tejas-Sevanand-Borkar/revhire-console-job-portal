package com.revhire.dao;

import com.revhire.model.Company;
import com.revhire.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CompanyDAO {
    public static void save(Company cpn) throws Exception {
        Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "REPLACE INTO companies VALUES(?,?,?,?,?,?,?)"
        );
        ps.setInt(1, cpn.id);
        ps.setInt(2, cpn.ownerId);
        ps.setString(3, cpn.name);
        ps.setString(4, cpn.industry);
        ps.setInt(5, cpn.size);
        ps.setString(6, cpn.description);
        ps.setString(7, cpn.website);
        ps.executeUpdate();
    }
}
