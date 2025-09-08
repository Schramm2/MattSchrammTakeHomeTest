package numberrangesummarizer;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for NumberRangeSummarizerImpl
 */
public class NumberRangeSummarizerImplTest {

    private NumberRangeSummarizerImpl summarizer;

    @BeforeEach
    void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }

    @Test
    void testCollectWithValidInput() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> result = summarizer.collect(input);
        
        assertEquals(Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31), result);
    }

    @Test
    void testCollectWithSpaces() {
        String input = "1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31";
        Collection<Integer> result = summarizer.collect(input);
        
        assertEquals(Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31), result);
    }

    @Test
    void testCollectWithDuplicates() {
        String input = "1,3,3,6,7,7,8,12";
        Collection<Integer> result = summarizer.collect(input);
        
        assertEquals(Arrays.asList(1, 3, 6, 7, 8, 12), result);
    }

    @Test
    void testCollectWithEmptyTokens() {
        String input = "1,,3,,6,7,8,12";
        Collection<Integer> result = summarizer.collect(input);
        
        assertEquals(Arrays.asList(1, 3, 6, 7, 8, 12), result);
    }

    @Test
    void testCollectWithInvalidInput() {
        String input = "1,3,abc,6,7";
        
        assertThrows(IllegalArgumentException.class, () -> {
            summarizer.collect(input);
        });
    }

    @Test
    void testCollectWithEmptyString() {
        String input = "";
        Collection<Integer> result = summarizer.collect(input);
        
        assertTrue(result.isEmpty());
    }

    @Test
    void testCollectWithNullInput() {
        Collection<Integer> result = summarizer.collect(null);
        
        assertTrue(result.isEmpty());
    }

    @Test
    void testSummarizeCollectionWithExample() {
        Collection<Integer> input = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
        String result = summarizer.summarizeCollection(input);
        
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    @Test
    void testSummarizeCollectionWithSingleNumbers() {
        Collection<Integer> input = Arrays.asList(1, 3, 5, 7, 9);
        String result = summarizer.summarizeCollection(input);
        
        assertEquals("1, 3, 5, 7, 9", result);
    }

    @Test
    void testSummarizeCollectionWithAllConsecutive() {
        Collection<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        String result = summarizer.summarizeCollection(input);
        
        assertEquals("1-5", result);
    }

    @Test
    void testSummarizeCollectionWithEmptyInput() {
        Collection<Integer> input = Arrays.asList();
        String result = summarizer.summarizeCollection(input);
        
        assertEquals("", result);
    }

    @Test
    void testSummarizeCollectionWithNullInput() {
        String result = summarizer.summarizeCollection(null);
        
        assertEquals("", result);
    }

    @Test
    void testSummarizeCollectionWithUnsortedInput() {
        Collection<Integer> input = Arrays.asList(3, 1, 6, 8, 7, 12, 15, 14, 13, 21, 24, 23, 22, 31);
        String result = summarizer.summarizeCollection(input);
        
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    @Test
    void testEndToEndWithExample() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> collected = summarizer.collect(input);
        String summarized = summarizer.summarizeCollection(collected);
        
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summarized);
    }
}
