<?php

namespace App\Controller;

use App\Entity\Train;
use App\Form\TrainType;
use App\Repository\TrainRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/train')]
final class TrainController extends AbstractController
{
    #[Route(name: 'app_train_index', methods: ['GET'])]
    public function index(TrainRepository $trainRepository): Response
    {
        return $this->render('train/index.html.twig', [
            'trains' => $trainRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_train_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $train = new Train();
        $form = $this->createForm(TrainType::class, $train);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($train);
            $entityManager->flush();

            return $this->redirectToRoute('app_train_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('train/new.html.twig', [
            'train' => $train,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrain}', name: 'app_train_show', methods: ['GET'])]
    public function show(Train $train): Response
    {
        return $this->render('train/show.html.twig', [
            'train' => $train,
        ]);
    }
    
#[Route('/{idTrain}/edit', name: 'app_train_edit', methods: ['POST'])]

    public function edit(Request $request, TrainRepository $trainRepository, EntityManagerInterface $entityManager, $idTrain)
    {
        $train = $trainRepository->find($idTrain);

        if (!$train) {
            return new JsonResponse(['success' => false, 'message' => 'Train not found'], 404);
        }

        $numeroTrain = $request->get('numeroTrain');
        $idTrajetTrain = $request->get('idTrajetTrain');

        $train->setNumeroTrain($numeroTrain);
        $train->setIdTrajetTrain($idTrajetTrain);

        $entityManager->flush();

        return new JsonResponse(['success' => true]);
    }


    #[Route('/{idTrain}', name: 'app_train_delete', methods: ['POST'])]
    public function delete(Request $request, Train $train, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$train->getIdTrain(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($train);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_train_index', [], Response::HTTP_SEE_OTHER);
    }
}
