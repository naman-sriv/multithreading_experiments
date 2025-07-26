# DeadlockDemo

## Overview

**DeadlockDemo** is a sample project within the [`multithreading_experiments`](https://github.com/naman-sriv/multithreading_experiments) repository, designed to illustrate the concept of deadlocks in concurrent programming. Deadlocks occur when two or more threads are each waiting for the other to release a resource, causing all involved threads to be unable to proceed. This folder provides a simple, reproducible example of how deadlocks can occur in Java, as well as instructions and educational notes for students and engineers learning about multithreading pitfalls.

## Purpose

- **Educational demonstration** of deadlock scenarios in multithreaded Java programs.
- **Hands-on experimentation** with thread synchronization and resource locking.
- **Reference code** for debugging, prevention, and detection techniques.

## Contents

- `DeadlockDemo.java`: Main Java file demonstrating a classic deadlock scenario using two threads and two locks.
- Other supporting files (if present).

## Deadlock Scenario Explained

The provided code typically creates two resources (locks or objects), and two threads. Each thread tries to acquire the locks in a different order:

- **Thread 1**: Acquires `lockA`, then tries to acquire `lockB`.
- **Thread 2**: Acquires `lockB`, then tries to acquire `lockA`.

If both threads acquire their first lock and then attempt to get the second, each will wait indefinitely for the other to release the lock, resulting in a deadlock.

## How to Run

### Prerequisites

- Java Development Kit (JDK) installed (version 8 or higher recommended).
- Command line terminal or IDE (such as IntelliJ IDEA, Eclipse, VS Code) configured for Java.

### Steps

1. **Clone the repository** (if not already done):

   ```bash
   git clone https://github.com/naman-sriv/multithreading_experiments.git
   cd multithreading_experiments/DeadlockDemo
   ```

2. **Compile the Java file**:

   ```bash
   javac DeadlockDemo.java
   ```

3. **Run the demo:**

   ```bash
   java DeadlockDemo
   ```

4. **Observe the output**: The program will likely hang (no completion), indicating a deadlock.

## Example Output

```
Thread 1: Holding lock A...
Thread 2: Holding lock B...
Thread 1: Waiting for lock B...
Thread 2: Waiting for lock A...
```
*(At this point, both threads are waiting indefinitely.)*

## Key Concepts Illustrated

- **Synchronization**: Using `synchronized` blocks or explicit lock objects.
- **Resource Ordering**: How acquiring resources in different orders can cause deadlocks.
- **Thread States**: Threads can be blocked and waiting.
- **Debugging Deadlocks**: Identifying deadlock situations using thread dumps or IDE debuggers.

## How to Avoid Deadlocks

- **Consistent lock ordering**: Always acquire locks in the same order.
- **Timeouts**: Use lock acquisition methods with timeouts.
- **Lock hierarchy**: Design locks in a way that avoids circular wait.
- **Lock splitting**: Minimize the scope and duration of lock holding.

## Further Reading

- [Java Concurrency in Practice](https://jcip.net/)
- [Official Oracle Tutorial: Deadlocks](https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html)
- [Stack Overflow: What is a deadlock?](https://stackoverflow.com/questions/34510/what-is-a-deadlock)

## License

This demo is provided for educational purposes. See the main repository's license for details.

## Author

- [Naman Srivastava](https://github.com/naman-sriv)

---

Feel free to modify and extend this demo to include more complex multithreading scenarios, detection mechanisms, or deadlock recovery strategies!
