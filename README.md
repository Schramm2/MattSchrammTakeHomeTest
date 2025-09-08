# Number Range Summarizer - Completed

A Java implementation that parses comma-separated integers and summarizes consecutive numbers into ranges.

## Problem Statement

Given a comma-separated string of integers, parse them into a sorted collection and summarize consecutive numbers into ranges.

**Sample Input/Output:**
- Input: `"1,3,6,7,8,12,13,14,15,21,22,23,24,31"`
- Output: `"1, 3, 6-8, 12-15, 21-24, 31"`

## Assumptions

- **Invalid tokens fail fast**: Any non-integer token throws `IllegalArgumentException`
- **Supports negatives**: Handles negative numbers correctly (e.g., `-3--1`)
- **Output sorted**: Numbers are always sorted in ascending order
- **Treats duplicates as set**: Duplicate numbers are removed during collection
- **Empty tokens ignored**: Empty or whitespace-only tokens are filtered out
- **Consecutive ranges**: Only consecutive numbers are compressed into ranges

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

## How to Build/Run Tests

```bash
mvn -q -DskipTests=false test
```

## Technical Details

- **Java version**: 1.8
- **Complexity**: 
  - `collect()`: O(n log n) due to sorting and duplicate removal
  - `summarizeCollection()`: O(n) for range compression
- **Dependencies**: JUnit 5 for testing

## Requirements

- Java 8 or higher
- Maven 3.6 or higher
