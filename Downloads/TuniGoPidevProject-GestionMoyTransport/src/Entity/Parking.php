<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\ParkingRepository;

#[ORM\Entity(repositoryClass: ParkingRepository::class)]
#[ORM\Table(name: 'parking')]
class Parking
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idParking')]
    private ?int $idParking = null;

    public function getIdParking(): ?int
    {
        return $this->idParking;
    }

    public function setIdParking(int $idParking): self
    {
        $this->idParking = $idParking;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'nomParking', nullable: true)]
    private ?string $nomParking = null;

    public function getNomParking(): ?string
    {
        return $this->nomParking;
    }

    public function setNomParking(?string $nomParking): self
    {
        $this->nomParking = $nomParking;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'localisationParking', nullable: true)]
    private ?string $localisationParking = null;

    public function getLocalisationParking(): ?string
    {
        return $this->localisationParking;
    }

    public function setLocalisationParking(?string $localisationParking): self
    {
        $this->localisationParking = $localisationParking;
        return $this;
    }

    #[ORM\Column(type: 'integer',name: 'place', nullable: true)]
    private ?int $place = null;

    public function getPlace(): ?int
    {
        return $this->place;
    }

    public function setPlace(?int $place): self
    {
        $this->place = $place;
        return $this;
    }

}
