<?php

namespace App\Controller;

use App\Entity\Bus;
use App\Form\BusType;
use App\Repository\BusRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/bus')]
final class BusController extends AbstractController
{
    #[Route(name: 'app_bus_index', methods: ['GET'])]
    public function index(BusRepository $busRepository): Response
    {
        return $this->render('bus/index.html.twig', [
            'buses' => $busRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_bus_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $bus = new Bus();
        $form = $this->createForm(BusType::class, $bus);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($bus);
            $entityManager->flush();

            return $this->redirectToRoute('app_bus_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('bus/new.html.twig', [
            'bus' => $bus,
            'form' => $form,
        ]);
    }

    #[Route('/{idBus}', name: 'app_bus_show', methods: ['GET'])]
    public function show(Bus $bus): Response
    {
        return $this->render('bus/show.html.twig', [
            'bus' => $bus,
        ]);
    }

    #[Route('/{idBus}/edit', name: 'app_bus_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, BusRepository $busRepository, EntityManagerInterface $entityManager, $idBus): Response
    {
        $bus = $busRepository->find($idBus);

        if (!$bus) {
            return new JsonResponse(['success' => false, 'message' => 'Bus not found'], 404);
        }

        $numeroBus = $request->get('numeroBus');
        $idTrajetBus = $request->get('idTrajetBus');

        $bus->setNumeroBus($numeroBus);
        $bus->setIdTrajetBus($idTrajetBus);

        $entityManager->flush();

        return new JsonResponse(['success' => true]);
    }

    #[Route('/{idBus}', name: 'app_bus_delete', methods: ['POST'])]
    public function delete(Request $request, Bus $bus, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$bus->getIdBus(), $request->getPayload()->getString('_token'))) {
            $entityManager->remove($bus);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_bus_index', [], Response::HTTP_SEE_OTHER);
    }
}
