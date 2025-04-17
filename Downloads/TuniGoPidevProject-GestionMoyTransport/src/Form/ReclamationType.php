<?php

// src/Form/ReclamationType.php
namespace App\Form;

use App\Entity\Reclamation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom_utilisateur', TextType::class, [
                'label' => "Nom de l'utilisateur",
            ])
            ->add('prenom_utilisateur', TextType::class, [
                'label' => "Prénom de l'utilisateur",
            ])
            ->add('typeReclamation', ChoiceType::class, [
                'label' => 'Type de réclamation',
                'choices' => [
                    'Service' => 'service',
                    'Disponibilité' => 'disponibilite',
                    'Paiement' => 'paiement',
                ],
            ])
            ->add('descriptionReclamation', TextareaType::class, [
                'label' => 'Description de la réclamation',
                'attr' => ['style' => 'height: 100px'],
            ])
/*
            ->add('image', FileType::class, [
                'label' => 'Télécharger une image',
                'mapped' => false,
                'required' => false,
                'attr' => ['accept' => 'image/*'],
            ]);
*/
            ->add('image', FileType::class, [
                'label' => 'Télécharger une image',
                'mapped' => false,
                'required' => false,
                'attr' => ['accept' => 'image/*'], // pour le navigateur (filtrage côté client)
                'constraints' => [
                    new File([
                        'maxSize' => '2M', // taille max
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/png',
                            'image/jpg',
                            'image/gif',
                            'image/webp',
                        ],
                        'mimeTypesMessage' => 'Veuillez uploader une image valide (jpg, png, gif, webp)',
                    ])
                ],
            ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
            'csrf_protection' => true,
            'csrf_field_name' => '_token',
            'csrf_token_id'   => 'reclamation', // Ce nom est important
        ]);
    }
}
