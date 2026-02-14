package com.revhire.test;

import com.revhire.dao.JobDAO;
import com.revhire.model.Job;
import org.junit.Assert;
import org.junit.Test;

public class JobDAOTest {

    @Test
    public void testAddJob() throws Exception {
        Job j = new Job();
        j.companyId = 1;
        j.title = "JUnit Dev";
        j.description = "Test";
        j.skills = "Java";
        j.education = "BE";
        j.location = "Pune";
        j.salary = 500000;
        j.jobType = "Full Time";
        j.deadline = "2026-12-31";
        j.minExp = 0;
        j.maxExp = 2;

        JobDAO.add(j);
        Assert.assertTrue(true);
    }
}
