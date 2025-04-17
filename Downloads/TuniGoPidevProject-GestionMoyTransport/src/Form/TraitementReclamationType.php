<?php

namespace App\Form;

use App\Entity\Reclamation;
use App\Entity\TraitementReclamation;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;

class TraitementReclamationType extends AbstractType
{
    /*
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('statut_traitement')
            ->add('date_traitement', null, [
                'widget' => 'single_text',
            ])
            ->add('traitement_description')
            ->add('reclamation', EntityType::class, [
                'class' => Reclamation::class,
    'choice_label' => 'getIdReclamation', // Utilisation de getIdReclamation et non 'id'
            ])
        ;
    }
*/
public function buildForm(FormBuilderInterface $builder, array $options): void
{
    $builder
        ->add('statut_traitement', ChoiceType::class, [
            'choices' => [
                '✔️ Résolue' => 'Résolue',
                '⏳ En attente' => 'En attente',
            ],
        ])
        ->add('traitement_description', TextareaType::class)
    ;
}

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TraitementReclamation::class,
        ]);
    }
}
