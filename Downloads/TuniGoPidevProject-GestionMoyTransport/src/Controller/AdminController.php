<?php
/*
namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Utilisateur;
use App\Repository\ReclamationRepository;

class AdminController extends AbstractController
{
    #[Route('/admin/dashboard', name: 'app_admin_dashboard')]
    public function index(EntityManagerInterface $entityManager): Response
    {
        // Check if user is logged in and has admin role
        $this->denyAccessUnlessGranted('ROLE_ADMIN');
        
        // Get user count
        $userCount = $entityManager->getRepository(Utilisateur::class)->count([]);
        
        // Get recent users
        $recentUsers = $entityManager->getRepository(Utilisateur::class)
            ->findBy([], ['idUtilisateur' => 'DESC'], 5);
        
        // Reservation count - you will need to implement this when you have the entity
        $reservationCount = 0;
        
        // Complaint count - you will need to implement this when you have the entity
        $complaintCount = 0;
        
        // Transport count - you will need to implement this when you have the entity
        $transportCount = 0;
        
        return $this->render('admin/dashboard.html.twig', [
            'userCount' => $userCount,
            'recentUsers' => $recentUsers,
            'reservationCount' => $reservationCount,
            'complaintCount' => $complaintCount,
            'transportCount' => $transportCount,
        ]);
    }

   


} */

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Utilisateur;
use App\Repository\ReclamationRepository;

class AdminController extends AbstractController
{
    #[Route('/admin/dashboard', name: 'app_admin_dashboard')]
    public function index(
        EntityManagerInterface $entityManager,
        ReclamationRepository $reclamationRepository
    ): Response {
        // Vérification des droits
        $this->denyAccessUnlessGranted('ROLE_ADMIN');

        // Nombre total d'utilisateurs
        $userCount = $entityManager->getRepository(Utilisateur::class)->count([]);

        // Derniers utilisateurs
        $recentUsers = $entityManager->getRepository(Utilisateur::class)
            ->findBy([], ['idUtilisateur' => 'DESC'], 5);

        // 📊 Répartition des réclamations par type
        $typeStats = $reclamationRepository->getStatsByType();

        $types = [];
        $typeCounts = [];

        foreach ($typeStats as $stat) {
            $types[] = $stat['type'];
            $typeCounts[] = (int) $stat['count'];
        }

        $totalByType = array_sum($typeCounts);
        $percentages = [];

        foreach ($typeCounts as $count) {
            $percentages[] = $totalByType > 0 ? round(($count / $totalByType) * 100, 2) : 0;
        }

        // 📅 Réclamations par mois
        $monthlyStats = $reclamationRepository->getReclamationsGroupedByMonth();

        $months = [];
        $monthlyCounts = [];

        foreach ($monthlyStats as $stat) {
            $months[] = $stat['month'];
            $monthlyCounts[] = (int) $stat['count'];
        }

        // 🔢 Données factices pour d'autres compteurs
        $reservationCount = 0;
        $complaintCount = 0;
        $transportCount = 0;

        return $this->render('admin/dashboard.html.twig', [
            'userCount' => $userCount,
            'recentUsers' => $recentUsers,
            'reservationCount' => $reservationCount,
            'complaintCount' => $complaintCount,
            'transportCount' => $transportCount,

            // Pour le pie chart
            'types' => $types,
            'typeCounts' => $typeCounts,
            'percentages' => $percentages,
            'total' => $totalByType,

            // Pour le bar chart mensuel
            'months' => $months,
            'monthlyCounts' => $monthlyCounts,
        ]);
    }
}
