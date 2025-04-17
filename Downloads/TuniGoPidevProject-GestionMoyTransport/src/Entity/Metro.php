<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;

use App\Repository\MetroRepository;

#[ORM\Entity(repositoryClass: MetroRepository::class)]
#[ORM\Table(name: 'metro')]
class Metro
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idMetro')]
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

    #[ORM\Column(type: 'string',name: 'numerometro', nullable: false)]
    #[Assert\NotBlank(message: "Le numéro de metro est obligatoire")]
    #[Assert\Regex(
        pattern: "/^M[0-9]+$/",
        message: "Le numéro de metro doit commencer par 'M' suivi de chiffres"
    )]
    #[Assert\Length(
        min: 2,
        max: 10,
        minMessage: "Le numéro de metro doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le numéro de metro ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $numeroMetro = null;

    public function getNumeroMetro(): ?string
    {
        return $this->numeroMetro;
    }

    public function setNumeroMetro(string $numeroMetro): self
    {
        $this->numeroMetro = $numeroMetro;
        return $this;
    }

    #[ORM\Column(type: 'integer',name: 'idTrajetMetro', nullable: true)]
    private ?int $idTrajetMetro = null;

    public function getIdTrajetMetro(): ?int
    {
        return $this->idTrajetMetro;
    }

    public function setIdTrajetMetro(?int $idTrajetMetro): self
    {
        $this->idTrajetMetro = $idTrajetMetro;
        return $this;
    }

}
