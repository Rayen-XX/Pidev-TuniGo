<?php

namespace App\Controller;

use App\Entity\Trajetmetro;
use App\Form\TrajetmetroType;
use App\Repository\TrajetmetroRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/trajetmetro')]
final class TrajetmetroController extends AbstractController
{
    #[Route(name: 'app_trajetmetro_index', methods: ['GET'])]
    public function index(TrajetmetroRepository $trajetmetroRepository): Response
    {
        return $this->render('trajetmetro/index.html.twig', [
            'trajetmetros' => $trajetmetroRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_trajetmetro_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $trajetmetro = new Trajetmetro();
        $form = $this->createForm(TrajetmetroType::class, $trajetmetro);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($trajetmetro);
            $entityManager->flush();

            return $this->redirectToRoute('app_trajetmetro_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('trajetmetro/new.html.twig', [
            'trajetmetro' => $trajetmetro,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrajetMetro}', name: 'app_trajetmetro_show', methods: ['GET'])]
    public function show(Trajetmetro $trajetmetro): Response
    {
        return $this->render('trajetmetro/show.html.twig', [
            'trajetmetro' => $trajetmetro,
        ]);
    }

    #[Route('/{idTrajetMetro}/edit', name: 'app_trajetmetro_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Trajetmetro $trajetmetro, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TrajetmetroType::class, $trajetmetro);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_trajetmetro_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('trajetmetro/edit.html.twig', [
            'trajetmetro' => $trajetmetro,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrajetMetro}', name: 'app_trajetmetro_delete', methods: ['POST'])]
    public function delete(Request $request, Trajetmetro $trajetmetro, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$trajetmetro->getIdTrajetMetro(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($trajetmetro);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_trajetmetro_index', [], Response::HTTP_SEE_OTHER);
    }
}
