# Student Management System

A console-based **Java + SQLite** application for managing student records with full **CRUD** operations.

## Features

- ✅ Add new student records
- 📋 View all students in a formatted table
- 🔍 Search student by ID
- ✏️ Update existing student details
- 🗑️ Delete student records
- 💾 Persistent SQLite database — data is saved between runs

## Project Structure

```
student-management-system/
├── src/
│   ├── Main.java           # Entry point + menu loop
│   ├── Student.java        # Student model
│   ├── StudentService.java # Validation layer
│   └── DatabaseManager.java# SQLite CRUD operations
├── lib/
│   └── sqlite-jdbc.jar     # SQLite JDBC driver
└── README.md
```

## How to Run

### 1. Compile
```bash
mkdir -p out
javac -cp lib/sqlite-jdbc.jar src/*.java -d out
```

### 2. Run
```bash
# macOS / Linux
java -cp out:lib/sqlite-jdbc.jar Main

# Windows
java -cp "out;lib/sqlite-jdbc.jar" Main
```

## Sample Output

```
╔══════════════════════════════════════════╗
║     STUDENT MANAGEMENT SYSTEM  v1.0      ║
║        Java + SQLite  |  CRUD App         ║
╚══════════════════════════════════════════╝

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
```

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 11+ | Core language |
| SQLite (via JDBC) | Persistent database |
| JDBC | Database connectivity |

## Requirements

- Java 11 or higher
- No external dependencies (SQLite JDBC jar included in `lib/`)
