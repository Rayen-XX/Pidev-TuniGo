<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\TrajettrainRepository;

#[ORM\Entity(repositoryClass: TrajettrainRepository::class)]
#[ORM\Table(name: 'trajettrain')]
class Trajettrain
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private ?int $idTrajetTrain = null;

    public function getIdTrajetTrain(): ?int
    {
        return $this->idTrajetTrain;
    }

    public function setIdTrajetTrain(int $idTrajetTrain): self
    {
        $this->idTrajetTrain = $idTrajetTrain;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $departTrajetTrain = null;

    public function getDepartTrajetTrain(): ?string
    {
        return $this->departTrajetTrain;
    }

    public function setDepartTrajetTrain(string $departTrajetTrain): self
    {
        $this->departTrajetTrain = $departTrajetTrain;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $arriveTrajetTrain = null;

    public function getArriveTrajetTrain(): ?string
    {
        return $this->arriveTrajetTrain;
    }

    public function setArriveTrajetTrain(string $arriveTrajetTrain): self
    {
        $this->arriveTrajetTrain = $arriveTrajetTrain;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $heurDepartTrain = null;

    public function getHeurDepartTrain(): ?string
    {
        return $this->heurDepartTrain;
    }

    public function setHeurDepartTrain(string $heurDepartTrain): self
    {
        $this->heurDepartTrain = $heurDepartTrain;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $heurArriveTrain = null;

    public function getHeurArriveTrain(): ?string
    {
        return $this->heurArriveTrain;
    }

    public function setHeurArriveTrain(string $heurArriveTrain): self
    {
        $this->heurArriveTrain = $heurArriveTrain;
        return $this;
    }

    #[ORM\Column(type: 'decimal', nullable: false)]
    private ?float $prixTicketTrain = null;

    public function getPrixTicketTrain(): ?float
    {
        return $this->prixTicketTrain;
    }

    public function setPrixTicketTrain(float $prixTicketTrain): self
    {
        $this->prixTicketTrain = $prixTicketTrain;
        return $this;
    }

    #[ORM\Column(type: 'integer', nullable: false)]
    private ?int $idTrain = null;

    public function getIdTrain(): ?int
    {
        return $this->idTrain;
    }

    public function setIdTrain(int $idTrain): self
    {
        $this->idTrain = $idTrain;
        return $this;
    }

    #[ORM\Column(type: 'integer', nullable: false)]
    private ?int $idReservation = null;

    public function getIdReservation(): ?int
    {
        return $this->idReservation;
    }

    public function setIdReservation(int $idReservation): self
    {
        $this->idReservation = $idReservation;
        return $this;
    }

}
