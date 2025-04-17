<?php

namespace App\Controller;

use App\Entity\TrainTrajet;
use App\Form\TrainTrajetType;
use App\Repository\TrainTrajetRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/train/trajet')]
final class TrainTrajetController extends AbstractController
{
    #[Route(name: 'app_train_trajet_index', methods: ['GET'])]
    public function index(TrainTrajetRepository $trainTrajetRepository): Response
    {
        return $this->render('train_trajet/index.html.twig', [
            'train_trajets' => $trainTrajetRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_train_trajet_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $trainTrajet = new TrainTrajet();
        $form = $this->createForm(TrainTrajetType::class, $trainTrajet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($trainTrajet);
            $entityManager->flush();

            return $this->redirectToRoute('app_train_trajet_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('train_trajet/new.html.twig', [
            'train_trajet' => $trainTrajet,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_train_trajet_show', methods: ['GET'])]
    public function show(TrainTrajet $trainTrajet): Response
    {
        return $this->render('train_trajet/show.html.twig', [
            'train_trajet' => $trainTrajet,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_train_trajet_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, TrainTrajet $trainTrajet, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TrainTrajetType::class, $trainTrajet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_train_trajet_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('train_trajet/edit.html.twig', [
            'train_trajet' => $trainTrajet,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_train_trajet_delete', methods: ['POST'])]
    public function delete(Request $request, TrainTrajet $trainTrajet, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$trainTrajet->getId(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($trainTrajet);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_train_trajet_index', [], Response::HTTP_SEE_OTHER);
    }
}
