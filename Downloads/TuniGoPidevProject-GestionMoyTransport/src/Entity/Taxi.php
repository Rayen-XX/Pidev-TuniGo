<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;

use App\Repository\TaxiRepository;

#[ORM\Entity(repositoryClass: TaxiRepository::class)]
#[ORM\Table(name: 'taxi')]
class Taxi
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idTaxi')]
    private ?int $idTaxi = null;

    public function getIdTaxi(): ?int
    {
        return $this->idTaxi;
    }

    public function setIdTaxi(int $idTaxi): self
    {
        $this->idTaxi = $idTaxi;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'numeroTaxi', nullable: false)]
    #[Assert\NotBlank(message: "Le numéro de Taxi est obligatoire")]
    #[Assert\Regex(
        pattern: "/^TX[0-9]+$/",
        message: "Le numéro de Taxi doit commencer par 'TX' suivi de chiffres"
    )]
    #[Assert\Length(
        min: 4,
        max: 10,
        minMessage: "Le numéro de Taxi doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le numéro de Taxi ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $numeroTaxi = null;

    public function getNumeroTaxi(): ?string
    {
        return $this->numeroTaxi;
    }

    public function setNumeroTaxi(string $numeroTaxi): self
    {
        $this->numeroTaxi = $numeroTaxi;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'numeroChauffeur', nullable: false)]
    #[Assert\NotBlank(message: "Le numéro de Chauffeur est obligatoire")]
    #[Assert\Regex(
        pattern: "/^CH[0-9]+$/",
        message: "Le numéro de Chauffeur doit commencer par 'CH' suivi de chiffres"
    )]
    #[Assert\Length(
        min: 4,
        max: 10,
        minMessage: "Le numéro de Chauffeur doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le numéro de Chauffeur ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $numeroChauffeur = null;

    public function getNumeroChauffeur(): ?string
    {
        return $this->numeroChauffeur;
    }

    public function setNumeroChauffeur(string $numeroChauffeur): self
    {
        $this->numeroChauffeur = $numeroChauffeur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'prenomChauffeur', nullable: false)]
    #[Assert\NotBlank(message: "Le prenom de Chauffeur est obligatoire")]
    #[Assert\Regex(
        pattern: "/^[a-zA-ZÀ-ÿ\s\-]+$/",
        message: "Le prenom de Chauffeur ne doit contenir que des lettres"
    )]
    #[Assert\Length(
        min: 3,
        max: 10,
        minMessage: "Le prenom de Chauffeur doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le prenom de Chauffeur ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $prenomChauffeur = null;

    public function getPrenomChauffeur(): ?string
    {
        return $this->prenomChauffeur;
    }

    public function setPrenomChauffeur(string $prenomChauffeur): self
    {
        $this->prenomChauffeur = $prenomChauffeur;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'nomChauffeur', nullable: false)]
    #[Assert\NotBlank(message: "Le nom de Chauffeur est obligatoire")]
    #[Assert\Regex(
        pattern: "/^[a-zA-ZÀ-ÿ\s\-]+$/",
        message: "Le nom de Chauffeur ne doit contenir que des lettres"
    )]
    #[Assert\Length(
        min: 3,
        max: 10,
        minMessage: "Le nom de Chauffeur doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le nom de Chauffeur ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $nomChauffeur = null;

    public function getNomChauffeur(): ?string
    {
        return $this->nomChauffeur;
    }

    public function setNomChauffeur(string $nomChauffeur): self
    {
        $this->nomChauffeur = $nomChauffeur;
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

    #[ORM\Column(type: 'boolean',name: 'Isdisponible', nullable: false)]
    
    private ?bool $Isdisponible = null;

    public function isIsdisponible(): ?bool
    {
        return $this->Isdisponible;
    }

    public function setIsdisponible(bool $Isdisponible): self
    {
        $this->Isdisponible = $Isdisponible;
        return $this;
    }

    public function isdisponible(): ?bool
    {
        return $this->Isdisponible;
    }

}
