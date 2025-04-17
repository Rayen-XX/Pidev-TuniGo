<?php

namespace App\Form;

use App\Entity\Trajetbus;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TrajetbusType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('departTrajetbus')
            ->add('arriveTrajetBus')
            ->add('heurDepartBus')
            ->add('heurArriveBus')
            ->add('prixTicketBus')
            ->add('idBus')
            ->add('idReservation')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Trajetbus::class,
        ]);
    }
}
