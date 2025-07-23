# ThreadPoolExecutorImplementation

This project demonstrates the use of Java's `ThreadPoolExecutor` for efficient multithreading and concurrent task management. It is structured as a Maven project and includes sample code, dependencies, and configurations to help you understand and experiment with thread pooling concepts.

## Table of Contents
- [Overview](#overview)
- [Folder Structure](#folder-structure)
- [Key Files](#key-files)
- [Getting Started](#getting-started)
- [Building & Running](#building--running)
- [Customization](#customization)
- [License](#license)

## Overview

Java's `ThreadPoolExecutor` provides a flexible framework for managing threads and executing tasks concurrently. Instead of manually creating threads, you submit tasks to the Executor, which efficiently manages the underlying thread pool.

This implementation showcases:
- Creation and configuration of a thread pool
- Submission of tasks for execution
- Graceful shutdown and management of threads

## Folder Structure

```
ThreadPoolExecutorImplementation/
├── .gitignore
├── .idea/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── ... (Java source files)
```

### Folder & File Details

- `.gitignore`: Specifies files/folders to exclude from Git version control, such as IDE configs and build outputs.
- `.idea/`: Contains IDE-specific metadata, useful for developers using JetBrains IDEs like IntelliJ IDEA.
- `pom.xml`: Maven configuration file listing dependencies, build plugins, and project metadata.
- `README.md`: Project documentation (you're reading it!).
- `src/main/`: Holds the main Java source code implementing thread pool concepts.

## Key Files

- **`pom.xml`**
  - Declares Maven dependencies (including JUnit for testing, if required).
  - Sets Java version and build plugins.

- **Source Code (in `src/main/`)**
  - Contains Java classes that demonstrate:
    - Thread pool creation using `ThreadPoolExecutor`
    - Task submission using `Runnable` or `Callable`
    - Custom thread pool configuration (core pool size, maximum pool size, keep-alive time, etc.)
    - Graceful shutdown and task completion management

## Getting Started

### Prerequisites
- Java 8 or above
- Maven

### Setup

1. **Clone the repository**
   ```sh
   git clone https://github.com/naman-sriv/multithreading_experiments.git
   cd multithreading_experiments/ThreadPoolExecutorImplementation
   ```

2. **Build the project**
   ```sh
   mvn clean install
   ```

3. **Run the implementation**
   - Navigate to the main class (typically in `src/main/java/...`) and run it using your IDE or the command line:
     ```sh
     mvn exec:java -Dexec.mainClass="your.main.Class"
     ```

## Building & Running

- Use Maven for lifecycle management:
  - **Compile:** `mvn compile`
  - **Test:** `mvn test`
  - **Package:** `mvn package`
  - **Run:** `mvn exec:java`

## Customization

You can modify thread pool parameters in the source code to experiment with different concurrency scenarios:
- Core/maximum pool size
- Task queue size/type
- Rejection policies

Refer to the source files in `src/main/` for examples and customization points.

## License

This repository is for educational purposes. You may use and modify the code as needed for learning or research.

---

For further questions or contributions, please open an issue or submit a pull request!
