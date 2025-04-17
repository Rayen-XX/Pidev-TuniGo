<?php

namespace App\Controller;

use App\Entity\Trajetbus;
use App\Form\TrajetbusType;
use App\Repository\TrajetbusRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/trajetbus')]
final class TrajetbusController extends AbstractController
{
    #[Route(name: 'app_trajetbus_index', methods: ['GET'])]
    public function index(TrajetbusRepository $trajetbuRepository): Response
    {
        return $this->render('trajetbus/index.html.twig', [
            'trajetbuses' => $trajetbuRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_trajetbus_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $trajetbu = new Trajetbus();
        $form = $this->createForm(TrajetbusType::class, $trajetbu);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($trajetbu);
            $entityManager->flush();

            return $this->redirectToRoute('app_trajetbus_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('trajetbus/new.html.twig', [
            'trajetbu' => $trajetbu,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrajetBus}', name: 'app_trajetbus_show', methods: ['GET'])]
    public function show(Trajetbus $trajetbu): Response
    {
        return $this->render('trajetbus/show.html.twig', [
            'trajetbu' => $trajetbu,
        ]);
    }

    #[Route('/{idTrajetBus}/edit', name: 'app_trajetbus_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Trajetbus $trajetbu, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TrajetbusType::class, $trajetbu);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_trajetbus_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('trajetbus/edit.html.twig', [
            'trajetbu' => $trajetbu,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrajetBus}', name: 'app_trajetbus_delete', methods: ['POST'])]
    public function delete(Request $request, Trajetbus $trajetbu, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$trajetbu->getIdTrajetBus(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($trajetbu);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_trajetbus_index', [], Response::HTTP_SEE_OTHER);
    }
}
