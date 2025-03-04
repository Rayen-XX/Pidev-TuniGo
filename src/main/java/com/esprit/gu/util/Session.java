// In com.esprit.gu.util.Session.java
package com.esprit.gu.util;

import com.esprit.gu.entity.Utilisateur;

public class Session {
    private static Utilisateur currentUser;

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Utilisateur user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null;
    }
}
