<?php

namespace App\Security;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepository;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Exception\AuthenticationException;
use Symfony\Component\Security\Core\Exception\CustomUserMessageAuthenticationException;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Security\Http\Authenticator\AbstractLoginFormAuthenticator;
use Symfony\Component\Security\Http\Authenticator\Passport\Badge\CsrfTokenBadge;
use Symfony\Component\Security\Http\Authenticator\Passport\Badge\UserBadge;
use Symfony\Component\Security\Http\Authenticator\Passport\Credentials\CustomCredentials;
use Symfony\Component\Security\Http\Authenticator\Passport\Passport;
use Symfony\Component\Security\Http\Util\TargetPathTrait;
use Psr\Log\LoggerInterface;

class LoginFormAuthenticator extends AbstractLoginFormAuthenticator
{
    use TargetPathTrait;

    public const LOGIN_ROUTE = 'app_login';

    private UrlGeneratorInterface $urlGenerator;
    private UtilisateurRepository $utilisateurRepository;
    private LoggerInterface $logger;

    public function __construct(
        UrlGeneratorInterface $urlGenerator,
        UtilisateurRepository $utilisateurRepository,
        LoggerInterface $logger
    ) {
        $this->urlGenerator = $urlGenerator;
        $this->utilisateurRepository = $utilisateurRepository;
        $this->logger = $logger;
    }

    public function authenticate(Request $request): Passport
    {
        $email = $request->request->get('email', '');
        $password = $request->request->get('password', '');
        $csrfToken = $request->request->get('_csrf_token');

        $request->getSession()->set(Security::LAST_USERNAME, $email);

        // Log email for debugging
        $this->logger->info('Authentication attempt for: ' . $email);

        return new Passport(
            new UserBadge($email, function ($userIdentifier) {
                $user = $this->utilisateurRepository->findOneBy(['emailUtilisateur' => $userIdentifier]);
                
                if (!$user) {
                    $this->logger->error('User not found: ' . $userIdentifier);
                    throw new CustomUserMessageAuthenticationException('Email ou mot de passe invalide.');
                }
                
                return $user;
            }),
            new CustomCredentials(
                function ($credentials, $user) {
                    // Direct password comparison - the database stores plain passwords
                    // IMPORTANT: This is NOT secure for production but will work with your current DB
                    $isValid = $user->getMotDePasseUtilisateur() === $credentials;
                    
                    if ($isValid) {
                        $this->logger->info('Password valid for: ' . $user->getEmailUtilisateur());
                    } else {
                        $this->logger->error('Password invalid for: ' . $user->getEmailUtilisateur());
                    }
                    
                    return $isValid;
                },
                $password
            ),
            [
                new CsrfTokenBadge('authenticate', $csrfToken),
            ]
        );
    }

    public function onAuthenticationSuccess(Request $request, TokenInterface $token, string $firewallName): ?Response
    {
        $this->logger->info('User logged in successfully: ' . $token->getUserIdentifier());
        
        if ($targetPath = $this->getTargetPath($request->getSession(), $firewallName)) {
            return new RedirectResponse($targetPath);
        }

        // Get the user's role
        $user = $token->getUser();
        
        // Based on the role, redirect to different pages
        if (in_array('ROLE_ADMIN', $user->getRoles())) {
            return new RedirectResponse($this->urlGenerator->generate('app_admin_dashboard'));
        }
        
        // Default redirect for regular users
        return new RedirectResponse($this->urlGenerator->generate('app_home'));
    }

    public function onAuthenticationFailure(Request $request, \Symfony\Component\Security\Core\Exception\AuthenticationException $exception): Response
    {
        $this->logger->error('Authentication failure: ' . $exception->getMessage());
        
        if ($request->hasSession()) {
            $request->getSession()->set(Security::AUTHENTICATION_ERROR, $exception);
        }

        return new RedirectResponse($this->urlGenerator->generate(self::LOGIN_ROUTE));
    }

    protected function getLoginUrl(Request $request): string
    {
        return $this->urlGenerator->generate(self::LOGIN_ROUTE);
    }
} 