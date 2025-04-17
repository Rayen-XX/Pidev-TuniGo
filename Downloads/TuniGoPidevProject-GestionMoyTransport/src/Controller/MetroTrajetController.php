<?php

namespace App\Controller;

use App\Entity\MetroTrajet;
use App\Form\MetroTrajetType;
use App\Repository\MetroTrajetRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/metro/trajet')]
final class MetroTrajetController extends AbstractController
{
    #[Route(name: 'app_metro_trajet_index', methods: ['GET'])]
    public function index(MetroTrajetRepository $metroTrajetRepository): Response
    {
        return $this->render('metro_trajet/index.html.twig', [
            'metro_trajets' => $metroTrajetRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_metro_trajet_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $metroTrajet = new MetroTrajet();
        $form = $this->createForm(MetroTrajetType::class, $metroTrajet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($metroTrajet);
            $entityManager->flush();

            return $this->redirectToRoute('app_metro_trajet_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('metro_trajet/new.html.twig', [
            'metro_trajet' => $metroTrajet,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_metro_trajet_show', methods: ['GET'])]
    public function show(MetroTrajet $metroTrajet): Response
    {
        return $this->render('metro_trajet/show.html.twig', [
            'metro_trajet' => $metroTrajet,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_metro_trajet_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, MetroTrajet $metroTrajet, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(MetroTrajetType::class, $metroTrajet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_metro_trajet_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('metro_trajet/edit.html.twig', [
            'metro_trajet' => $metroTrajet,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_metro_trajet_delete', methods: ['POST'])]
    public function delete(Request $request, MetroTrajet $metroTrajet, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$metroTrajet->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($metroTrajet);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_metro_trajet_index', [], Response::HTTP_SEE_OTHER);
    }
}
