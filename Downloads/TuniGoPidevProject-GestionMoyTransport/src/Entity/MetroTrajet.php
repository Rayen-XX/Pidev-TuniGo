<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\MetroTrajetRepository;

#[ORM\Entity(repositoryClass: MetroTrajetRepository::class)]
#[ORM\Table(name: 'metro_trajet')]
class MetroTrajet
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
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

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $gareDepart = null;

    public function getGareDepart(): ?string
    {
        return $this->gareDepart;
    }

    public function setGareDepart(string $gareDepart): self
    {
        $this->gareDepart = $gareDepart;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $gareArrivee = null;

    public function getGareArrivee(): ?string
    {
        return $this->gareArrivee;
    }

    public function setGareArrivee(string $gareArrivee): self
    {
        $this->gareArrivee = $gareArrivee;
        return $this;
    }

    #[ORM\Column(type: 'time', nullable: false)]
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

    #[ORM\Column(type: 'time', nullable: false)]
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

}
