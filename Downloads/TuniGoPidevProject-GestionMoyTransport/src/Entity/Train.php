<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;

use App\Repository\TrainRepository;

#[ORM\Entity(repositoryClass: TrainRepository::class)]
#[ORM\Table(name: 'train')]
class Train
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idTrain')]
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

    #[ORM\Column(type: 'string',name: 'numeroTrain', nullable: false)]
    #[Assert\NotBlank(message: "Le numéro de train est obligatoire")]
    #[Assert\Regex(
        pattern: "/^TN-[0-9]+$/",
        message: "Le numéro de train doit commencer par 'TN-' suivi de chiffres"
    )]
    #[Assert\Length(
        min: 2,
        max: 10,
        minMessage: "Le numéro de train doit contenir au moins {{ limit }} caractères",
        maxMessage: "Le numéro de train ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $numeroTrain = null;

    public function getNumeroTrain(): ?string
    {
        return $this->numeroTrain;
    }

    public function setNumeroTrain(string $numeroTrain): self
    {
        $this->numeroTrain = $numeroTrain;
        return $this;
    }

    #[ORM\Column(type: 'integer',name: 'idTrajetTrain', nullable: true)]
    private ?int $idTrajetTrain = null;

    public function getIdTrajetTrain(): ?int
    {
        return $this->idTrajetTrain;
    }

    public function setIdTrajetTrain(?int $idTrajetTrain): self
    {
        $this->idTrajetTrain = $idTrajetTrain;
        return $this;
    }

}
