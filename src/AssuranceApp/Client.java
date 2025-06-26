package AssuranceApp;

import java.time.LocalDate;

// Classe représentant un client de l'agence d'assurance
public class Client {
    // Attributs d'un client
    private String identifiant;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    // Constructeur pour initialiser un client avec ses attributs
    public Client(String identifiant, String prenom, String nom, LocalDate dateNaissance) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // Getters pour accéder aux attributs du client
    public String getIdentifiant() {
        return identifiant;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    // Setters pour modifier les attributs du client
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    // Méthode toString pour obtenir une représentation textuelle des informations du client
    @Override
    public String toString() {
        return  identifiant+": " + prenom+ " " + nom +" " + " " + " " +dateNaissance;
    }
}
