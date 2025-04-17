<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class ForgotPasswordController extends AbstractController
{
    #[Route('/forgot-password', name: 'app_forgot_password')]
    public function requestReset(Request $request, UtilisateurRepository $utilisateurRepository): Response
    {
        // Step 1: Find user by email
        if ($request->isMethod('POST')) {
            $email = $request->request->get('email');
            $user = $utilisateurRepository->findOneBy(['emailUtilisateur' => $email]);

            if (!$user) {
                $this->addFlash('danger', 'Aucun compte trouvé avec cet email.');
                return $this->redirectToRoute('app_forgot_password');
            }

            // Store email in session for the next step
            $request->getSession()->set('reset_email', $email);
            return $this->redirectToRoute('app_security_question');
        }

        return $this->render('security/forgot_password.html.twig');
    }

    #[Route('/security-question', name: 'app_security_question')]
    public function securityQuestion(Request $request, UtilisateurRepository $utilisateurRepository): Response
    {
        // Get email from session
        $email = $request->getSession()->get('reset_email');
        if (!$email) {
            return $this->redirectToRoute('app_forgot_password');
        }

        $user = $utilisateurRepository->findOneBy(['emailUtilisateur' => $email]);
        if (!$user) {
            $this->addFlash('danger', 'Utilisateur non trouvé.');
            return $this->redirectToRoute('app_forgot_password');
        }

        // Step 2: Answer security question
        if ($request->isMethod('POST')) {
            $answer = $request->request->get('security_answer');
            
            // Check if answer is correct
            if ($user->getReponseSecurite() === $answer) {
                // Store user ID in session for the reset step
                $request->getSession()->set('reset_user_id', $user->getIdUtilisateur());
                return $this->redirectToRoute('app_reset_password');
            } else {
                $this->addFlash('danger', 'La réponse à la question de sécurité est incorrecte.');
            }
        }

        return $this->render('security/security_question.html.twig', [
            'question' => $user->getQuestionSecurite(),
        ]);
    }

    #[Route('/reset-password', name: 'app_reset_password')]
    public function resetPassword(Request $request, EntityManagerInterface $entityManager, UtilisateurRepository $utilisateurRepository): Response
    {
        // Get user ID from session
        $userId = $request->getSession()->get('reset_user_id');
        if (!$userId) {
            return $this->redirectToRoute('app_forgot_password');
        }

        $user = $utilisateurRepository->find($userId);
        if (!$user) {
            $this->addFlash('danger', 'Utilisateur non trouvé.');
            return $this->redirectToRoute('app_forgot_password');
        }

        // Step 3: Reset password
        if ($request->isMethod('POST')) {
            $password = $request->request->get('password');
            $confirmPassword = $request->request->get('confirm_password');

            if (strlen($password) < 6) {
                $this->addFlash('danger', 'Le mot de passe doit contenir au moins 6 caractères.');
            } else if ($password !== $confirmPassword) {
                $this->addFlash('danger', 'Les mots de passe ne correspondent pas.');
            } else {
                // Update password
                $user->setMotDePasseUtilisateur($password);
                $entityManager->flush();

                // Clear session data
                $request->getSession()->remove('reset_email');
                $request->getSession()->remove('reset_user_id');

                $this->addFlash('success', 'Votre mot de passe a été réinitialisé avec succès.');
                return $this->redirectToRoute('app_login');
            }
        }

        return $this->render('security/reset_password.html.twig');
    }
} 