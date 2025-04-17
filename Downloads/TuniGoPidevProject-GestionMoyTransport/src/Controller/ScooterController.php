<?php

namespace App\Controller;

use App\Entity\Scooter;
use App\Form\ScooterType;
use App\Repository\ScooterRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/scooter')]
final class ScooterController extends AbstractController
{
    #[Route(name: 'app_scooter_index', methods: ['GET'])]
    public function index(ScooterRepository $scooterRepository): Response
    {
        return $this->render('scooter/index.html.twig', [
            'scooters' => $scooterRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_scooter_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $scooter = new Scooter();
    
        $scooter->setTempsReservation(new \DateTime()); // Date actuelle
        $scooter->setTempsArrivee(new \DateTime()); // Date actuelle
    
        $form = $this->createForm(ScooterType::class, $scooter);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Persistez l'entité après avoir défini les valeurs
            $entityManager->persist($scooter);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_scooter_index', [], Response::HTTP_SEE_OTHER);
        }
    
        return $this->render('scooter/new.html.twig', [
            'scooter' => $scooter,
            'form' => $form,
        ]);
    }
    

    #[Route('/{idScooter}', name: 'app_scooter_show', methods: ['GET'])]
    public function show(Scooter $scooter): Response
    {
        return $this->render('scooter/show.html.twig', [
            'scooter' => $scooter,
        ]);
    }

    #[Route('/{idScooter}/edit', name: 'app_scooter_edit', methods: ['POST'])]
    public function edit(
        Request $request,
        ScooterRepository $scooterRepository,
        EntityManagerInterface $entityManager,
        int $idScooter
    ): JsonResponse {
        $scooter = $scooterRepository->find($idScooter);
    
        if (!$scooter) {
            return new JsonResponse(['success' => false, 'message' => 'Scooter not found'], 404);
        }
    
        $numeroScooter = $request->request->get('numeroScooter');
        $localisationScooter = $request->request->get('localisationScooter');
        $idReservation = $request->request->get('idReservation');
        $isDisponible = $request->request->get('isDisponible');
        $tempsReservation = $request->request->get('tempsReservation');
        $tempsArrivee = $request->request->get('tempsArrivee');
    
        if ($numeroScooter !== null) {
            $scooter->setNumeroScooter($numeroScooter);
        }
    
        if ($localisationScooter !== null) {
            $scooter->setLocalisationScooter($localisationScooter);
        }
    
        if ($idReservation !== null) {
            $scooter->setIdReservation($idReservation);
        }
    
        if ($isDisponible !== null) {
            // Correction ici :
            $scooter->setIsDisponible(in_array($isDisponible, ['1', 1, true, 'true'], true));
        }
    
        if ($tempsReservation !== null) {
            try {
                $scooter->setTempsReservation(new \DateTime($tempsReservation));
            } catch (\Exception $e) {
                return new JsonResponse(['success' => false, 'message' => 'Invalid reservation time'], 400);
            }
        }
    
        if ($tempsArrivee !== null) {
            try {
                // Correction ici :
                $scooter->setTempsArrivee(new \DateTime($tempsArrivee));
            } catch (\Exception $e) {
                return new JsonResponse(['success' => false, 'message' => 'Invalid arrival time'], 400);
            }
        }
    
        $entityManager->persist($scooter); // Optionnel mais conseillé
        $entityManager->flush();
    
        return new JsonResponse(['success' => true, 'message' => 'Scooter updated successfully']);
    }
    

    #[Route('/{idScooter}', name: 'app_scooter_delete', methods: ['POST'])]
    public function delete(Request $request, Scooter $scooter, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$scooter->getIdScooter(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($scooter);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_scooter_index', [], Response::HTTP_SEE_OTHER);
    }
}
