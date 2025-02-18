package com.esprit.gu.service;

public class ServiceAuthentification {

    private static String authenticatedUser;

    public ServiceAuthentification(String authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    // Méthode pour définir l'utilisateur authentifié
    public void setAuthenticatedUser(String authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    // Méthode pour récupérer l'utilisateur authentifié
    public static String getAuthenticatedUser() {
        return authenticatedUser;
    }
}
