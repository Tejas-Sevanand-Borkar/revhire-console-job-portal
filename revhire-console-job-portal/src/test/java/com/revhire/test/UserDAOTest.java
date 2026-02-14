package com.revhire.test;

import com.revhire.dao.UserDAO;
import com.revhire.model.User;
import org.junit.Assert;
import org.junit.Test;

public class UserDAOTest {

    @Test
    public void testRegisterAndLogin() throws Exception {
        User u = new User();
        u.name = "JUnit User";
        u.email = "junit_user@mail.com";
        u.password = "1234";
        u.role = "JOBSEEKER";
        u.securityQuestion = "City?";
        u.securityAnswer = "Pune";

        UserDAO.register(u);
        User logged = UserDAO.login(u.email, u.password);

        Assert.assertNotNull(logged);
    }
}
