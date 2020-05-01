
CREATE TABLE `marches_societe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marche` int(11) NOT NULL,
  `societe` int(11) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `src_financement` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `label` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `secteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `marches_etat` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `marches_type` (
  `id` int(11) NOT NULL,
  `nom` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `os_type` (
  `id` int(11) NOT NULL,
  `label` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `partenaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` text,
  PRIMARY KEY (`id`)
);

CREATE TABLE `permission` (
  `id` int(20) NOT NULL,
  `label` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `responsable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
  `id` int(10) NOT NULL,
  `label` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `acheteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `commune` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(60) DEFAULT NULL,
  `rurale` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `division` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nom` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `maitre_ouvrage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `fraction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `commune` int(11) NOT NULL,
  `LAT` double DEFAULT NULL,
  `LNG` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fraction_ibfk_1` (`commune`),
  CONSTRAINT `fraction_ibfk_1` FOREIGN KEY (`commune`) REFERENCES `commune` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `indh_programme` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `label` varchar(100) NOT NULL,
  `parent` int(10) DEFAULT NULL,
  `phase` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PROG_PARENT_1` (`parent`),
  CONSTRAINT `FK_PROG_PARENT_1` FOREIGN KEY (`parent`) REFERENCES `indh_programme` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `projet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `montant` double unsigned DEFAULT NULL,
  `intitule` varchar(1000),
  `secteur` int(11) DEFAULT NULL,
  `date_saisie` datetime DEFAULT NULL,
  `convention` tinyint(1) unsigned NOT NULL,
  `projet_maitre_ouvrage` int(10) DEFAULT NULL,
  `projet_maitre_ouvrage_delegue` int(10) DEFAULT NULL,
  `prdts` tinyint(1) DEFAULT NULL,
  `default_marche` int(11) DEFAULT NULL,
  `charge_suivi` int(11) NOT NULL,
  `user_saisie` int(11) DEFAULT NULL,
  `date_last_modif` datetime DEFAULT NULL,
  `src_financement` int(10) DEFAULT NULL,
  `annee_projet` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);




CREATE TABLE `marches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projet` int(11) DEFAULT NULL,
  `date_os_commencement` date DEFAULT NULL,
  `date_approbation` date DEFAULT NULL,
  `date_reception_provisoire` date DEFAULT NULL,
  `date_reception_definitive` date DEFAULT NULL,
  `num_marche` varchar(100) DEFAULT NULL,
  `delai_execution` tinyint(4) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `date_plis` date DEFAULT NULL,
  `type_marche` int(11) DEFAULT NULL,
  `intitule` text,
  `current_taux` int(11) DEFAULT NULL,
  `etat_marche` int(11) DEFAULT NULL,
  `date_saisie` datetime DEFAULT NULL,
  `current_os` int(11) DEFAULT NULL,
  `current_decompte` int(11) DEFAULT NULL,
  `en_retard` tinyint(1) unsigned DEFAULT NULL,
  `retard_en_jour` int(11) DEFAULT NULL,
  `worked_days` int(11) DEFAULT NULL,
  `start_os` int(11) DEFAULT NULL,
  `work_days_last_arret` int(11) DEFAULT NULL,
  `last_reprise` date DEFAULT NULL,
  PRIMARY KEY (`id`)
);




CREATE TABLE `marches_decomptes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decompte` double NOT NULL,
  `date_decompte` date NOT NULL,
  `commentaire` tinytext,
  `marche` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MARCHES_DEC` (`marche`),
  CONSTRAINT `FK_MARCHES_DEC` FOREIGN KEY (`marche`) REFERENCES `marches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE `marches_os` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `os` int(20) NOT NULL,
  `date_os` date NOT NULL,
  `commentaire` tinytext,
  `marche` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MARCHES_OS` (`os`),
  KEY `FK_MARCHES_OS_2` (`marche`),
  CONSTRAINT `FK_MARCHES_OS` FOREIGN KEY (`os`) REFERENCES `os_type` (`id`),
  CONSTRAINT `FK_MARCHES_OS_2` FOREIGN KEY (`marche`) REFERENCES `marches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `marches_taux` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marches` int(11) NOT NULL,
  `taux` int(11) NOT NULL,
  `date_taux` date NOT NULL,
  `commentaire` text,
  `retard_en_jour` int(11) DEFAULT NULL,
  `worked_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `marches_taux_ibfk_1` (`marches`),
  CONSTRAINT `marches_taux_ibfk_1` FOREIGN KEY (`marches`) REFERENCES `marches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE `localisation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commune` int(11) DEFAULT NULL,
  `fraction` int(11) DEFAULT NULL,
  `projet` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `localisation_ibfk_1` (`fraction`),
  KEY `localisation_ibfk_2` (`commune`),
  KEY `localisation_ibfk_3` (`projet`),
  CONSTRAINT `localisation_ibfk_1` FOREIGN KEY (`fraction`) REFERENCES `fraction` (`id`),
  CONSTRAINT `localisation_ibfk_2` FOREIGN KEY (`commune`) REFERENCES `commune` (`id`),
  CONSTRAINT `localisation_ibfk_3` FOREIGN KEY (`projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `projet_indh` (
  `projet` int(10) NOT NULL,
  `programme` int(20) NOT NULL,
  `sous_programme` int(20) DEFAULT NULL,
  PRIMARY KEY (`projet`),
  KEY `FK_PROJET_INDH_2` (`programme`),
  KEY `FK_PROJET_INDH_3` (`sous_programme`),
  CONSTRAINT `FK_PROJET_INDH` FOREIGN KEY (`projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_PROJET_INDH_2` FOREIGN KEY (`programme`) REFERENCES `indh_programme` (`id`),
  CONSTRAINT `FK_PROJET_INDH_3` FOREIGN KEY (`sous_programme`) REFERENCES `indh_programme` (`id`)
);

CREATE TABLE `projet_maitre_ouvrage` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `maitre_ouvrage` int(10) NOT NULL,
  `projet` int(10) NOT NULL,
  `delegate` tinyint(1) NOT NULL,
  `responsable` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MAITRE_OUVRAGE_1` (`maitre_ouvrage`),
  KEY `FK_MAITRE_OUVRAGE_2` (`projet`),
  CONSTRAINT `FK_MAITRE_OUVRAGE_1` FOREIGN KEY (`maitre_ouvrage`) REFERENCES `acheteur` (`id`),
  CONSTRAINT `FK_MAITRE_OUVRAGE_2` FOREIGN KEY (`projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `projet_partenaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partenaire` int(11) NOT NULL,
  `projet` int(11) NOT NULL,
  `financement` double(20) unsigned DEFAULT NULL,
  `commentaire` text,
  PRIMARY KEY (`id`),
  KEY `PROJET_PARTENAIRE_FK1` (`projet`),
  KEY `PROJET_PARTENAIRE_FK2` (`partenaire`),
  CONSTRAINT `PROJET_PARTENAIRE_FK1` FOREIGN KEY (`projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PROJET_PARTENAIRE_FK2` FOREIGN KEY (`partenaire`) REFERENCES `acheteur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);






CREATE TABLE `roles_permissions` (
  `role` int(11) NOT NULL,
  `permission` int(11) NOT NULL,
  PRIMARY KEY (`role`,`permission`),
  KEY `roles_permissions_fk_2` (`permission`),
  CONSTRAINT `roles_permissions_fk_1` FOREIGN KEY (`role`) REFERENCES `role` (`id`),
  CONSTRAINT `roles_permissions_fk_2` FOREIGN KEY (`permission`) REFERENCES `permission` (`id`)
);





CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(40) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `division` int(10) DEFAULT NULL,
  `nom` varchar(40) DEFAULT NULL,
  `prenom` varchar(40) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `last_connexion` datetime DEFAULT NULL,
  `date_creation` datetime DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_login` (`login`),
  KEY `FK_DIVISION` (`division`),
  CONSTRAINT `FK_DIVISION` FOREIGN KEY (`division`) REFERENCES `division` (`id`)
);

CREATE TABLE `users_roles` (
  `user` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`user`,`role`),
  KEY `users_roles_fk_2` (`role`),
  CONSTRAINT `users_roles_fk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `users_roles_fk_2` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
);


CREATE TABLE `projet_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `projet` int(10) NOT NULL,
  `user` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PROJET_USER_UNIQUE` (`projet`,`user`),
  KEY `PROJET_USER_FK2` (`user`),
  CONSTRAINT `PROJET_USER_FK1` FOREIGN KEY (`projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PROJET_USER_FK2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `societe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(60) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `responsable` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `societe_ibfk_1` (`responsable`),
  CONSTRAINT `societe_ibfk_1` FOREIGN KEY (`responsable`) REFERENCES `responsable` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
);


-- projet FKs
ALTER TABLE `projet` ADD FOREIGN KEY (`src_financement`) REFERENCES `src_financement` (`id`);
ALTER TABLE `projet` ADD FOREIGN KEY (`default_marche`) REFERENCES `marches` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE `projet` ADD FOREIGN KEY (`charge_suivi`) REFERENCES `user` (`id`);
ALTER TABLE `projet` ADD FOREIGN KEY (`projet_maitre_ouvrage`) REFERENCES `projet_maitre_ouvrage` (`id`);
ALTER TABLE `projet` ADD FOREIGN KEY (`projet_maitre_ouvrage_delegue`) REFERENCES `projet_maitre_ouvrage` (`id`);
ALTER TABLE `projet` ADD FOREIGN KEY (`user_saisie`) REFERENCES `user` (`id`);
ALTER TABLE `projet` ADD FOREIGN KEY (`secteur`) REFERENCES `secteur` (`id`);

-- marche FKs
ALTER TABLE `marches` ADD FOREIGN KEY (`projet`) REFERENCES `projet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `marches` ADD FOREIGN KEY (`type_marche`) REFERENCES `marches_type` (`id`);
ALTER TABLE `marches` ADD FOREIGN KEY (`etat_marche`) REFERENCES `marches_etat` (`id`);
ALTER TABLE `marches` ADD FOREIGN KEY (`current_taux`) REFERENCES `marches_taux` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;
ALTER TABLE `marches` ADD FOREIGN KEY (`current_os`) REFERENCES `marches_os` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE `marches` ADD FOREIGN KEY (`current_decompte`) REFERENCES `marches_decomptes` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE `marches` ADD FOREIGN KEY (`start_os`) REFERENCES `marches_os` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
