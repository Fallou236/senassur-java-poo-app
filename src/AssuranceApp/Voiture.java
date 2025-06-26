package AssuranceApp;

// Classe représentant un type spécifique de bien assurable : une voiture
public class Voiture extends Bien {
    // Attributs spécifiques à la voiture
    private String typeVoiture;
    private String marque;
    private int anneeMiseCirculation;
    private String numMatriculeProprietaire;
    private String numChassis;

    // Constructeur pour initialiser une voiture avec ses attributs
    public Voiture(String idBien, String typeVoiture, String marque, int anneeMiseCirculation, String numMatriculeProprietaire, String numChassis) {
        // Appel du constructeur de la classe parent (Bien)
        super(idBien);
        // Initialisation des attributs spécifiques à la voiture
        this.typeVoiture = typeVoiture;
        this.marque = marque;
        this.anneeMiseCirculation = anneeMiseCirculation;
        this.numMatriculeProprietaire = numMatriculeProprietaire;
        this.numChassis = numChassis;
    }

    // Getters et Setters pour accéder et modifier les attributs de la voiture
    public String getTypeVoiture() {
        return typeVoiture;
    }

    public void setTypeVoiture(String typeVoiture) {
        this.typeVoiture = typeVoiture;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getAnneeMiseCirculation() {
        return anneeMiseCirculation;
    }

    public void setAnneeMiseCirculation(int anneeMiseCirculation) {
        this.anneeMiseCirculation = anneeMiseCirculation;
    }

    public String getNumMatriculeProprietaire() {
        return numMatriculeProprietaire;
    }

    public void setNumMatriculeProprietaire(String numMatriculeProprietaire) {
        this.numMatriculeProprietaire = numMatriculeProprietaire;
    }

    public String getNumChassis() {
        return numChassis;
    }

    public void setNumChassis(String numChassis) {
        this.numChassis = numChassis;
    }

    // Méthode toString pour obtenir une représentation textuelle des informations de la voiture
    @Override
    public String toString() {
        return "Voiture [IdBien=" + getBien() + ", TypeVoiture=" + typeVoiture + ", Marque=" + marque
                + ", AnneeMiseCirculation=" + anneeMiseCirculation + ", NumMatriculeProprietaire="
                + numMatriculeProprietaire + ", NumChassis=" + numChassis + "]";
    }
}

