<?php

namespace App\Form;

use App\Entity\ReservationParking;
use App\Entity\Parking;
use App\Entity\Utilisateur;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReservationParkingType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('dateReservation', DateTimeType::class, [
                'widget' => 'single_text',
                'label' => 'Date de réservation'
            ])
            ->add('utilisateur', EntityType::class, [
                'class' => Utilisateur::class,
                'choice_label' => 'nomUtilisateur', // ou autre champ pertinent
                'label' => 'Utilisateur',
                'placeholder' => 'Sélectionner un utilisateur'
            ])
            ->add('parking', EntityType::class, [
                'class' => Parking::class,
                'choice_label' => 'nomParking', // ou autre champ
                'label' => 'Parking',
                'placeholder' => 'Sélectionner un parking'
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => ReservationParking::class,
        ]);
    }
}
