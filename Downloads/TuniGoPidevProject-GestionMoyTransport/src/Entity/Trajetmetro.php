<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\TrajetmetroRepository;

#[ORM\Entity(repositoryClass: TrajetmetroRepository::class)]
#[ORM\Table(name: 'trajetmetro')]
class Trajetmetro
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private ?int $idTrajetMetro = null;

    public function getIdTrajetMetro(): ?int
    {
        return $this->idTrajetMetro;
    }

    public function setIdTrajetMetro(int $idTrajetMetro): self
    {
        $this->idTrajetMetro = $idTrajetMetro;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $departTrajetMetro = null;

    public function getDepartTrajetMetro(): ?string
    {
        return $this->departTrajetMetro;
    }

    public function setDepartTrajetMetro(string $departTrajetMetro): self
    {
        $this->departTrajetMetro = $departTrajetMetro;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $arriveTrajetMetro = null;

    public function getArriveTrajetMetro(): ?string
    {
        return $this->arriveTrajetMetro;
    }

    public function setArriveTrajetMetro(string $arriveTrajetMetro): self
    {
        $this->arriveTrajetMetro = $arriveTrajetMetro;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $heurDepartMetro = null;

    public function getHeurDepartMetro(): ?string
    {
        return $this->heurDepartMetro;
    }

    public function setHeurDepartMetro(string $heurDepartMetro): self
    {
        $this->heurDepartMetro = $heurDepartMetro;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $heurArriveMetro = null;

    public function getHeurArriveMetro(): ?string
    {
        return $this->heurArriveMetro;
    }

    public function setHeurArriveMetro(string $heurArriveMetro): self
    {
        $this->heurArriveMetro = $heurArriveMetro;
        return $this;
    }

    #[ORM\Column(type: 'decimal', nullable: false)]
    private ?float $prixTicketMetro = null;

    public function getPrixTicketMetro(): ?float
    {
        return $this->prixTicketMetro;
    }

    public function setPrixTicketMetro(float $prixTicketMetro): self
    {
        $this->prixTicketMetro = $prixTicketMetro;
        return $this;
    }

    #[ORM\Column(type: 'integer', nullable: false)]
    private ?int $idMetro = null;

    public function getIdMetro(): ?int
    {
        return $this->idMetro;
    }

    public function setIdMetro(int $idMetro): self
    {
        $this->idMetro = $idMetro;
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
