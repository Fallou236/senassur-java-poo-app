package AssuranceApp;

// Classe représentant une souscription à une assurance
public class Souscription {
    // Attributs d'une souscription
    private int annee;
    private double prix;
    private Client client;
    private FormuleAssurance formule;
    private Bien bien;

    // Constructeur pour initialiser une souscription avec ses attributs
    public Souscription(int annee, double prix, Client client, FormuleAssurance formule, Bien bien) {
        this.annee = annee;
        this.prix = prix;
        this.client = client;
        this.formule = formule;
        this.bien = bien;
    }

    // Getters pour accéder aux attributs de la souscription
    public int getAnnee() {
        return annee;
    }

    public double getPrix() {
        return prix;
    }

    public Client getClient() {
        return client;
    }

    public FormuleAssurance getFormule() {
        return formule;
    }

    public Bien getBien() {
        return bien;
    }

    // Setters pour modifier les attributs de la souscription
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setFormule(FormuleAssurance formule) {
        this.formule = formule;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

 // Méthode toString pour obtenir une représentation textuelle des informations de la souscription
    @Override
    public String toString() {
        return "Souscription[" +
                "\n\tAnnée: " + annee +
                "\n\tPrix: " + prix +
                "\n\tClient: " + client.toString() +
                "\n\tFormule d'assurance: " + formule.toString() +
                "\n\tBien assuré: " + bien.toString() +
                "\n]";
    }

}
