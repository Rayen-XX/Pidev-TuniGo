<?php

namespace App\Form;

use App\Entity\TrainTrajet;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TrainTrajetType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('gare_depart')
            ->add('gare_arrivee')
            ->add('heure_depart')
            ->add('heure_arrivee')
            ->add('numero_train')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TrainTrajet::class,
        ]);
    }
}
