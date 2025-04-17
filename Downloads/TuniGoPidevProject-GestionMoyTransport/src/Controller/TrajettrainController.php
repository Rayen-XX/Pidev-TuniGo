<?php

namespace App\Controller;

use App\Entity\Trajettrain;
use App\Form\TrajettrainType;
use App\Repository\TrajettrainRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/trajettrain')]
final class TrajettrainController extends AbstractController
{
    #[Route(name: 'app_trajettrain_index', methods: ['GET'])]
    public function index(TrajettrainRepository $trajettrainRepository): Response
    {
        return $this->render('trajettrain/index.html.twig', [
            'trajettrains' => $trajettrainRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_trajettrain_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $trajettrain = new Trajettrain();
        $form = $this->createForm(TrajettrainType::class, $trajettrain);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($trajettrain);
            $entityManager->flush();

            return $this->redirectToRoute('app_trajettrain_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('trajettrain/new.html.twig', [
            'trajettrain' => $trajettrain,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrajetTrain}', name: 'app_trajettrain_show', methods: ['GET'])]
    public function show(Trajettrain $trajettrain): Response
    {
        return $this->render('trajettrain/show.html.twig', [
            'trajettrain' => $trajettrain,
        ]);
    }

    #[Route('/{idTrajetTrain}/edit', name: 'app_trajettrain_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Trajettrain $trajettrain, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TrajettrainType::class, $trajettrain);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_trajettrain_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('trajettrain/edit.html.twig', [
            'trajettrain' => $trajettrain,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrajetTrain}', name: 'app_trajettrain_delete', methods: ['POST'])]
    public function delete(Request $request, Trajettrain $trajettrain, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$trajettrain->getIdTrajetTrain(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($trajettrain);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_trajettrain_index', [], Response::HTTP_SEE_OTHER);
    }
}
