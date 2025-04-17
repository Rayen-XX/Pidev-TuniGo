<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Entity\TraitementReclamation;
use Symfony\Component\Validator\Constraints as Assert;

use App\Repository\ReclamationRepository;

#[ORM\Entity(repositoryClass: ReclamationRepository::class)]
#[ORM\Table(name: 'reclamation')]
class Reclamation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer',name: 'idReclamation')]
    private ?int $idReclamation = null;

    public function getIdReclamation(): ?int
    {
        return $this->idReclamation;
    }

    public function setIdReclamation(int $idReclamation): self
    {
        $this->idReclamation = $idReclamation;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'typeReclamation', nullable: false)]
    #[Assert\NotBlank(message: "Le type de réclamation est obligatoire")]
    #[Assert\Choice(
        choices: ['Service', 'Disponibilité', 'Paiement'],
        message: "Le type doit être 'Service', 'Disponibilité' ou 'Paiement'"
    )]
    private ?string $typeReclamation = null;

    public function getTypeReclamation(): ?string
    {
        return $this->typeReclamation;
    }

    public function setTypeReclamation(string $typeReclamation): self
    {
        $this->typeReclamation = $typeReclamation;
        return $this;
    }

    #[ORM\Column(type: 'string',name: 'descriptionReclamation', nullable: false)]
    #[Assert\NotBlank(message: "La description est obligatoire")]
    #[Assert\Length(
        min: 10,
        max: 100,
        minMessage: "La description doit contenir au moins {{ limit }} caractères",
        maxMessage: "La description ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $descriptionReclamation = null;

    public function getDescriptionReclamation(): ?string
    {
        return $this->descriptionReclamation;
    }

    public function setDescriptionReclamation(string $descriptionReclamation): self
    {
        $this->descriptionReclamation = $descriptionReclamation;
        return $this;
    }

    #[ORM\Column(type: 'string', nullable: false,name: 'statutReclamation')]
    #[Assert\Choice(
        choices: ['En Attente', 'Résolue'],
        message: "Le statut Reclamation doit être  'En Attente' ou 'Résolue'"
    )]
    private ?string $statutReclamation = null;

    public function getStatutReclamation(): ?string
    {
        
        return $this->statutReclamation;
    }

    public function setStatutReclamation(?string $statutReclamation): self
    {
        // On assigne la valeur du statut directement, en fonction du champ envoyé
        $this->statutReclamation = $statutReclamation;
    
        return $this;
    }
    
    #[ORM\Column(length: 255, nullable: true)]
    private ?string $image = null;
    
    public function getImage(): ?string
    {
        return $this->image;
    }
    
    public function setImage(?string $image): self
    {
        $this->image = $image;
        return $this;
    }
    
   /**
     * @ORM\Column(type="datetime")
     */
    #[ORM\Column(type: 'datetime', name: 'dateReclamation', nullable: false)]

    private ?\DateTimeInterface $dateReclamation = null;

    public function getDateReclamation(): ?\DateTimeInterface
    {
        return $this->dateReclamation;
    }

    public function setDateReclamation(\DateTimeInterface $dateReclamation): self
    {
        $this->dateReclamation = $dateReclamation;
        return $this;
    }

    #[ORM\ManyToOne(targetEntity: Utilisateur::class, inversedBy: 'reclamations')]
    #[ORM\JoinColumn(name: 'idUtilisateur', referencedColumnName: 'idUtilisateur')]
    private ?Utilisateur $utilisateur = null;

    public function getUtilisateur(): ?Utilisateur
    {
        return $this->utilisateur;
    }

    public function setUtilisateur(?Utilisateur $utilisateur): self
    {
        $this->utilisateur = $utilisateur;
        return $this;
    }



    #[ORM\Column(type: 'string',name: 'nom_utilisateur', nullable: false)]
    #[Assert\NotBlank]
    #[Assert\Regex(
        pattern: "/^[a-zA-ZÀ-ÿ\s\-]+$/",
        message: "Le nom d'utilisateur ne doit contenir que des lettres"
    )]
    private ?string $nom_utilisateur = null;
    public function getNomUtilisateur(): ?string
    {
        return $this->nom_utilisateur;
    }
    
    public function setNomUtilisateur(string $nom_utilisateur): self
    {
        $this->nom_utilisateur = $nom_utilisateur;
        return $this;
    }
    

    #[ORM\Column(type: 'string',name: 'prenom_utilisateur', nullable: false)]
    #[Assert\NotBlank]
    #[Assert\Regex(
        pattern: "/^[a-zA-ZÀ-ÿ\s\-]+$/",
        message: "Le prénom d'utilisateur ne doit contenir que des lettres"
    )]
    private ?string $prenom_utilisateur = null;
    
    public function getPrenomUtilisateur(): ?string
    {
        return $this->prenom_utilisateur;
    }
    
    public function setPrenomUtilisateur(string $prenom_utilisateur): self
    {
        $this->prenom_utilisateur = $prenom_utilisateur;
        return $this;
    }
    

    #[ORM\OneToMany(mappedBy: 'reclamation', targetEntity: TraitementReclamation::class, cascade: ['persist', 'remove'])]
    private Collection $traitements;
    
    public function __construct()
    {
        $this->traitements = new ArrayCollection();
    }
    
    public function getTraitements(): Collection
    {
        return $this->traitements;
    }
    
    public function addTraitement(TraitementReclamation $traitement): self
    {
        if (!$this->traitements->contains($traitement)) {
            $this->traitements[] = $traitement;
            $traitement->setReclamation($this);
        }
    
        return $this;
    }
    
    public function removeTraitement(TraitementReclamation $traitement): self
    {
        if ($this->traitements->removeElement($traitement)) {
            // set the owning side to null (unless already changed)
            if ($traitement->getReclamation() === $this) {
                $traitement->setReclamation(null);
            }
        }
    
        return $this;
    }
    

}

