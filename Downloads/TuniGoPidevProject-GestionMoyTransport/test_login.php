<?php

// Simple script to test direct database access and login

// Database connection parameters
$host = 'localhost';
$dbname = 'pidevtunigo';
$username = 'root';
$password = '';

try {
    // Connect to the database
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    echo "Database connection successful!\n";
    
    // Try to fetch a user
    $email = 'rayenbelkhouja00@gmail.com'; // This user exists in the database dump
    $plainPassword = '123456';    // Password from the database dump
    
    $stmt = $pdo->prepare("SELECT * FROM utilisateur WHERE emailUtilisateur = :email");
    $stmt->bindParam(':email', $email);
    $stmt->execute();
    
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
    
    if ($user) {
        echo "User found: " . $user['nomUtilisateur'] . " " . $user['prenomUtilisateur'] . "\n";
        echo "Email: " . $user['emailUtilisateur'] . "\n";
        echo "Stored password length: " . strlen($user['motDePasseUtilisateur']) . " characters\n";
        echo "Stored password: " . $user['motDePasseUtilisateur'] . "\n";
        
        if ($user['motDePasseUtilisateur'] === $plainPassword) {
            echo "Password match! Login would be successful.\n";
        } else {
            echo "Password mismatch. Login would fail.\n";
        }
    } else {
        echo "User not found\n";
    }
    
} catch (PDOException $e) {
    echo "Database connection failed: " . $e->getMessage() . "\n";
} 