<?php

namespace App\Form;

use App\Entity\Trajetmetro;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TrajetmetroType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('departTrajetMetro')
            ->add('arriveTrajetMetro')
            ->add('heurDepartMetro')
            ->add('heurArriveMetro')
            ->add('prixTicketMetro')
            ->add('idMetro')
            ->add('idReservation')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Trajetmetro::class,
        ]);
    }
}
