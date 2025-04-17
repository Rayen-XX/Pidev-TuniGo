<?php

namespace App\Controller;
use App\Entity\Parking;
use App\Entity\ReservationParking;
use App\Form\ReservationParkingType;
use App\Repository\ReservationParkingRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Security\Core\Security;

#[Route('/reservation/parking/front')]

final class FrontReservationParkingController extends AbstractController
{#[Route('/book', name: 'app_reservation_parking_book', methods: ['GET', 'POST'])]
    public function book(
        Request $request,
        EntityManagerInterface $entityManager,
        Security $security
    ): Response {
        $user = $security->getUser();
    
        if (!$user) {
            $this->addFlash('error', 'Vous devez être connecté pour réserver.');
            return $this->redirectToRoute('app_login');
        }
    
        $reservation = new ReservationParking();
    
        // Obtenir toutes les localisations uniques depuis la table Parking
        $localisations = $entityManager->getRepository(Parking::class)
            ->createQueryBuilder('p')
            ->select('DISTINCT p.localisationParking')
            ->getQuery()
            ->getSingleColumnResult();
    
        // Localisation sélectionnée via l'URL
        $selectedLocalisation = $request->query->get('localisationParking');
    
        // Liste des parkings filtrés par localisation
        $parkings = [];
        if ($selectedLocalisation) {
            $parkings = $entityManager->getRepository(Parking::class)->findBy([
                'localisationParking' => $selectedLocalisation
            ]);
        }
    
        // Traitement de la soumission du formulaire
        if ($request->isMethod('POST')) {
            $parkingId = $request->request->get('parking');
            $date = $request->request->get('date_reservation');
    
            $parking = $entityManager->getRepository(Parking::class)->find($parkingId);
    
            if ($parking) {
                $reservation->setParking($parking);
                $reservation->setUtilisateur($user);
                $reservation->setDateReservation(new \DateTime($date));
    
                $entityManager->persist($reservation);
                $entityManager->flush();
    
                $this->addFlash('success', 'Réservation confirmée !');
                return $this->redirectToRoute('app_reservation_parking_book');
            }
        }
        $reservationsUser = $entityManager->getRepository(ReservationParking::class)->findBy(
            ['utilisateur' => $user],
            ['idReservation' => 'DESC']
        );
        
    
        return $this->render('reservation_parking/front/passer.html.twig', [
            'localisations' => $localisations,
            'selected_localisation' => $selectedLocalisation,
            'parkings' => $parkings,
            'reservationsUser' => $reservationsUser
        ]);
        
    }
    
    #[Route('/{idReservation}/front', name: 'app_reservation_parking_delete_front', methods: ['POST'])]
    public function deleteFront(Request $request, ReservationParking $reservationParking, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservationParking->getIdReservation(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($reservationParking);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_reservation_parking_book', [], Response::HTTP_SEE_OTHER);
    }
   
    #[Route('/list', name: 'app_reservation_parking_list', methods: ['GET'])]
    public function list(Request $request, EntityManagerInterface $em): Response
    {
        $localisations = $em->getRepository(Parking::class)
            ->createQueryBuilder('p')
            ->select('DISTINCT p.localisationParking')
            ->getQuery()
            ->getSingleColumnResult();
    
        $selectedLocalisation = $request->query->get('localisationParking');
    
        $parkings = [];
        if ($selectedLocalisation) {
            $parkings = $em->getRepository(Parking::class)->findBy([
                'localisationParking' => $selectedLocalisation
            ]);
        }
    
        return $this->render('parking/selectionnerparking.html.twig', [
            'localisations' => $localisations,
            'selected_localisation' => $selectedLocalisation,
            'parkings' => $parkings,
        ]);
    }
    
}
