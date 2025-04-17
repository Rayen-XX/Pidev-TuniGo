<?php

namespace App\Form;

use App\Entity\Trajettrain;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TrajettrainType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('departTrajetTrain')
            ->add('arriveTrajetTrain')
            ->add('heurDepartTrain')
            ->add('heurArriveTrain')
            ->add('prixTicketTrain')
            ->add('idTrain')
            ->add('idReservation')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Trajettrain::class,
        ]);
    }
}
