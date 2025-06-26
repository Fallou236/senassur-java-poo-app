package AssuranceApp;

// Classe représentant un type spécifique de bien assurable : un bien immobilier
public class Immobilier extends Bien {
    // Attributs spécifiques à l'immobilier
    private String typeMobilier;
    private double surface;
    private int nombreEtage;
    private String adresse;

    // Constructeur pour initialiser un bien immobilier avec ses attributs
    public Immobilier(String idBien, String typeMobilier, double surface, int nombreEtage, String adresse) {
        // Appel du constructeur de la classe parent (Bien)
        super(idBien);
        // Initialisation des attributs spécifiques à l'immobilier
        this.typeMobilier = typeMobilier;
        this.surface = surface;
        this.nombreEtage = nombreEtage;
        this.adresse = adresse;
    }

    // Getters pour accéder aux attributs de l'immobilier
    public String getTypeMobilier() {
        return typeMobilier;
    }

    public double getSurface() {
        return surface;
    }

    public int getNombreEtage() {
        return nombreEtage;
    }

    public String getAdresse() {
        return adresse;
    }

    // Setters pour modifier les attributs de l'immobilier
    public void setTypeMobilier(String typeMobilier) {
        this.typeMobilier = typeMobilier;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public void setNombreEtage(int nombreEtage) {
        this.nombreEtage = nombreEtage;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    // Méthode toString pour obtenir une représentation textuelle des informations de l'immobilier
    @Override
    public String toString() {
        return super.toString() + ": Type de mobilier: " + typeMobilier + ", Surface: " + surface + "m², Nombre d'étages: " + nombreEtage + ", Adresse: " + adresse;
    }

}
