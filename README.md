# Number Range Summarizer

A Java implementation that parses comma-separated integers and summarizes consecutive numbers into ranges.

## Features

- Parses comma-separated strings of numbers (handles spaces)
- Sorts numbers in ascending order and removes duplicates
- Compresses consecutive numbers into ranges (e.g., `6-8`)
- Handles invalid input with proper error messages
- Comprehensive test suite

## Usage

### 1. Interactive Demo
Run the interactive demo to input your own numbers:

```bash
mvn compile
java -cp target/classes numberrangesummarizer.InteractiveDemo
```

This will start an interactive session where you can:
- Enter comma-separated numbers
- See the parsed and summarized results
- Type 'quit' or 'exit' to stop

### 2. Command Line Demo
Process numbers directly from command line:

```bash
mvn compile
java -cp target/classes numberrangesummarizer.CommandLineDemo "1,3,6,7,8,12,13,14,15,21,22,23,24,31"
```

### 3. Programmatic Usage
```java
NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

// Parse input
Collection<Integer> numbers = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");

// Summarize ranges
String result = summarizer.summarizeCollection(numbers);
// Result: "1, 3, 6-8, 12-15, 21-24, 31"
```

## Examples

| Input | Output |
|-------|--------|
| `1,3,6,7,8,12,13,14,15,21,22,23,24,31` | `1, 3, 6-8, 12-15, 21-24, 31` |
| `1, 2, 3, 4, 5` | `1-5` |
| `1, 3, 5, 7, 9` | `1, 3, 5, 7, 9` |
| `1, 2, 3, 5, 6, 7, 10` | `1-3, 5-7, 10` |

## Running Tests

```bash
mvn test
```

## Requirements

- Java 8 or higher
- Maven 3.6 or higher
