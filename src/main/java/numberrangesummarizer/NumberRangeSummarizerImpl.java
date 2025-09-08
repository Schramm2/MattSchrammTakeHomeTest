package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of NumberRangeSummarizer interface.
 * 
 * This class provides functionality to:
 * 1. Parse comma-separated strings of numbers into sorted, unique collections
 * 2. Summarize collections of integers by compressing consecutive numbers into ranges
 */
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    /**
     * Collects and processes a comma-separated string of numbers.
     * 
     * @param input comma-separated string of numbers (possibly with spaces)
     * @return sorted collection of unique integers
     * @throws IllegalArgumentException if any token cannot be parsed as an integer
     */
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .map(this::parseInteger)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Summarizes a collection of integers by compressing consecutive numbers into ranges.
     * 
     * @param input sorted collection of integers
     * @return comma-separated string with ranges compressed (e.g., "1, 3, 6-8, 12-15")
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        
        List<Integer> sortedNumbers = new ArrayList<>(input);
        Collections.sort(sortedNumbers);
        
        List<String> result = new ArrayList<>();
        int start = sortedNumbers.get(0);
        int end = start;
        
        for (int i = 1; i < sortedNumbers.size(); i++) {
            int current = sortedNumbers.get(i);
            
            if (current == end + 1) {
                // Consecutive number, extend the range
                end = current;
            } else {
                // Non-consecutive number, add the current range and start a new one
                result.add(formatRange(start, end));
                start = current;
                end = current;
            }
        }
        
        // Add the last range
        result.add(formatRange(start, end));
        
        return String.join(", ", result);
    }
    
    /**
     * Parses a string token to an integer, throwing IllegalArgumentException if invalid.
     * 
     * @param token the string token to parse
     * @return the parsed integer
     * @throws IllegalArgumentException if the token cannot be parsed as an integer
     */
    private Integer parseInteger(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: '" + token + "'", e);
        }
    }
    
    /**
     * Formats a range as either a single number or start-end format.
     * 
     * @param start the start of the range
     * @param end the end of the range
     * @return formatted string representation of the range
     */
    private String formatRange(int start, int end) {
        if (start == end) {
            return String.valueOf(start);
        } else {
            return start + "-" + end;
        }
    }
}
