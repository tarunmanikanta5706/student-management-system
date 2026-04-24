import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager.java
 * Handles all SQLite database operations (CRUD) for Student records.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:students.db";

    public DatabaseManager() {
        initializeDatabase();
    }

    /** Create the students table if it doesn't exist. */
    private void initializeDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS students (
                    id         INTEGER PRIMARY KEY AUTOINCREMENT,
                    name       TEXT    NOT NULL,
                    age        INTEGER NOT NULL,
                    department TEXT    NOT NULL,
                    gpa        REAL    NOT NULL
                );
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initialising database: " + e.getMessage());
        }
    }

    // ── CREATE ────────────────────────────────────────────────────────────────

    /** Insert a new student. Returns true on success. */
    public boolean addStudent(String name, int age, String department, double gpa) {
        String sql = "INSERT INTO students (name, age, department, gpa) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, department);
            pstmt.setDouble(4, gpa);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // ── READ ──────────────────────────────────────────────────────────────────

    /** Retrieve all students. */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("gpa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    /** Retrieve a single student by ID. Returns null if not found. */
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("gpa")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    /** Update an existing student's details. Returns true on success. */
    public boolean updateStudent(int id, String name, int age, String department, double gpa) {
        String sql = "UPDATE students SET name=?, age=?, department=?, gpa=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, department);
            pstmt.setDouble(4, gpa);
            pstmt.setInt(5, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    /** Delete a student by ID. Returns true on success. */
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    /** Return total number of students in the database. */
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }
}
