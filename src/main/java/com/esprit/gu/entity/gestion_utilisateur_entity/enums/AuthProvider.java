package com.esprit.gu.entity.gestion_utilisateur_entity.enums;

/**
 * Enum representing different authentication providers.
 * This is used to track how users authenticate with the application.
 */
public enum AuthProvider {
    /**
     * Regular local authentication (username/password)
     */
    LOCAL,
    
    /**
     * Authentication via Google OAuth
     */
    GOOGLE,
    
    /**
     * Authentication via Facebook OAuth
     */
    FACEBOOK,
    
    /**
     * Authentication via Twitter (X) OAuth
     */
    TWITTER
} 