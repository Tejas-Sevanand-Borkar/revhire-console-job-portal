CREATE DATABASE IF NOT EXISTS revhire;
USE revhire;

CREATE TABLE IF NOT EXISTS users(
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(100),
 email VARCHAR(100) UNIQUE,
 password VARCHAR(100),
 role VARCHAR(20),
 security_question VARCHAR(255),
 security_answer VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS companies(
 id INT AUTO_INCREMENT PRIMARY KEY,
 ownerId INT,
 name VARCHAR(100),
 industry VARCHAR(100),
 size INT,
 description TEXT,
 website VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS jobs(
 id INT AUTO_INCREMENT PRIMARY KEY,
 companyId INT,
 title VARCHAR(100),
 description TEXT,
 skills TEXT,
 education VARCHAR(100),
 location VARCHAR(100),
 salary INT,
 jobType VARCHAR(50),
 deadline VARCHAR(50),
 status VARCHAR(20),
 minExp INT,
 maxExp INT
);

CREATE TABLE IF NOT EXISTS profiles(
 userId INT PRIMARY KEY,
 education TEXT,
 experience TEXT,
 skills TEXT,
 certifications TEXT
);

CREATE TABLE IF NOT EXISTS resumes(
 userId INT PRIMARY KEY,
 objective TEXT,
 education TEXT,
 experience TEXT,
 skills TEXT,
 projects TEXT
);

CREATE TABLE IF NOT EXISTS applications(
 id INT AUTO_INCREMENT PRIMARY KEY,
 userId INT,
 jobId INT,
 status VARCHAR(50),
 coverLetter TEXT,
 comment TEXT,
 withdrawReason TEXT
);

CREATE TABLE IF NOT EXISTS notifications(
 id INT AUTO_INCREMENT PRIMARY KEY,
 userId INT,
 message TEXT
);
