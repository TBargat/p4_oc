# Projet_4_OC

Projet 4 du parcours Expert Java EE.

## 1 - Organisation du repo

* `projet_B4_FR` : dossier parent
    *   `doc` : documentation relative au modèle du projet
    *   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
        *   `dev` : environnement de développement
    *   `src` : code source de l'application
    

## 2 - Corrections apportées sur les 4 erreurs

[x]   Fichier `sqlContext.xml`: `SQLinsertListLigneEcritureComptable` avait **une virgule en moins** sur le INSERT entre `debit` & `credit`

[x]   Bean `EcritureComptable`: la méthode **`getTotalCredit()`** appelait la méthode **`getDebit()`** à la place de **`getCredit()`**

[x]   Bean `EcritureComptable`: la méthode `isEquilibree()` retournait une égalité via **`equals()`** au lieu d'utiliser une comparaison **`compareTo()`**

[x]   Bean `EcritureComptable`: **correction de l'annotation `@Pattern`** sur le paramètre `reference`


## 3 - Tests et Intégration Continue

### Integration continue via Jenkins CI

*   Utilisation de **Jenkins CI** avec un **Job Maven** avec les plug-ins suivant :

    *   **Git** & **Github**
        Permet de gérer l'intégration continue en lançant le build Jenkins à chaque push
        
    *   **JaCoco**
        Permet de récupérer les résultats de tests après le build afin de dresser un rapport avec les chiffres clés et graphiques du code coverage sur la page du build sur Jenkins
        
*   Les tests sont déclenchés à chaque build Jenkins (déclenché par un push), via la commande Maven depuis le pom parent `projet_B4_FR/src/pom.xml` :
        
        `<clean package org.jacoco:jacoco-maven-plugin:0.8.2:report -P test-consumer,test-business>`

### Les tests :

*   Tests Unitaires : Ils sont déclenchés par la commande `<package>` de Maven

*   Test d'Intégration : Ils sont déchlenchés via l'appel des profils `test-consumer,test-business` avec la commande `<-P test-consumer,test-business>`