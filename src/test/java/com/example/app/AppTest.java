package com.example.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the App class.
 */
public class AppTest {
    
    private App app;
    
    @BeforeEach
    void setUp() {
        app = new App();
    }
    
    @Test
    void testGetGreeting() {
        // Test with a simple name
        String result = app.getGreeting("World");
        assertEquals("Hello, World!", result);
    }
    
    @Test
    void testGetGreetingWithEmptyName() {
        // Test with empty name
        String result = app.getGreeting("");
        assertEquals("Hello, !", result);
    }
    
    @Test
    void testGetGreetingWithNullName() {
        // Test with null name
        String result = app.getGreeting(null);
        assertEquals("Hello, null!", result);
    }
    
    @Test
    void testAppInstantiation() {
        // Test that App can be instantiated
        assertNotNull(app);
    }
}
