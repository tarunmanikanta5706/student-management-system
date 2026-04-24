import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 * Entry point — console menu loop for the Student Management System.
 *
 * Compile:  javac -cp lib/sqlite-jdbc.jar src/*.java -d out
 * Run:      java -cp out:lib/sqlite-jdbc.jar Main
 */
public class Main {

    private static final StudentService service = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> viewAllStudents();
                case "3" -> searchStudent();
                case "4" -> updateStudent();
                case "5" -> deleteStudent();
                case "6" -> {
                    System.out.println("\n  Goodbye! Total students in DB: " + service.getStudentCount());
                    System.exit(0);
                }
                default  -> System.out.println("  [!] Invalid option. Please enter 1-6.");
            }
        }
    }

    // ── Menu helpers ──────────────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println("""
                ╔══════════════════════════════════════════╗
                ║     STUDENT MANAGEMENT SYSTEM  v1.0      ║
                ║        Java + SQLite  |  CRUD App         ║
                ╚══════════════════════════════════════════╝
                """);
    }

    private static void printMenu() {
        System.out.println("""
                ┌─────────────────────────────┐
                │         MAIN MENU           │
                ├─────────────────────────────┤
                │  1. Add Student             │
                │  2. View All Students       │
                │  3. Search Student by ID    │
                │  4. Update Student          │
                │  5. Delete Student          │
                │  6. Exit                    │
                └─────────────────────────────┘
                Enter your choice: \
                """);
    }

    // ── CRUD actions ──────────────────────────────────────────────────────────

    private static void addStudent() {
        System.out.println("\n── Add New Student ──────────────────────────");
        System.out.print("  Name       : "); String name = scanner.nextLine();
        System.out.print("  Age        : "); int age = readInt();
        System.out.print("  Department : "); String dept = scanner.nextLine();
        System.out.print("  GPA (0-10) : "); double gpa = readDouble();

        if (service.addStudent(name, age, dept, gpa)) {
            System.out.println("  ✅ Student added successfully!");
        } else {
            System.out.println("  ❌ Failed to add student.");
        }
    }

    private static void viewAllStudents() {
        List<Student> students = service.getAllStudents();
        System.out.println("\n── All Students ─────────────────────────────────────────────────────────");
        if (students.isEmpty()) {
            System.out.println("  No students found. Add some first!");
            return;
        }
        printTableHeader();
        students.forEach(System.out::println);
        printTableFooter();
        System.out.println("  Total: " + students.size() + " student(s)");
    }

    private static void searchStudent() {
        System.out.print("\n── Search by ID: ");
        int id = readInt();
        Student s = service.getStudentById(id);
        if (s != null) {
            printTableHeader();
            System.out.println(s);
            printTableFooter();
        } else {
            System.out.println("  [!] No student found with ID " + id);
        }
    }

    private static void updateStudent() {
        System.out.print("\n── Update Student — enter ID: ");
        int id = readInt();
        Student existing = service.getStudentById(id);
        if (existing == null) {
            System.out.println("  [!] No student found with ID " + id);
            return;
        }
        System.out.println("  Existing record: " + existing);
        System.out.print("  New Name        [" + existing.getName()       + "]: "); String name = scanner.nextLine();
        System.out.print("  New Age         [" + existing.getAge()        + "]: "); int age = readInt();
        System.out.print("  New Department  [" + existing.getDepartment() + "]: "); String dept = scanner.nextLine();
        System.out.print("  New GPA         [" + existing.getGpa()        + "]: "); double gpa = readDouble();

        if (name.isBlank())  name = existing.getName();
        if (age == 0)        age  = existing.getAge();
        if (dept.isBlank())  dept = existing.getDepartment();
        if (gpa == 0.0)      gpa  = existing.getGpa();

        if (service.updateStudent(id, name, age, dept, gpa)) {
            System.out.println("  ✅ Student updated successfully!");
        } else {
            System.out.println("  ❌ Update failed.");
        }
    }

    private static void deleteStudent() {
        System.out.print("\n── Delete Student — enter ID: ");
        int id = readInt();
        System.out.print("  ⚠️  Confirm deletion of student ID " + id + "? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes")) {
            if (service.deleteStudent(id)) {
                System.out.println("  ✅ Student deleted.");
            } else {
                System.out.println("  ❌ Deletion failed.");
            }
        } else {
            System.out.println("  Deletion cancelled.");
        }
    }

    // ── Table formatting ──────────────────────────────────────────────────────

    private static void printTableHeader() {
        System.out.println("+-" + "-".repeat(4) + "-+-" + "-".repeat(20) + "-+-" + "-".repeat(3) + "-+-" + "-".repeat(20) + "-+-" + "-".repeat(4) + "-+");
        System.out.printf("| %-4s | %-20s | %-3s | %-20s | %-4s |%n", "ID", "Name", "Age", "Department", "GPA");
        System.out.println("+-" + "-".repeat(4) + "-+-" + "-".repeat(20) + "-+-" + "-".repeat(3) + "-+-" + "-".repeat(20) + "-+-" + "-".repeat(4) + "-+");
    }

    private static void printTableFooter() {
        System.out.println("+-" + "-".repeat(4) + "-+-" + "-".repeat(20) + "-+-" + "-".repeat(3) + "-+-" + "-".repeat(20) + "-+-" + "-".repeat(4) + "-+");
    }

    // ── Input helpers ─────────────────────────────────────────────────────────

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double readDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
