package com.esprit.gu.util;

/**
 * This class provides a bridge to access RestFB classes from the modular application.
 * It helps overcome JPMS restrictions when accessing classes from unnamed modules.
 */
public class RestFBBridge {
    
    // Include methods that will be used to access RestFB functionality
    
    /**
     * Ensures the RestFB classes are loaded and accessible.
     * Call this method before using RestFB.
     */
    public static void ensureRestFBAccess() {
        try {
            // Force loading of key RestFB classes
            Class.forName("com.restfb.DefaultFacebookClient");
            Class.forName("com.restfb.FacebookClient");
            Class.forName("com.restfb.Parameter");
            Class.forName("com.restfb.Version");
            System.out.println("RestFB classes loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load RestFB classes: " + e.getMessage());
        }
    }
} 