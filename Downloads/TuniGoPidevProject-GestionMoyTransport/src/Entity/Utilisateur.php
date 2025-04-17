<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;
use Symfony\Component\Security\Core\User\UserInterface;
use App\Repository\UtilisateurRepository;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

#[ORM\Entity(repositoryClass: UtilisateurRepository::class)]
#[ORM\Table(name: 'utilisateur')]
#[UniqueEntity(fields: ['emailUtilisateur'], message: 'Cet email est déjà utilisé par un autre compte.')]
class Utilisateur implements UserInterface, PasswordAuthenticatedUserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idUtilisateur')]
    private ?int $idUtilisateur = null;

    public function getIdUtilisateur(): ?int
    {
        return $this->idUtilisateur;
    }

    public function setIdUtilisateur(int $idUtilisateur): self
    {
        $this->idUtilisateur = $idUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'nomUtilisateur', nullable: false)]
    private ?string $nomUtilisateur = null;

    public function getNomUtilisateur(): ?string
    {
        return $this->nomUtilisateur;
    }

    public function setNomUtilisateur(string $nomUtilisateur): self
    {
        $this->nomUtilisateur = $nomUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'prenomUtilisateur', nullable: false)]
    private ?string $prenomUtilisateur = null;

    public function getPrenomUtilisateur(): ?string
    {
        return $this->prenomUtilisateur;
    }

    public function setPrenomUtilisateur(string $prenomUtilisateur): self
    {
        $this->prenomUtilisateur = $prenomUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'emailUtilisateur', nullable: false, unique: true)]
    private ?string $emailUtilisateur = null;

    public function getEmailUtilisateur(): ?string
    {
        return $this->emailUtilisateur;
    }

    public function setEmailUtilisateur(string $emailUtilisateur): self
    {
        $this->emailUtilisateur = $emailUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'motDePasseUtilisateur', nullable: false)]
    private ?string $motDePasseUtilisateur = null;

    public function getMotDePasseUtilisateur(): ?string
    {
        return $this->motDePasseUtilisateur;
    }

    public function setMotDePasseUtilisateur(string $motDePasseUtilisateur): self
    {
        $this->motDePasseUtilisateur = $motDePasseUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'numeroTelephoneUtilisateur', nullable: false)]
    private ?string $numeroTelephoneUtilisateur = null;

    public function getNumeroTelephoneUtilisateur(): ?string
    {
        return $this->numeroTelephoneUtilisateur;
    }

    public function setNumeroTelephoneUtilisateur(string $numeroTelephoneUtilisateur): self
    {
        $this->numeroTelephoneUtilisateur = $numeroTelephoneUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'roleUtilisateur', nullable: false)]
    private ?string $roleUtilisateur = null;

    public function getRoleUtilisateur(): ?string
    {
        return $this->roleUtilisateur;
    }

    public function setRoleUtilisateur(string $roleUtilisateur): self
    {
        $this->roleUtilisateur = $roleUtilisateur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'questionSecurite', nullable: false)]
    private ?string $questionSecurite = null;

    public function getQuestionSecurite(): ?string
    {
        return $this->questionSecurite;
    }

    public function setQuestionSecurite(string $questionSecurite): self
    {
        $this->questionSecurite = $questionSecurite;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'reponseSecurite', nullable: false)]
    private ?string $reponseSecurite = null;

    public function getReponseSecurite(): ?string
    {
        return $this->reponseSecurite;
    }

    public function setReponseSecurite(string $reponseSecurite): self
    {
        $this->reponseSecurite = $reponseSecurite;
        return $this;
    }

    /**
     * The public representation of the user (e.g. a username, an email address, etc.)
     */
    public function getUserIdentifier(): string
    {
        return (string) $this->emailUtilisateur;
    }

    /**
     * @see UserInterface
     */
    public function getRoles(): array
    {
        // Guarantee every user at least has ROLE_USER
        $roles = ['ROLE_USER'];
        
        // Map database role values to Symfony roles
        if ($this->roleUtilisateur === 'admin') {
            $roles[] = 'ROLE_ADMIN';
        } else if ($this->roleUtilisateur === 'utilisateur') {
            $roles[] = 'ROLE_UTILISATEUR';
        }

        return array_unique($roles);
    }

    /**
     * @see PasswordAuthenticatedUserInterface
     */
    public function getPassword(): string
    {
        // Just return the plain password
        return $this->motDePasseUtilisateur;
    }

    /**
     * @see UserInterface
     */
    public function eraseCredentials(): void
    {
        // Nothing to erase since we're using plain text passwords
    }
    public function getId(): ?int
{
    return $this->idUtilisateur;
}
}
