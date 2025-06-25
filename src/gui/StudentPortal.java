package gui;

import backend.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentPortal extends JFrame {
    private JTextArea availableCoursesArea, registeredCoursesArea;
    private JTextField studentIdField, courseCodeField;
    private JButton loadCoursesButton, registerButton, unregisterButton, viewTimetableButton;

    public StudentPortal() {
        setTitle("Student Portal - Time Table Builder");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fields
        studentIdField = new JTextField(15);
        courseCodeField = new JTextField(10);
        availableCoursesArea = new JTextArea(10, 50);
        registeredCoursesArea = new JTextArea(10, 50);
        availableCoursesArea.setEditable(false);
        registeredCoursesArea.setEditable(false);

        // Buttons
        loadCoursesButton = new JButton("📚 Load Available Courses");
        registerButton = new JButton("✅ Register");
        unregisterButton = new JButton("❌ Unregister");
        viewTimetableButton = new JButton("📅 View My Timetable");

        // Layout
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.add(new JLabel("🎓 Student ID:"));
        inputPanel.add(studentIdField);
        inputPanel.add(new JLabel("📘 Course Code:"));
        inputPanel.add(courseCodeField);
        inputPanel.add(registerButton);
        inputPanel.add(unregisterButton);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.add(new JScrollPane(availableCoursesArea));
        centerPanel.add(new JScrollPane(registeredCoursesArea));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loadCoursesButton);
        bottomPanel.add(viewTimetableButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Button listeners

        loadCoursesButton.addActionListener(e -> {
            List<String> courses = DatabaseManager.getAllCourses();
            if (courses.isEmpty()) {
                availableCoursesArea.setText("No courses available.");
            } else {
                StringBuilder sb = new StringBuilder("📚 Available Courses:\n");
                for (String course : courses) sb.append(course).append("\n");
                availableCoursesArea.setText(sb.toString());
            }
        });

        registerButton.addActionListener(e -> {
            String studentId = studentIdField.getText().trim();
            String courseCode = courseCodeField.getText().trim().toUpperCase();

            if (studentId.isEmpty() || courseCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Student ID and Course Code.");
                return;
            }

            DatabaseManager.registerCourse(studentId, courseCode);
            JOptionPane.showMessageDialog(this, "Course registered successfully!");
            courseCodeField.setText("");
        });

        unregisterButton.addActionListener(e -> {
            String studentId = studentIdField.getText().trim();
            String courseCode = courseCodeField.getText().trim().toUpperCase();

            if (studentId.isEmpty() || courseCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Student ID and Course Code.");
                return;
            }

            DatabaseManager.unregisterCourse(studentId, courseCode);
            JOptionPane.showMessageDialog(this, "Unregistered from course.");
            courseCodeField.setText("");
        });

        viewTimetableButton.addActionListener(e -> {
            String studentId = studentIdField.getText().trim();

            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Student ID.");
                return;
            }

            List<String> timetable = DatabaseManager.getRegisteredCourses(studentId);
            if (timetable.isEmpty()) {
                registeredCoursesArea.setText("No courses registered yet.");
            } else {
                StringBuilder sb = new StringBuilder("📅 My Timetable:\n");
                for (String entry : timetable) sb.append(entry).append("\n");
                registeredCoursesArea.setText(sb.toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentPortal::new);
    }
}
