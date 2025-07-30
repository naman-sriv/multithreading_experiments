# Producer Consumer Problem

This directory contains implementations and experiments related to the classic **Producer-Consumer Problem** in multithreading, using various approaches and synchronization mechanisms. The Producer-Consumer Problem is a fundamental example of a multi-process synchronization challenge, where producer threads generate data and place it in a buffer, while consumer threads retrieve and process that data. Proper synchronization is necessary to prevent race conditions, buffer overflows, and underflows.

## Contents

- Source code demonstrating the Producer-Consumer Problem using different synchronization primitives (e.g., mutexes, semaphores, condition variables).
- Example programs to illustrate thread coordination and safe sharing of resources.
- Experimentation with different buffer sizes, producer/consumer ratios, and thread counts.

## Getting Started

### Prerequisites (Java)

- **Java Development Kit (JDK) 8 or later** installed on your system.
- Basic knowledge of Java threading (`Thread`, `Runnable`, `synchronized`, `wait`, `notify`, etc.).
- Familiarity with Java concurrency utilities (`java.util.concurrent` package: `Semaphore`, `BlockingQueue`, etc.).
- Any standard IDE (Eclipse, IntelliJ IDEA, VS Code) or a text editor for writing and running Java code.
- Command-line interface for compiling and running Java programs.

### Building

You can compile the Java programs using the following command:

```sh
javac ProducerConsumer.java
```

Replace `ProducerConsumer.java` with the relevant source file name.

### Running

After compiling, run the program using:

```sh
java ProducerConsumer
```

Refer to the source code comments for information on command-line arguments and configuration options, if any.

## Features

- **Thread Synchronization:** Demonstrates safe communication between producer and consumer threads using locks and condition variables.
- **Buffer Management:** Implements bounded buffers to show the effects of buffer size on synchronization and throughput.
- **Configurability:** Easily modify the number of producers, consumers, and buffer size to observe different behaviors.

## Directory Structure

```
ProducerConsumerProblem/
├── ProducerConsumer.java      # Example implementation of the problem
├── ...                       # Other source or helper files
├── README.md                 # This file
```

## References

- [Wikipedia: Producer–consumer problem](https://en.wikipedia.org/wiki/Producer%E2%80%93consumer_problem)
- [Java Concurrency Utilities](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [Java Threads and Synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html)

## License

This project is provided for educational purposes. See the main repository's license for details.
