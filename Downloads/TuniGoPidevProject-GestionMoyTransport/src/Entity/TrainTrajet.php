<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

use App\Repository\TrainTrajetRepository;

#[ORM\Entity(repositoryClass: TrainTrajetRepository::class)]
#[ORM\Table(name: 'train_trajet')]
class TrainTrajet
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
    private ?string $gare_depart = null;

    public function getGare_depart(): ?string
    {
        return $this->gare_depart;
    }

    public function setGare_depart(string $gare_depart): self
    {
        $this->gare_depart = $gare_depart;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $gare_arrivee = null;

    public function getGare_arrivee(): ?string
    {
        return $this->gare_arrivee;
    }

    public function setGare_arrivee(string $gare_arrivee): self
    {
        $this->gare_arrivee = $gare_arrivee;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $heure_depart = null;

    public function getHeure_depart(): ?string
    {
        return $this->heure_depart;
    }

    public function setHeure_depart(string $heure_depart): self
    {
        $this->heure_depart = $heure_depart;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $heure_arrivee = null;

    public function getHeure_arrivee(): ?string
    {
        return $this->heure_arrivee;
    }

    public function setHeure_arrivee(string $heure_arrivee): self
    {
        $this->heure_arrivee = $heure_arrivee;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false)]
    private ?string $numero_train = null;

    public function getNumero_train(): ?string
    {
        return $this->numero_train;
    }

    public function setNumero_train(string $numero_train): self
    {
        $this->numero_train = $numero_train;
        return $this;
    }

    public function getGareDepart(): ?string
    {
        return $this->gare_depart;
    }

    public function setGareDepart(string $gare_depart): static
    {
        $this->gare_depart = $gare_depart;

        return $this;
    }

    public function getGareArrivee(): ?string
    {
        return $this->gare_arrivee;
    }

    public function setGareArrivee(string $gare_arrivee): static
    {
        $this->gare_arrivee = $gare_arrivee;

        return $this;
    }

    public function getHeureDepart(): ?string
    {
        return $this->heure_depart;
    }

    public function setHeureDepart(string $heure_depart): static
    {
        $this->heure_depart = $heure_depart;

        return $this;
    }

    public function getHeureArrivee(): ?string
    {
        return $this->heure_arrivee;
    }

    public function setHeureArrivee(string $heure_arrivee): static
    {
        $this->heure_arrivee = $heure_arrivee;

        return $this;
    }

    public function getNumeroTrain(): ?string
    {
        return $this->numero_train;
    }

    public function setNumeroTrain(string $numero_train): static
    {
        $this->numero_train = $numero_train;

        return $this;
    }

}
