<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\BusTrajetRepository;

#[ORM\Entity(repositoryClass: BusTrajetRepository::class)]
#[ORM\Table(name: 'bus_trajet')]
class BusTrajet
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'id')]
    private ?int $id = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function setId(int $id): self
    {
        $this->id = $id;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'station_Depart', nullable: false)]
    private ?string $stationDepart = null;

    public function getStationDepart(): ?string
    {
        return $this->stationDepart;
    }

    public function setStationDepart(string $stationDepart): self
    {
        $this->stationDepart = $stationDepart;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'station_Arrivee', nullable: false)]
    private ?string $stationArrivee = null;

    public function getStationArrivee(): ?string
    {
        return $this->stationArrivee;
    }

    public function setStationArrivee(string $stationArrivee): self
    {
        $this->stationArrivee = $stationArrivee;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'heure_Depart', nullable: false)]
    private ?string $heureDepart = null;

    public function getHeureDepart(): ?string
    {
        return $this->heureDepart;
    }

    public function setHeureDepart(string $heureDepart): self
    {
        $this->heureDepart = $heureDepart;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'heure_Arriveet', nullable: false)]
    private ?string $heureArrivee = null;

    public function getHeureArrivee(): ?string
    {
        return $this->heureArrivee;
    }

    public function setHeureArrivee(string $heureArrivee): self
    {
        $this->heureArrivee = $heureArrivee;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'numero_Bus', nullable: false)]
    private ?string $numeroBus = null;

    public function getNumeroBus(): ?string
    {
        return $this->numeroBus;
    }

    public function setNumeroBus(string $numeroBus): self
    {
        $this->numeroBus = $numeroBus;
        return $this;
    }

}
