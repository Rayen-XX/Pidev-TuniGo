-- SQL script to update the database schema if needed

-- This is just for backup - we'll check first to see if this is necessary
-- ALTER TABLE utilisateur MODIFY motDePasseUtilisateur VARCHAR(255) NOT NULL;

-- Modify the email column to make it unique if not already
-- Some versions of MySQL don't support IF NOT EXISTS for constraints
-- First check if the index exists, then create it if it doesn't
-- ALTER TABLE utilisateur ADD UNIQUE INDEX email_unique (emailUtilisateur); 