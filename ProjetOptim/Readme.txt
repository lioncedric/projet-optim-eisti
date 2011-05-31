========================
informations génerales
========================
Le livrable est composé d'un dossier source contenant le projet, ce dossier est muni d'un build.xml fabriqué par nos soins permettant de compiler,creer un jar executable,et creer la documentation technique du produit.
Il y a aussi un dossier Jeu de test, documentation technique, manuel d'utilisation, et appliction executable.
Voici les quelque commandes utiles pour manipuler le projet :

+->Creation des fichier generables
--->                                                                                                <---
---> Ouvrir un terminal en ayant Ant d'intallé et se positionner sur la racine du dossier contenant <---
--->               les sources du projet projet ainsi que le build.xml                              <---
--->                                                                                                <---
-----+ Pour compiler le code source en .class
-----------> avec la commande "ant compile" sans les guillemets 
-----------> les fichier générés seront situés dans le dossier /build/classes
-----+ Pour generer un archive jar executable
-----------> avec la commande "ant jar" sans les guillemets 
-----------> les fichier générés seront situés dans le dossier /build/jar
-----+ Pour generer la documentation constructeur
-----------> avec la commande "ant creerDoc" sans les guillemets
-----------> les fichier générés seront situés dans le dossier /doc
-----+ Pour effacer tous les fichiers générés
-----------> avec la commande "ant clean" sans les guillemets 

+->Lancer l'application ou ouvrir la doc
-----+ Ouvrir la documentation
-----------> Avec votre navigateur préféré ouvrir le fichier index.html contenu dan le dossier /doc généré precedemment
-----------> Avec votre navigateur préféré ouvrir le fichier index.html fourni pour le client situé le dossier "documentation technique"
-----+ Lancer l'application
-----------> double click sur le jar généré precedemment ou
-----------> double click sur le jar fourni pour le client dans le dossier "application executable" ou
-----------> avec la commande "java -jar OptimEisti.jar" sans les guillemets ou
-----------> avec la commande "ant run" ou "ant" sans les guillemets 

+->Utilisation d'un jeu de test
-----+ Ouvrir le dossier "jeu de test"
-----------> copier le dossier bdd dans lequel se trouve des données
-----------> coller ce dossier a l'endroit ou le jar executable se trouve.
-----------> par exemple dans le dossier  "application executable" au aurait le jar "OptimEisti.jar" et le dossier "bbd"
-----------> lancer le jar

+->Utilisation de l'application
-----+ Ouvrir le dossier "Manuel d'utilisation"
----------->Lancer le document aide pour decouvrir comment manipuler toutes les fonctionalités de ce logiciel

rmq: le jar executable est depalacaple a n'importe que endroit de l'ordinateur

