package AssuranceApp;

// Classe représentant une formule d'assurance
public class FormuleAssurance {
    // Attributs d'une formule d'assurance
    private int idFormule;
    private String descriptionFormule;

    // Constructeur pour initialiser une formule d'assurance avec ses attributs
    public FormuleAssurance(int idFormule, String descriptionFormule) {
        this.idFormule = idFormule;
        this.descriptionFormule = descriptionFormule;
    }

    // Getters pour accéder aux attributs de la formule d'assurance
    public int getIdFormule() {
        return idFormule;
    }

    public String getDescriptionFormule() {
        return descriptionFormule;
    }

    // Setters pour modifier les attributs de la formule d'assurance
    public void setIdFormule(int idFormule) {
        this.idFormule = idFormule;
    }

    public void setDescriptionFormule(String descriptionFormule) {
        this.descriptionFormule = descriptionFormule;
    }
    
    // Méthode toString pour obtenir une représentation textuelle des informations de la formule d'assurance
    @Override
    public String toString() {
        return "FormuleAssurance{" +
                "idFormule=" + idFormule +
                ", descriptionFormule='" + descriptionFormule + '\'' +
                '}';
    }
}
