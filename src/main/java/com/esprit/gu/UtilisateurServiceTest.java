/*package com.esprit.gu;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;

public class UtilisateurServiceTest {
    public static void main(String[] args) {
        UtilisateurService userService = new UtilisateurService();

        // Testing User Registration
        Utilisateur newUtilisateur = new Utilisateur("Belkhouja", "Rayen", "rayenbelkffhouja22@gmail.com", "rayen123", "29511139", "utilisateur");
        boolean registered = userService.register(newUtilisateur);
        if (registered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed.");
        }

        // Testing Login with correct credentials
        boolean loginSuccess = userService.login("testuser@example.com", "testpass");
        if (loginSuccess) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed.");
        }


        // Testing Login with incorrect credentials
        boolean loginFail = userService.login("testuser@example.com", "wrongpass");
        if (!loginFail) {
            System.out.println("Login failed with wrong password, as expected.");
        }

        // Testing Update Functionality
        Utilisateur updatedUtilisateur = new Utilisateur(2, "mourad", "belkhouja", "testuser@example.com", "newpass", "987654321", "utilisateur");
        boolean updated = userService.updateUtilisateur(updatedUtilisateur);
        if (updated) {
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User update failed.");
        }

// Testing Delete Functionality
        boolean deleted = userService.deleteUtilisateur(5);
        if (deleted) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User deletion failed.");
        }


    }


}*/
