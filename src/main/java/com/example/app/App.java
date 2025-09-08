package com.example.app;

/**
 * A simple main class for the minimal Maven project.
 */
public class App {
    
    /**
     * Main method that prints a greeting message.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello, Maven World!");
        System.out.println("This is a minimal Maven project.");
    }
    
    /**
     * A simple method that returns a greeting message.
     * 
     * @param name the name to greet
     * @return a greeting message
     */
    public String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
}
