package gui;

import backend.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminPortal extends JFrame {
    private JTextField courseCodeField, courseNameField, creditsField, semesterField, dayField, timeSlotField;
    private JTextArea courseDisplayArea;
    private JButton addButton, deleteButton, loadButton;

    public AdminPortal() {
        setTitle("Admin Portal - Time Table Builder");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Input fields
        courseCodeField = new JTextField(10);
        courseNameField = new JTextField(15);
        creditsField = new JTextField(5);
        semesterField = new JTextField(5);
        dayField = new JTextField(10);
        timeSlotField = new JTextField(15);

        // Buttons
        addButton = new JButton("Add Course");
        deleteButton = new JButton("Delete Course");
        loadButton = new JButton("Load Courses");

        // Display area
        courseDisplayArea = new JTextArea(10, 50);
        courseDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(courseDisplayArea);

        // Layout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 5, 5));
        inputPanel.add(new JLabel("Course Code:"));
        inputPanel.add(courseCodeField);
        inputPanel.add(new JLabel("Course Name:"));
        inputPanel.add(courseNameField);
        inputPanel.add(new JLabel("Credits:"));
        inputPanel.add(creditsField);
        inputPanel.add(new JLabel("Semester:"));
        inputPanel.add(semesterField);
        inputPanel.add(new JLabel("Day (e.g., Monday):"));
        inputPanel.add(dayField);
        inputPanel.add(new JLabel("Time Slot (e.g., 10:00-11:00):"));
        inputPanel.add(timeSlotField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(loadButton, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Action Listeners
        addButton.addActionListener(e -> {
            try {
                String code = courseCodeField.getText().trim();
                String name = courseNameField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                int semester = Integer.parseInt(semesterField.getText().trim());
                String day = dayField.getText().trim();
                String time = timeSlotField.getText().trim();

                if (code.isEmpty() || name.isEmpty() || day.isEmpty() || time.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields.");
                    return;
                }

                DatabaseManager.addCourse(code, name, credits, semester, day, time);
                JOptionPane.showMessageDialog(this, "Course added successfully!");
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            String code = courseCodeField.getText().trim();
            if (!code.isEmpty()) {
                DatabaseManager.deleteCourse(code);
                JOptionPane.showMessageDialog(this, "Course deleted.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Enter Course Code to delete.");
            }
        });

        loadButton.addActionListener(e -> {
            List<String> courseList = DatabaseManager.getAllCourses();
            StringBuilder sb = new StringBuilder("All Courses:\n");
            for (String course : courseList) {
                sb.append(course).append("\n");
            }
            courseDisplayArea.setText(sb.toString());
        });
    }

    private void clearFields() {
        courseCodeField.setText("");
        courseNameField.setText("");
        creditsField.setText("");
        semesterField.setText("");
        dayField.setText("");
        timeSlotField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminPortal::new);
    }
}
