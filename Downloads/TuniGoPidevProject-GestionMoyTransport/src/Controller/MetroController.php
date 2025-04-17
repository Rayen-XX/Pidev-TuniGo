<?php

namespace App\Controller;

use App\Entity\Metro;
use App\Form\MetroType;
use App\Repository\MetroRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/metro')]
final class MetroController extends AbstractController
{
    #[Route(name: 'app_metro_index', methods: ['GET'])]
    public function index(MetroRepository $metroRepository): Response
    {
        return $this->render('metro/index.html.twig', [
            'metros' => $metroRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_metro_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $metro = new Metro();
        $form = $this->createForm(MetroType::class, $metro);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($metro);
            $entityManager->flush();

            return $this->redirectToRoute('app_metro_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('metro/new.html.twig', [
            'metro' => $metro,
            'form' => $form,
        ]);
    }

    #[Route('/{idMetro}', name: 'app_metro_show', methods: ['GET'])]
    public function show(Metro $metro): Response
    {
        return $this->render('metro/show.html.twig', [
            'metro' => $metro,
        ]);
    }

    #[Route('/{idMetro}/edit', name: 'app_metro_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, MetroRepository $metroRepository, EntityManagerInterface $entityManager, $idMetro): Response
    {
        $metro = $metroRepository->find($idMetro);

        if (!$metro) {
            return new JsonResponse(['success' => false, 'message' => 'Metro not found'], 404);
        }

        $numeroMetro = $request->get('numeroMetro');
        $idTrajetMetro = $request->get('idTrajetMetro');

        $metro->setNumeroMetro($numeroMetro);
        $metro->setIdTrajetMetro($idTrajetMetro);

        $entityManager->flush();

        return new JsonResponse(['success' => true]);
    }

    #[Route('/{idMetro}', name: 'app_metro_delete', methods: ['POST'])]
    public function delete(Request $request, Metro $metro, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$metro->getIdMetro(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($metro);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_metro_index', [], Response::HTTP_SEE_OTHER);
    }
}
