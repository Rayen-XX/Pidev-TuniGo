# 🚍 TuniGo

**TuniGo** est une plateforme 🇹🇳 tunisienne innovante qui facilite :

- 🚌 la **réservation** de moyens de transport **publics** et **privés**
- 🅿️ la **réservation** de places de **parking**

---

## 🧩 Modules Principaux

- 👤 Gestion des **utilisateurs**
- 📅 Gestion des **réservations**
- 🚗 Gestion des **moyens de transport**
- 🅿️ Gestion des **parkings**
- 🛠️ Gestion des **réclamations**
- 🗺️ Gestion des **trajets**

---

## ⚙️ Technologies & Dépendances

Ce projet est développé en **PHP 8.1+** avec **Symfony 6.4**. Il utilise plusieurs outils et bibliothèques modernes :

- 🛠️ **Doctrine ORM & Migrations** – gestion de base de données  
- 🔐 **Symfony Security** – système d’authentification  
- 🔗 **HWIOAuthBundle + OAuth2 (Google, Facebook, GitHub)** – connexion sociale  
- 📄 **KnpPaginatorBundle** – pagination simple et efficace  
- ⚡ **Symfony UX / Turbo / Stimulus** – frontend réactif sans JavaScript complexe  
- 📬 **Symfony Mailer / Notifier / HTTP Client** – communication externe  
- 🎨 **Twig** – moteur de templates  
- 🧪 **PHPUnit** – tests unitaires

📦 Le projet suit la norme **PSR-4** et utilise **Composer** pour la gestion des dépendances.

---

## 🛠️ Installation & Lancement du Projet

Suivez ces étapes pour installer et exécuter **TuniGo** en local :

# 1. Cloner le dépôt
git clone https://github.com/votre-utilisateur/tunigo.git
cd tunigo

# 2. Installer les dépendances PHP
composer install

# 3. Copier le fichier d’environnement et le configurer
cp .env .env.local
# Modifier les variables nécessaires (ex: DATABASE_URL) dans .env.local

# 4. Créer la base de données
php bin/console doctrine:database:create
php bin/console doctrine:migrations:migrate

# 5. Lancer le serveur Symfony
symfony server:start
# ou
php -S localhost:8000 -t public

