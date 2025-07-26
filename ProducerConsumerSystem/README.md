# ProducerConsumerSystem

## Overview

The **ProducerConsumerSystem** is a classic implementation of the Producer-Consumer pattern using multithreading concepts. This system demonstrates how multiple producer and consumer threads synchronize and communicate with each other to process data efficiently. It is designed to showcase concurrent programming techniques, thread safety, and inter-thread communication in a controlled environment.

This folder is a part of the [`multithreading_experiments`](https://github.com/naman-sriv/multithreading_experiments) repository and serves as a practical reference for understanding and experimenting with producer-consumer problems.

## Features

- **Multiple Producers and Consumers:** Supports creation of multiple producer and consumer threads.
- **Thread Synchronization:** Utilizes synchronization primitives (e.g., mutexes, locks, condition variables) to avoid race conditions and ensure safe data exchange.
- **Bounded Buffer:** Implements a shared buffer with a fixed size to mediate between producers and consumers.
- **Graceful Shutdown:** Handles termination of threads and proper cleanup of resources.
- **Configurable Parameters:** Allows adjustment of buffer size, number of threads, and production/consumption rates.
- **Logging:** Provides detailed logging for thread operations, buffer status, and error handling.

## Architecture

The system consists of the following main components:

1. **Producer Threads:**  
   Continuously generate data and attempt to place it into the shared buffer. If the buffer is full, producers wait until space becomes available.

2. **Consumer Threads:**  
   Continuously remove data from the shared buffer for processing. If the buffer is empty, consumers wait until data is produced.

3. **Shared Buffer:**  
   A fixed-size container (e.g., queue or list) that holds produced data items until they are consumed.

4. **Synchronization Mechanisms:**  
   - **Mutex/Lock:** Protects buffer access.
   - **Condition Variable/Semaphore:** Coordinates producers and consumers, ensuring proper waiting and notification.

## How It Works

1. **Initialization:**  
   The main thread creates and starts a specified number of producer and consumer threads, initializes the shared buffer, and sets up synchronization objects.

2. **Data Production:**  
   Each producer thread generates data (could be random numbers, objects, etc.) and attempts to add it to the buffer. If the buffer is full, the thread blocks until a consumer consumes an item.

3. **Data Consumption:**  
   Each consumer thread removes data from the buffer and processes it. If the buffer is empty, the thread blocks until a producer adds an item.

4. **Termination:**  
   When the system is signaled to stop (e.g., after processing a certain number of items or via a control flag), threads complete their remaining work and terminate gracefully.

## Typical Files

Depending on the language and implementation, the folder may include:

- `ProducerConsumerSystem.java` / `ProducerConsumerSystem.py` / `main.cpp`: Main entry point.
- `Producer.java` / `Producer.py` / `Producer.cpp`: Producer thread logic.
- `Consumer.java` / `Consumer.py` / `Consumer.cpp`: Consumer thread logic.
- `Buffer.java` / `Buffer.py` / `Buffer.cpp`: Shared buffer.
- `utils/`: Utility functions and classes.
- `README.md`: This documentation file.

## Usage

### Prerequisites

- Install the required language runtime (e.g., Java, Python, C++).
- (Optional) Install dependencies listed in the repository or subfolder.

### Running the System

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/naman-sriv/multithreading_experiments.git
   cd multithreading_experiments/ProducerConsumerSystem
   ```

2. **Build and Run:**
   - **Java:**
     ```bash
     javac *.java
     java ProducerConsumerSystem
     ```
   - **Python:**
     ```bash
     python ProducerConsumerSystem.py
     ```
   - **C++:**
     ```bash
     g++ -std=c++11 *.cpp -o ProducerConsumerSystem -lpthread
     ./ProducerConsumerSystem
     ```

3. **Configure Parameters:**
   Adjust thread counts, buffer size, or other parameters in the source code or configuration files as needed.

### Sample Output

```
Producer 1 produced item 42. Buffer size: 1
Consumer 2 consumed item 42. Buffer size: 0
...
```

## Customization

- **Change Buffer Size:**  
  Modify the buffer initialization parameter to adjust how many items can be held concurrently.

- **Adjust Thread Counts:**  
  Set the number of producer and consumer threads for different workload scenarios.

- **Logging Level:**  
  Change log verbosity for debugging or performance analysis.

## Key Concepts Demonstrated

- Mutual exclusion and thread safety
- Inter-thread communication (wait/notify or equivalent)
- Handling resource contention
- Avoiding deadlocks and starvation
- Graceful thread termination

## References

- [Producer-Consumer Problem (Wikipedia)](https://en.wikipedia.org/wiki/Producer–consumer_problem)
- [Java Concurrency Utilities](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [Python threading](https://docs.python.org/3/library/threading.html)
- [C++11 Concurrency](https://en.cppreference.com/w/cpp/thread)

## License

MIT License

---

**Author:** [naman-sriv](https://github.com/naman-sriv)

For questions or contributions, please open an issue or pull request in the main repository.
