package numberrangesummarizer;

import java.util.Collection;

/**
 * Command-line demo that processes input from command-line arguments.
 * Usage: java CommandLineDemo "1,3,6,7,8,12,13,14,15,21,22,23,24,31"
 */
public class CommandLineDemo {
    
    public static void main(String[] args) {
        NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
        
        if (args.length == 0) {
            System.out.println("Usage: java CommandLineDemo \"1,3,6,7,8,12,13,14,15,21,22,23,24,31\"");
            System.out.println("Or run the interactive demo: java InteractiveDemo");
            return;
        }
        
        String input = args[0];
        System.out.println("Input: " + input);
        
        try {
            // Step 1: Collect and process the input
            Collection<Integer> collected = summarizer.collect(input);
            System.out.println("Parsed numbers: " + collected);
            
            // Step 2: Summarize the collection
            String result = summarizer.summarizeCollection(collected);
            System.out.println("Result: " + result);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please make sure all values are valid integers separated by commas.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
