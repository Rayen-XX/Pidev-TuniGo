<?php

namespace App\Form;

use App\Entity\Scooter;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ScooterType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('numeroScooter')
            ->add('localisationScooter')
            ->add('idReservation')
            ->add('Isdisponible')
            ->add('tempsReservation', null, [
                'widget' => 'single_text',
            ])
            ->add('tempsArrivee', null, [
                'widget' => 'single_text',
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Scooter::class,
        ]);
    }
}
