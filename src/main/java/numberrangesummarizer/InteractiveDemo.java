package numberrangesummarizer;

import java.util.Collection;
import java.util.Scanner;

/**
 * Interactive demo class that allows users to input their own integers
 * and see the number range summarization in action.
 */
public class InteractiveDemo {
    
    public static void main(String[] args) {
        NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Number Range Summarizer Interactive Demo ===");
        System.out.println("Enter comma-separated integers to see them summarized as ranges.");
        System.out.println("Examples:");
        System.out.println("  - 1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        System.out.println("  - 1, 2, 3, 4, 5");
        System.out.println("  - 1, 3, 5, 7, 9");
        System.out.println("Type 'quit' or 'exit' to stop the program.\n");
        
        while (true) {
            System.out.print("Enter your numbers: ");
            String input = scanner.nextLine().trim();
            
            // Check for exit commands
            if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            // Handle empty input
            if (input.isEmpty()) {
                System.out.println("Please enter some numbers or type 'quit' to exit.\n");
                continue;
            }
            
            try {
                // Process the input
                System.out.println("Processing: " + input);
                
                // Step 1: Collect and process the input
                Collection<Integer> collected = summarizer.collect(input);
                System.out.println("Parsed numbers: " + collected);
                
                // Step 2: Summarize the collection
                String result = summarizer.summarizeCollection(collected);
                System.out.println("Summarized: " + result);
                
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please make sure all values are valid integers separated by commas.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            
            System.out.println(); // Add blank line for readability
        }
        
        scanner.close();
    }
}
