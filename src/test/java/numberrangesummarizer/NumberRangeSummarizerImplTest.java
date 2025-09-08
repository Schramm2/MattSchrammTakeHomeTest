package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test class for NumberRangeSummarizerImpl
 * Tests all behavioral requirements and edge cases
 */
public class NumberRangeSummarizerImplTest {

    private NumberRangeSummarizerImpl summarizer;

    @BeforeEach
    void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }

    // 1) Sample from brief
    @Test
    void testSampleFromBrief() {
        // Arrange
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    // 2) Unsorted input + duplicates removed
    @Test
    void testUnsortedInputWithDuplicatesRemoved() {
        // Arrange
        String input = " 3, 1, 2, 2, 5, 4 ";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("1-5", result);
    }

    // 3) Single number
    @Test
    void testSingleNumber() {
        // Arrange
        String input = "42";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("42", result);
    }

    // 4) Negatives and mixed values
    @Test
    void testNegativesAndMixedValues() {
        // Arrange
        String input = "-3,-2,-1,1,2";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("-3--1, 1-2", result);
    }

    // 5) Empty input becomes empty output
    @Test
    void testEmptyInputBecomesEmptyOutput() {
        // Arrange
        String input = "";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertTrue(collected.isEmpty());
        assertEquals("", result);
    }

    // 6) Only spaces / blank tokens ignored
    @Test
    void testOnlySpacesAndBlankTokensIgnored() {
        // Arrange
        String input = " , , 10 , 11 , ";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("10-11", result);
    }

    // 7) Invalid token policy (fail fast)
    @Test
    void testInvalidTokenPolicyFailsFast() {
        // Arrange
        String input = "1,a,3";
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect(input);
        });
        
        assertTrue(exception.getMessage().contains("Invalid number format"));
        assertTrue(exception.getMessage().contains("'a'"));
    }

    // 8) Non-consecutive separation
    @Test
    void testNonConsecutiveSeparation() {
        // Arrange
        String input = "1,4,5,9";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("1, 4-5, 9", result);
    }

    // 9) Large consecutive run (performance sanity)
    @Test
    void testLargeConsecutiveRunPerformance() {
        // Arrange - Generate numbers 1 to 10000
        String input = IntStream.rangeClosed(1, 10000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        
        // Act
        long startTime = System.currentTimeMillis();
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        long endTime = System.currentTimeMillis();
        
        // Assert
        assertEquals("1-10000", result);
        assertTrue(endTime - startTime < 5000, "Performance test should complete within 5 seconds");
        assertEquals(10000, collected.size());
    }

    // 10) Null handling
    @Test
    void testNullHandling() {
        // Test collect with null
        Collection<Integer> collected = summarizer.collect(null);
        assertTrue(collected.isEmpty());
        
        // Test summarizeCollection with null
        String result = summarizer.summarizeCollection(null);
        assertEquals("", result);
    }

    // 11) Output formatting
    @Test
    void testOutputFormatting() {
        // Arrange
        String input = "1,3,5,7,9";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert - Check exact formatting
        assertEquals("1, 3, 5, 7, 9", result);
        assertFalse(result.startsWith(" "), "Result should not start with space");
        assertFalse(result.endsWith(" "), "Result should not end with space");
        assertFalse(result.contains(",,"), "Result should not contain double commas");
        assertFalse(result.contains("1-1"), "Singletons should not be printed as x-x");
    }

    // 12) Sorting guarantee
    @Test
    void testSortingGuaranteeWithUnsortedCollection() {
        // Arrange - Create unsorted collection directly
        Collection<Integer> unsortedInput = Arrays.asList(31, 1, 15, 3, 22, 6, 24, 7, 12, 8, 21, 13, 14, 23);
        
        // Act
        String result = summarizer.summarizeCollection(unsortedInput);
        
        // Assert
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    // Additional edge cases and comprehensive tests

    @Test
    void testCollectWithSpacesAndDuplicates() {
        // Arrange
        String input = "1, 3, 6, 7, 7, 8, 12, 13, 13, 14, 15, 21, 22, 23, 24, 31";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        
        // Assert
        assertEquals(Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31), collected);
    }

    @Test
    void testCollectWithEmptyTokens() {
        // Arrange
        String input = "1,,3,,6,7,8,12";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        
        // Assert
        assertEquals(Arrays.asList(1, 3, 6, 7, 8, 12), collected);
    }

    @Test
    void testCollectWithWhitespaceOnly() {
        // Arrange
        String input = "   ,  ,  ,  ";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        
        // Assert
        assertTrue(collected.isEmpty());
    }

    @Test
    void testSummarizeCollectionWithSingleNumber() {
        // Arrange
        Collection<Integer> input = Arrays.asList(42);
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert
        assertEquals("42", result);
    }

    @Test
    void testSummarizeCollectionWithAllConsecutive() {
        // Arrange
        Collection<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert
        assertEquals("1-5", result);
    }

    @Test
    void testSummarizeCollectionWithEmptyInput() {
        // Arrange
        Collection<Integer> input = new ArrayList<>();
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert
        assertEquals("", result);
    }

    @Test
    void testSummarizeCollectionWithNegativeNumbers() {
        // Arrange
        Collection<Integer> input = Arrays.asList(-5, -4, -3, -1, 0, 1, 3, 4, 5);
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert
        assertEquals("-5--3, -1-1, 3-5", result);
    }

    @Test
    void testSummarizeCollectionWithZero() {
        // Arrange
        Collection<Integer> input = Arrays.asList(-1, 0, 1, 2, 3);
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert
        assertEquals("-1-3", result);
    }

    @Test
    void testCollectWithLargeNumbers() {
        // Arrange
        String input = "1000000,1000001,1000002,2000000";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("1000000-1000002, 2000000", result);
    }

    @Test
    void testCollectWithMixedValidAndInvalidTokens() {
        // Arrange
        String input = "1,2,invalid,3,4";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect(input);
        });
    }

    @Test
    void testSummarizeCollectionWithDuplicateNumbers() {
        // Arrange - Create collection with duplicates (though collect should remove them)
        Collection<Integer> input = Arrays.asList(1, 1, 2, 2, 3, 3, 5, 5);
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert - The implementation doesn't remove duplicates in summarizeCollection, only in collect
        assertEquals("1, 1-2, 2-3, 3, 5, 5", result);
    }

    @Test
    void testEndToEndWithComplexExample() {
        // Arrange
        String input = "100, 1, 3, 3, 2, 2, 4, 4, 5, 99, 98, 97, 96, 95, 10, 11, 12, 13, 14, 15";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("1-5, 10-15, 95-100", result);
    }

    @Test
    void testCollectWithOnlyCommas() {
        // Arrange
        String input = ",,,,";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        
        // Assert
        assertTrue(collected.isEmpty());
    }

    @Test
    void testSummarizeCollectionWithSingleElementRange() {
        // Arrange
        Collection<Integer> input = Arrays.asList(1, 3, 5, 7, 9);
        
        // Act
        String result = summarizer.summarizeCollection(input);
        
        // Assert
        assertEquals("1, 3, 5, 7, 9", result);
    }

    @Test
    void testCollectWithLeadingAndTrailingSpaces() {
        // Arrange
        String input = "  1, 2, 3  ";
        
        // Act
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        
        // Assert
        assertEquals("1-3", result);
    }
}