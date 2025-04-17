<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Repository\ScooterRepository;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ScooterRepository::class)]
#[ORM\Table(name: 'scooter')]
class Scooter
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idScooter')]
    private ?int $idScooter = null;

    public function getIdScooter(): ?int
    {
        return $this->idScooter;
    }

    public function setIdScooter(int $idScooter): self
    {
        $this->idScooter = $idScooter;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'numeroScooter', nullable: false)]
    #[Assert\NotBlank(message: "Le numéro de Scooter est obligatoire")]
    #[Assert\Regex(
        pattern: "/^SC[0-9]+$/",
        message: "Le numéro de Scooter doit commencer par 'SC' suivi de chiffres"
    )]
    #[Assert\Length(
        min: 4,
        max: 10,
        minMessage: "Le numéro de Scooter doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le numéro de Scooter ne peut pas dépasser {{ limit }} caractères"
    )]
    private string $numeroScooter;

    public function getNumeroScooter(): string
    {
        return $this->numeroScooter;
    }

    public function setNumeroScooter(string $numeroScooter): self
    {
        $this->numeroScooter = $numeroScooter;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'localisationScooter', nullable: false)]
    #[Assert\NotBlank(message: "La localisation est obligatoire")]
    #[Assert\Length(
        min: 4,
        max: 20,
        minMessage: "Le numéro doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le numéro ne peut pas dépasser {{ limit }} caractères"
    )]
    #[Assert\Regex(
        pattern: "/^[a-zA-ZÀ-ÿ\s\-]+$/",
        message: "La localisation ne doit contenir que des lettres"
    )]
    private string $localisationScooter;

    public function getLocalisationScooter(): string
    {
        return $this->localisationScooter;
    }

    public function setLocalisationScooter(string $localisationScooter): self
    {
        $this->localisationScooter = $localisationScooter;
        return $this;
    }

    #[ORM\Column(type: 'integer',name: 'idReservation', nullable: true)]

    private ?int $idReservation = null;

    public function getIdReservation(): ?int
    {
        return $this->idReservation;
    }

    public function setIdReservation(?int $idReservation): self
    {
        $this->idReservation = $idReservation;
        return $this;
    }

  

    // src/Entity/Scooter.php

#[ORM\Column(type: 'boolean',name: 'isDisponible', nullable: false)]
#[Assert\Type(
    type: 'bool',
    message: "La disponibilité doit être true (1) ou false (0)"
)]
private bool $isDisponible;

public function getIsDisponible(): bool
{
    return $this->isDisponible;
}

public function setIsDisponible(bool $isDisponible): self
{
    $this->isDisponible = $isDisponible;
    return $this;
}


    #[ORM\Column(type: 'datetime',name: 'tempsReservation', nullable: false)]
    private \DateTimeInterface $tempsReservation;

    public function getTempsReservation(): \DateTimeInterface
    {
        return $this->tempsReservation;
    }

    public function setTempsReservation(\DateTimeInterface $tempsReservation): self
    {
        $this->tempsReservation = $tempsReservation;
        return $this;
    }

    #[ORM\Column(type: 'datetime',name: 'tempsArrivee', nullable: false)]
    private \DateTimeInterface $tempsArrivee;

    public function getTempsArrivee(): \DateTimeInterface
    {
        return $this->tempsArrivee;
    }

    public function setTempsArrivee(\DateTimeInterface $tempsArrivee): self
    {
        $this->tempsArrivee = $tempsArrivee;
        return $this;
    }

    // Constructeur pour initialiser les dates et autres propriétés
    public function __construct()
    {
        $this->tempsReservation = new \DateTime(); // Date actuelle
        $this->tempsArrivee = new \DateTime(); // Date actuelle par défaut
        $this->isDisponible = true; // Valeur par défaut (disponible)
        $this->numeroScooter = ''; // Valeur par défaut pour numéroScooter (chaine vide)
        $this->localisationScooter = ''; // Valeur par défaut pour localisationScooter
    }
}
