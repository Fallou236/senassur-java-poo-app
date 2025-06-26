package AssuranceApp;

import java.util.List;

// Interface pour une agence d'assurance
public interface IAgenceAssurance {

    // Méthodes pour enregistrer des entités
    void enregistrerClient(Client client); // Enregistre un client
    void enregistrerFormuleAssurance(FormuleAssurance formuleAssurance); // Enregistre une formule d'assurance
    void enregistrerVoiture(Voiture voiture); // Enregistre une voiture
    void enregistrerImmobilier(Immobilier immobilier); // Enregistre un bien immobilier
    
    // Méthode pour créer une nouvelle souscription
    void nouvelleSouscription(Souscription souscription);

    // Méthodes pour afficher les informations
    List<FormuleAssurance> afficherFormulesExistantes(); // Affiche les formules d'assurance existantes
    List<Souscription> afficherSouscriptionsAnnee(int annee); // Affiche les souscriptions pour une année donnée
    List<Souscription> afficherSouscriptionsClient(Client client); // Affiche les souscriptions d'un client donné
    List<Client> afficherClientsAssurantVoiture(); // Affiche les clients assurant une voiture
    List<Client> afficherClientsAssurantImmobilier(); // Affiche les clients assurant un bien immobilier
    List<Voiture> afficherVoitures(); // Affiche les voitures enregistrées
    List<Immobilier> afficherImmobiliers(); // Affiche les biens immobiliers enregistrés

    // Méthodes pour supprimer les enregistrements
    void deleteClient(Client client); // Supprime un client
    void deleteFormuleAssurance(FormuleAssurance formuleAssurance); // Supprime une formule d'assurance
    void deleteSouscription(Souscription souscription); // Supprime une souscription
    void deleteVoiture(Voiture voiture); // Supprime une voiture
    void deleteImmobilier(Immobilier immobilier); // Supprime un bien immobilier

}
