<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\TrajetbusRepository;

#[ORM\Entity(repositoryClass: TrajetbusRepository::class)]
#[ORM\Table(name: 'trajetbus')]
class Trajetbus
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idTrajetBus')]
    private ?int $idTrajetBus = null;

    public function getIdTrajetBus(): ?int
    {
        return $this->idTrajetBus;
    }

    public function setIdTrajetBus(int $idTrajetBus): self
    {
        $this->idTrajetBus = $idTrajetBus;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'departTrajetbus', nullable: false)]
    private ?string $departTrajetbus = null;

    public function getDepartTrajetbus(): ?string
    {
        return $this->departTrajetbus;
    }

    public function setDepartTrajetbus(string $departTrajetbus): self
    {
        $this->departTrajetbus = $departTrajetbus;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'arriveTrajetBus', nullable: false)]
    private ?string $arriveTrajetBus = null;

    public function getArriveTrajetBus(): ?string
    {
        return $this->arriveTrajetBus;
    }

    public function setArriveTrajetBus(string $arriveTrajetBus): self
    {
        $this->arriveTrajetBus = $arriveTrajetBus;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'heurDepartBus', nullable: false)]
    private ?string $heurDepartBus = null;

    public function getHeurDepartBus(): ?string
    {
        return $this->heurDepartBus;
    }

    public function setHeurDepartBus(string $heurDepartBus): self
    {
        $this->heurDepartBus = $heurDepartBus;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'heurArriveBus', nullable: false)]
    private ?string $heurArriveBus = null;

    public function getHeurArriveBus(): ?string
    {
        return $this->heurArriveBus;
    }

    public function setHeurArriveBus(string $heurArriveBus): self
    {
        $this->heurArriveBus = $heurArriveBus;
        return $this;
    }

    #[ORM\Column(type: 'decimal',name: 'prixTicketBus', nullable: false)]
    private ?float $prixTicketBus = null;

    public function getPrixTicketBus(): ?float
    {
        return $this->prixTicketBus;
    }

    public function setPrixTicketBus(float $prixTicketBus): self
    {
        $this->prixTicketBus = $prixTicketBus;
        return $this;
    }

    #[ORM\Column(type: 'integer',name: 'idBus', nullable: false)]
    private ?int $idBus = null;

    public function getIdBus(): ?int
    {
        return $this->idBus;
    }

    public function setIdBus(int $idBus): self
    {
        $this->idBus = $idBus;
        return $this;
    }

    #[ORM\Column(type: 'integer',name: 'idReservation', nullable: false)]
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
