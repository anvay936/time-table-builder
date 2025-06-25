package backend;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/timetable_db";
    private static final String USER = "root";
    private static final String PASSWORD = "anvay";

    private static Connection conn = null;

    public static void connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to DB successfully");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static void addCourse(String code, String name, int credits, int semester, String day, String timeSlot) {
    try {
        connect();
        String sql = "INSERT INTO courses (code, name, credits, semester, day, time_slot) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, code);
        stmt.setString(2, name);
        stmt.setInt(3, credits);
        stmt.setInt(4, semester);
        stmt.setString(5, day);
        stmt.setString(6, timeSlot);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void getCourses() {
    try {
        connect();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM courses");
        while (rs.next()) {
            System.out.println(rs.getString("code") + " - " + rs.getString("name"));
        }
    } catch (SQLException e) {
        System.out.println("Query failed: " + e.getMessage());
    }
}



// Delete a course by code
public static void deleteCourse(String code) {
    try {
        connect();
        String sql = "DELETE FROM courses WHERE code = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, code);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Get all courses from DB
public static List<String> getAllCourses() {
    List<String> courses = new ArrayList<>();
    try {
        connect();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM courses");
        while (rs.next()) {
            courses.add(rs.getString("code") + " - " + rs.getString("name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return courses;
}

public static void registerCourse(String studentId, String courseCode) {
    try {
        connect();
        String sql = "INSERT INTO student_courses (student_id, course_code) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, studentId);
        stmt.setString(2, courseCode);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static List<String> getRegisteredCourses(String studentId) {
    List<String> registered = new ArrayList<>();
    try {
        connect();
        String sql = "SELECT c.code, c.name, c.day, c.time_slot FROM courses c " +
                     "JOIN student_courses sc ON c.code = sc.course_code " +
                     "WHERE sc.student_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, studentId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String line = rs.getString("code") + " - " + rs.getString("name") +
                          " | " + rs.getString("day") + " | " + rs.getString("time_slot");
            registered.add(line);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return registered;
}

public static void unregisterCourse(String studentId, String courseCode) {
    try {
        connect();
        String sql = "DELETE FROM student_courses WHERE student_id = ? AND course_code = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, studentId);
        stmt.setString(2, courseCode);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        connect();
    }
}
