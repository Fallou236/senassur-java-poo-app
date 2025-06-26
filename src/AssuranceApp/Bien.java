package AssuranceApp;

// Classe abstraite représentant un bien assurable
public abstract class Bien {
    private String idBien;

    // Constructeur prenant l'identifiant du bien en paramètre
    public Bien(String idBien) {
        this.idBien = idBien;
    }

    // Méthode pour obtenir l'identifiant du bien
    public String getBien() {
        return this.idBien;
    }

    // Méthode pour définir l'identifiant du bien
    public void setBien(String idBien) {
        this.idBien = idBien;
    }
}
