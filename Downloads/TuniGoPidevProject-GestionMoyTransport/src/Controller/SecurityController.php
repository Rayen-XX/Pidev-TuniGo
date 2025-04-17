<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Psr\Log\LoggerInterface;

class SecurityController extends AbstractController
{
    #[Route('/login', name: 'app_login')]
    public function login(AuthenticationUtils $authenticationUtils, LoggerInterface $logger): Response
    {
        // If the user is already logged in, redirect to home
        if ($this->getUser()) {
            $logger->info('Already logged in user tried to access login page');
            return $this->redirectToRoute('app_home');
        }

        // Get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        
        // Log the error if there is one
        if ($error) {
            $logger->error('Login error: ' . $error->getMessage());
        }
        
        // Last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();
        $logger->info('Login page accessed with last username: ' . $lastUsername);

        return $this->render('security/login.html.twig', [
            'last_username' => $lastUsername,
            'error' => $error,
        ]);
    }

    #[Route('/logout', name: 'app_logout')]
    public function logout(): Response
    {
        // This code will never be executed because the logout is handled by the security system
        return $this->redirectToRoute('app_login');
    }
} 