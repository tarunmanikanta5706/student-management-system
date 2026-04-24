/**
 * Student.java
 * Model class representing a Student record.
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private String department;
    private double gpa;

    public Student(int id, String name, int age, String department, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.gpa = gpa;
    }

    // Getters
    public int getId()           { return id; }
    public String getName()      { return name; }
    public int getAge()          { return age; }
    public String getDepartment(){ return department; }
    public double getGpa()       { return gpa; }

    // Setters
    public void setName(String name)           { this.name = name; }
    public void setAge(int age)                { this.age = age; }
    public void setDepartment(String dept)     { this.department = dept; }
    public void setGpa(double gpa)             { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-3d | %-20s | %-4.2f |",
                id, name, age, department, gpa);
    }
}
