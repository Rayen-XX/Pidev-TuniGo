<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class HomeController extends AbstractController
{
    #[Route('/', name: 'app_home')]
    public function index(): Response
    {
        // This route will require authentication based on our security settings
        return $this->render('home/index.html.twig', [
            'user' => $this->getUser(),
        ]);
    }
    
    #[Route('/home', name: 'app_home_alt')]
    public function homeRedirect(): Response
    {
        // Redirect /home to the root route
        return $this->redirectToRoute('app_home');
    }
} 