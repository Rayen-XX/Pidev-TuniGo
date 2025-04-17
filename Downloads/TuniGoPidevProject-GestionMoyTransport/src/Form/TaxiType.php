<?php

namespace App\Form;

use App\Entity\Taxi;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TaxiType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('numeroTaxi')
            ->add('numeroChauffeur')
            ->add('prenomChauffeur')
            ->add('nomChauffeur')
            ->add('idReservation')
            ->add('Isdisponible')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Taxi::class,
        ]);
    }
}
