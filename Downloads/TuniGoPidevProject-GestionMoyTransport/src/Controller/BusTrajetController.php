<?php

namespace App\Controller;

use App\Entity\BusTrajet;
use App\Form\BusTrajetType;
use App\Repository\BusTrajetRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/bus/trajet')]
final class BusTrajetController extends AbstractController
{
    #[Route(name: 'app_bus_trajet_index', methods: ['GET'])]
    public function index(BusTrajetRepository $busTrajetRepository): Response
    {
        return $this->render('bus_trajet/index.html.twig', [
            'bus_trajets' => $busTrajetRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_bus_trajet_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $busTrajet = new BusTrajet();
        $form = $this->createForm(BusTrajetType::class, $busTrajet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($busTrajet);
            $entityManager->flush();

            return $this->redirectToRoute('app_bus_trajet_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('bus_trajet/new.html.twig', [
            'bus_trajet' => $busTrajet,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_bus_trajet_show', methods: ['GET'])]
    public function show(BusTrajet $busTrajet): Response
    {
        return $this->render('bus_trajet/show.html.twig', [
            'bus_trajet' => $busTrajet,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_bus_trajet_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, BusTrajet $busTrajet, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(BusTrajetType::class, $busTrajet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_bus_trajet_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('bus_trajet/edit.html.twig', [
            'bus_trajet' => $busTrajet,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_bus_trajet_delete', methods: ['POST'])]
    public function delete(Request $request, BusTrajet $busTrajet, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$busTrajet->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($busTrajet);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_bus_trajet_index', [], Response::HTTP_SEE_OTHER);
    }
}
