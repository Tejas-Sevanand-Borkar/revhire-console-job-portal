package com.revhire.service;

import java.util.Scanner;
import java.sql.ResultSet;

import com.revhire.dao.*;
import com.revhire.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenu {

    private static final Logger logger = LogManager.getLogger(MainMenu.class);
    static Scanner sc = new Scanner(System.in);

    public static void start() {
        while (true) {
            System.out.println("\n===== RevHire Job Portal =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int c = sc.nextInt();
            sc.nextLine();

            try {
                if (c == 1) register();
                else if (c == 2) login();
                else if (c == 3) forgotPassword();
                else return;
            } catch (Exception e) {
                logger.error("Database error occurred", e);
                System.out.println("❌ Error occurred.");
            }
        }
    }

    static void register() throws Exception {
        User u = new User();
        System.out.print("Name: "); u.name = sc.nextLine();
        System.out.print("Email: "); u.email = sc.nextLine();
        System.out.print("Password: "); u.password = sc.nextLine();
        System.out.print("Role (JOBSEEKER/EMPLOYER): "); u.role = sc.nextLine();
        System.out.print("Security Question: "); u.securityQuestion = sc.nextLine();
        System.out.print("Security Answer: "); u.securityAnswer = sc.nextLine();
        UserDAO.register(u);
        logger.info("User registered successfully");
        System.out.println("✅ Registered");
    }

    static void login() throws Exception {
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        User u = UserDAO.login(e, p);
        if (u == null) {
            System.out.println("❌ Invalid credentials");
            return;
        }
        if ("EMPLOYER".equalsIgnoreCase(u.role)) employerMenu(u);
        else jobMenu(u);
    }

    static void forgotPassword() throws Exception {
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Question: ");
        String q = sc.nextLine();
        System.out.print("Answer: ");
        String a = sc.nextLine();
        System.out.print("New Password: ");
        String np = sc.nextLine();
        boolean ok = UserDAO.resetPassword(e, q, a, np);
        System.out.println(ok ? "✅ Password reset" : "❌ Invalid details");
    }

    static void jobMenu(User u) throws Exception {
        while (true) {
            System.out.println("\n1.Profile 2.Resume 3.Search 4.Apply 5.My Apps 6.Withdraw 7.Change Pass 8.Logout");
            int c = sc.nextInt(); sc.nextLine();
            if (c == 1) {
                Profile p = new Profile();
                p.userId = u.id;
                System.out.print("Education: "); p.education = sc.nextLine();
                System.out.print("Experience: "); p.experience = sc.nextLine();
                System.out.print("Skills: "); p.skills = sc.nextLine();
                System.out.print("Certifications: "); p.certifications = sc.nextLine();
                ProfileDAO.save(p);
                System.out.println("✅ Profile saved");
            } else if (c == 2) {
                Resume r = new Resume();
                r.userId = u.id;
                System.out.print("Objective: "); r.objective = sc.nextLine();
                System.out.print("Education: "); r.education = sc.nextLine();
                System.out.print("Experience: "); r.experience = sc.nextLine();
                System.out.print("Skills: "); r.skills = sc.nextLine();
                System.out.print("Projects: "); r.projects = sc.nextLine();
                ResumeDAO.save(r);
                System.out.println("✅ Resume saved");
            } else if (c == 3) {
                System.out.print("Location: ");
                for (Job j : JobDAO.search(sc.nextLine())) {
                    System.out.println(j.id + " | " + j.title + " | Salary:" + j.salary);
                }
            } else if (c == 4) {
                System.out.print("Job ID: "); int jid = sc.nextInt(); sc.nextLine();
                System.out.print("Cover Letter: "); String cl = sc.nextLine();
                ApplicationDAO.apply(u.id, jid, cl);
                NotificationDAO.notifyUser(u.id, "Applied for job " + jid);
                System.out.println("✅ Applied");
            } else if (c == 5) {
                ResultSet rs = ApplicationDAO.findByUser(u.id);
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | " + rs.getString("title") + " | " + rs.getString("status"));
                }
            } else if (c == 6) {
                System.out.print("Application ID: "); int aid = sc.nextInt(); sc.nextLine();
                System.out.print("Reason: "); String r = sc.nextLine();
                ApplicationDAO.withdraw(aid, r);
                System.out.println("✅ Withdrawn");
            } else if (c == 7) {
                System.out.print("Old Pass: "); String op = sc.nextLine();
                System.out.print("New Pass: "); String np = sc.nextLine();
                boolean ok = UserDAO.changePassword(u.id, op, np);
                System.out.println(ok ? "✅ Changed" : "❌ Wrong password");
            } else return;
        }
    }

    static void employerMenu(User u) throws Exception {
        while (true) {
            System.out.println("\n1.Company 2.Post Job 3.Close Job 4.View Applicants 5.Shortlist/Reject 6.Change Pass 7.Logout");
            int c = sc.nextInt(); sc.nextLine();
            if (c == 1) {
                Company cp = new Company();
                cp.ownerId = u.id;
                System.out.print("Name: "); cp.name = sc.nextLine();
                System.out.print("Industry: "); cp.industry = sc.nextLine();
                System.out.print("Size: "); cp.size = sc.nextInt(); sc.nextLine();
                System.out.print("Desc: "); cp.description = sc.nextLine();
                System.out.print("Website: "); cp.website = sc.nextLine();
                CompanyDAO.save(cp);
                System.out.println("✅ Company saved");
            } else if (c == 2) {
                Job j = new Job();
                j.companyId = u.id;
                System.out.print("Title: "); j.title = sc.nextLine();
                System.out.print("Desc: "); j.description = sc.nextLine();
                System.out.print("Skills: "); j.skills = sc.nextLine();
                System.out.print("Edu: "); j.education = sc.nextLine();
                System.out.print("Loc: "); j.location = sc.nextLine();
                System.out.print("Salary: "); j.salary = sc.nextInt();
                System.out.print("MinExp: "); j.minExp = sc.nextInt();
                System.out.print("MaxExp: "); j.maxExp = sc.nextInt(); sc.nextLine();
                System.out.print("Type: "); j.jobType = sc.nextLine();
                System.out.print("Deadline: "); j.deadline = sc.nextLine();
                JobDAO.add(j);
                logger.info("Job posted successfully");
                System.out.println("✅ Job posted");
            } else if (c == 3) {
                System.out.print("Job ID: "); JobDAO.close(sc.nextInt()); sc.nextLine();
                System.out.println("✅ Job closed");
            } else if (c == 4) {
                System.out.print("Job ID: "); int jid = sc.nextInt(); sc.nextLine();
                ResultSet rs = ApplicationDAO.findApplicantsByJob(jid);
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString("status"));
                }
            } else if (c == 5) {
                System.out.print("App ID: "); int aid = sc.nextInt(); sc.nextLine();
                System.out.print("Status (Shortlisted/Rejected): "); String st = sc.nextLine();
                System.out.print("Comment: "); String cm = sc.nextLine();
                ApplicationDAO.updateStatus(aid, st, cm);
                System.out.println("✅ Updated");
            } else if (c == 6) {
                System.out.print("Old Pass: "); String op = sc.nextLine();
                System.out.print("New Pass: "); String np = sc.nextLine();
                boolean ok = UserDAO.changePassword(u.id, op, np);
                System.out.println(ok ? "✅ Changed" : "❌ Wrong password");
            } else return;
        }
    }
}
