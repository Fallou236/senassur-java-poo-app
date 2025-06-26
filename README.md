# 🛡️ Senassur – Application Java de gestion d’assurances  
### Java Insurance Management Application – Senassur

Projet réalisé dans le cadre du module **Programmation Orientée Objet – Java** (Licence 2 Ingénierie Informatique), UFR Sciences et Technologies – Université Assane Seck de Ziguinchor.

Project developed as part of the **Object-Oriented Programming (Java)** module, 2nd Year Computer Engineering Program, Faculty of Science and Technology – Assane Seck University of Ziguinchor.

## 📘 Description du projet / Project Description

**Senassur** est une application Java qui permet de gérer une base de données d’assurance avec plusieurs entités :
- Clients
- Formules d'assurance
- Souscriptions
- Biens assurés (voitures ou immobiliers)

L'application évolue en trois parties :
1. **Console Java avec POO**
2. **Persistance avec MySQL (JDBC)**
3. **Interface graphique Swing**

## 🧱 Fonctionnalités / Features

### Partie I – Mode Console

- ✅ Enregistrer des clients / Register clients  
- ✅ Enregistrer des formules d'assurance / Register insurance plans  
- ✅ Effectuer une souscription / Create a subscription  
- ✅ Afficher les formules existantes / Display existing plans  
- ✅ Afficher les souscriptions d'une année / Show subscriptions by year  
- ✅ Afficher les souscriptions d’un client / Show subscriptions by client  
- ✅ Afficher les clients ayant souscrit pour une voiture / Show clients with car subscriptions  
- ✅ Afficher les clients ayant souscrit pour un bien immobilier / Show clients with real estate subscriptions  

### Partie II – Persistance des données avec MySQL

- 💾 Sauvegarde automatique des données dans MySQL à la fermeture  
- 🔄 Chargement automatique des données à l'ouverture  
- 🧩 Utilisation de JDBC avec le pilote MySQL Connector

### Partie III – Interface Graphique (Swing)

- 🎨 Formulaires pour toutes les opérations de gestion (client, formule, souscription, etc.)
- 📋 Affichage dynamique des données dans des interfaces conviviales
- 📦 Architecture MVC simplifiée et modularisée par package

## 🔧 Technologies utilisées / Technologies Used

- **Java 8+**
- **JDBC** – Connexion à MySQL
- **MySQL** – Base de données relationnelle
- **Swing** – Interface utilisateur graphique
- **UML** – Modélisation objet
- **Eclipse** – Environnement de développement

## 👥 Membres du groupe / Group Members

- **Nom 1 :** Fallou Diouck  
  **Matricule :** 202101663

- **Nom 2 :** El Hadji Malick Mbengue  
  **Matricule :** 2022YYYY

## 🏅 Note obtenue / Grade Received

  > ✅ **16 / 20**  
  > (Travail complet, bien structuré, respect des principes objets et bonne interface graphique)

## ▶️ Exécution / Run the App

### 1. Base de données

Créez la base `senassur` avec les tables correspondantes (script SQL fourni dans `/doc`).

### 2. Lancer l’application
```bash
java -jar dist/senassur.jar

> Ce projet a été réalisé en respectant les principes de la programmation orientée objet et en autonomie complète.

