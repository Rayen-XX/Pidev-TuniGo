<?php

namespace App\Command;

use App\Entity\Utilisateur;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

#[AsCommand(
    name: 'app:create-user',
    description: 'Creates a new user for testing',
)]
class CreateUserCommand extends Command
{
    private EntityManagerInterface $entityManager;
    private UserPasswordHasherInterface $passwordHasher;

    public function __construct(
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher
    ) {
        $this->entityManager = $entityManager;
        $this->passwordHasher = $passwordHasher;

        parent::__construct();
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $io = new SymfonyStyle($input, $output);
        
        // First delete any existing test user to avoid duplicates
        $existingUser = $this->entityManager->getRepository(Utilisateur::class)
            ->findOneBy(['emailUtilisateur' => 'admin@example.com']);
            
        if ($existingUser) {
            $this->entityManager->remove($existingUser);
            $this->entityManager->flush();
            $io->info('Removed existing test user');
        }
        
        // Create new test user
        $user = new Utilisateur();
        $user->setNomUtilisateur('Admin');
        $user->setPrenomUtilisateur('User');
        $user->setEmailUtilisateur('admin@example.com');
        $user->setNumeroTelephoneUtilisateur('12345678');
        $user->setRoleUtilisateur('admin');
        $user->setQuestionSecurite('What is your favorite color?');
        $user->setReponseSecurite('Blue');
        
        // Use a very simple password
        $plainPassword = '123456';
        
        // Use plain text password (no hashing)
        $user->setMotDePasseUtilisateur($plainPassword);
        
        // Save to database
        $this->entityManager->persist($user);
        $this->entityManager->flush();
        
        $io->success('Test user created:');
        $io->table(
            ['Email', 'Password', 'Hashed Password'],
            [['admin@example.com', $plainPassword, $plainPassword]]
        );

        return Command::SUCCESS;
    }
} 