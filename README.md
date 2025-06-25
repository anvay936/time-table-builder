# 🗓️ Time Table Builder – Java + MySQL + Swing

A Java-based desktop application for academic timetable management. Students can register for courses and view their personalized timetables. 
Admins can add, delete, and manage course details including day and time — with real-time MySQL backend integration.

---

## 🚀 Features

### 👨‍🎓 Student Portal
- 📚 View available courses
- ✅ Register for courses
- ❌ Unregister from courses
- 📅 View personalized timetable (with day & time)

### 🧑‍💼 Admin Portal
- ➕ Add new courses (code, name, credits, semester, day, time slot)
- 🗑️ Delete existing courses
- 📖 View all courses in the database

### 💾 MySQL Integration
- JDBC-based connectivity
- Uses `courses` and `student_courses` tables
- Easily extensible schema

---

## 🧱 Database Schema

### 🗂️ `courses` Table

```sql
CREATE TABLE courses (
  code VARCHAR(10) PRIMARY KEY,
  name VARCHAR(100),
  credits INT,
  semester INT,
  day VARCHAR(20),
  time_slot VARCHAR(20)
);
```
### 🗂️ student_courses Table
```sql
CREATE TABLE student_courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id VARCHAR(50),
  course_code VARCHAR(10),
  FOREIGN KEY (course_code) REFERENCES courses(code)
);
```
---

## 🛠️ Tech Stack

| Layer         | Tech                          |
|---------------|-------------------------------|
| Language       | Java 17                       |
| UI Framework   | Java Swing (JFrame, JPanel)   |
| Database       | MySQL                         |
| DB Connector   | JDBC                          |
| IDE Used       | VS Code / Eclipse             |


---

## How To Run
### 1. Clone this repo
```bash
git clone https://github.com/<your-username>/time-table-builder.git
cd time-table-builder
```
### 2. Set up the database
- Create a MySQL DB: CREATE DATABASE timetable_db;
- Run the schema (see above)
- Insert some sample courses
- 
### 3. Update DB credentials in DatabaseManager.java:
```java
private static final String URL = "jdbc:mysql://localhost:3306/timetable_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```
### 4. Compile and Run
- Use VS Code or Eclipse
- Run AdminPortal.java for admin tasks
- Run StudentPortal.java for student registration and timetable

---

## 📌 Future Improvements

🔐 Role-based login system

📊 Grid-style timetable display

🧑‍🏫 Teacher portal with course assignment

❗ Timetable conflict detection

📤 Export to PDF
