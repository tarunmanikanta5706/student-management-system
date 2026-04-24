import java.util.List;

/**
 * StudentService.java
 * Business logic layer — validates input and delegates to DatabaseManager.
 */
public class StudentService {

    private final DatabaseManager db;

    public StudentService() {
        this.db = new DatabaseManager();
    }

    public boolean addStudent(String name, int age, String department, double gpa) {
        if (name == null || name.isBlank()) {
            System.out.println("  [!] Name cannot be empty.");
            return false;
        }
        if (age < 15 || age > 60) {
            System.out.println("  [!] Age must be between 15 and 60.");
            return false;
        }
        if (department == null || department.isBlank()) {
            System.out.println("  [!] Department cannot be empty.");
            return false;
        }
        if (gpa < 0.0 || gpa > 10.0) {
            System.out.println("  [!] GPA must be between 0.0 and 10.0.");
            return false;
        }
        return db.addStudent(name.trim(), age, department.trim(), gpa);
    }

    public List<Student> getAllStudents() {
        return db.getAllStudents();
    }

    public Student getStudentById(int id) {
        return db.getStudentById(id);
    }

    public boolean updateStudent(int id, String name, int age, String department, double gpa) {
        if (db.getStudentById(id) == null) {
            System.out.println("  [!] No student found with ID " + id);
            return false;
        }
        return db.updateStudent(id, name.trim(), age, department.trim(), gpa);
    }

    public boolean deleteStudent(int id) {
        if (db.getStudentById(id) == null) {
            System.out.println("  [!] No student found with ID " + id);
            return false;
        }
        return db.deleteStudent(id);
    }

    public int getStudentCount() {
        return db.getStudentCount();
    }
}
