<?php

namespace App\Entity;

use App\Repository\ReservationParkingRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use App\Entity\Utilisateur;
use App\Entity\Parking;

#[ORM\Entity(repositoryClass: ReservationParkingRepository::class)]
#[ORM\Table(name: "reservation_parking")]
class ReservationParking
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer', name: "idReservation")]
    private ?int $idReservation = null;

    #[ORM\Column(type: 'datetime', name: "dateReservation")]
    #[Assert\NotBlank(message: "La date de réservation est requise.")]
    #[Assert\Type(\DateTimeInterface::class)]
    private ?\DateTimeInterface $dateReservation = null;

    #[ORM\ManyToOne(targetEntity: Utilisateur::class)]
    #[ORM\JoinColumn(name: "idUtilisateur", referencedColumnName: "idUtilisateur", nullable: false)]
    #[Assert\NotNull(message: "L'utilisateur est requis.")]
    private ?Utilisateur $utilisateur = null;

    #[ORM\ManyToOne(targetEntity: Parking::class)]
    #[ORM\JoinColumn(name: "idParking", referencedColumnName: "idParking", nullable: false)]
    #[Assert\NotNull(message: "Le parking est requis.")]
    private ?Parking $parking = null;

    public function getIdReservation(): ?int
    {
        return $this->idReservation;
    }

    public function getDateReservation(): ?\DateTimeInterface
    {
        return $this->dateReservation;
    }

    public function setDateReservation(\DateTimeInterface $dateReservation): self
    {
        $this->dateReservation = $dateReservation;
        return $this;
    }

    public function getUtilisateur(): ?Utilisateur
    {
        return $this->utilisateur;
    }

    public function setUtilisateur(?Utilisateur $utilisateur): self
    {
        $this->utilisateur = $utilisateur;
        return $this;
    }

    public function getParking(): ?Parking
    {
        return $this->parking;
    }

    public function setParking(?Parking $parking): self
    {
        $this->parking = $parking;
        return $this;
    }
}
