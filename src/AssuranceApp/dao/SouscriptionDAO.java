package AssuranceApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import AssuranceApp.Client;
import java.time.LocalDate;
import AssuranceApp.Souscription;
import AssuranceApp.FormuleAssurance;
import AssuranceApp.Bien;
import AssuranceApp.Immobilier;
import AssuranceApp.Voiture;

/**
 * Cette classe gère les opérations de persistance pour les souscriptions dans la base de données.
 */
public class SouscriptionDAO {
    private Connection connection;

    /**
     * Constructeur de la classe SouscriptionDAO.
     * @param connection La connexion à la base de données.
     */
    public SouscriptionDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Enregistre une nouvelle souscription dans la base de données.
     * @param souscription La souscription à enregistrer.
     * @param idBien L'identifiant du bien lié à la souscription.
     */
    public void saveSouscription(Souscription souscription, String idBien) {
        // Vérifie si la souscription existe déjà
        if (souscriptionExisteDeja(souscription, idBien)) {
            System.out.println("La souscription existe déjà dans la base de données.");
            return; // Sort de la méthode si la souscription existe déjà
        }

        String sql = "INSERT INTO souscription (annee, prix, idClient, idFormule, idBien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, souscription.getAnnee());
            statement.setDouble(2, souscription.getPrix());
            statement.setString(3, souscription.getClient().getIdentifiant());
            statement.setInt(4, souscription.getFormule().getIdFormule());
            statement.setString(5, idBien);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifie si une souscription existe déjà dans la base de données.
     * @param souscription La souscription à vérifier.
     * @param idBien L'identifiant du bien lié à la souscription.
     * @return true si la souscription existe déjà, sinon false.
     */
    private boolean souscriptionExisteDeja(Souscription souscription, String idBien) {
        String sql = "SELECT COUNT(*) FROM souscription WHERE annee = ? AND prix = ? AND idClient = ? AND idFormule = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, souscription.getAnnee());
            statement.setDouble(2, souscription.getPrix());
            statement.setString(3, souscription.getClient().getIdentifiant());
            statement.setInt(4, souscription.getFormule().getIdFormule());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Retourne vrai si la souscription existe déjà
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retourne faux si une erreur se produit ou si la souscription n'existe pas encore
    }

    /**
     * Récupère toutes les souscriptions de la base de données.
     * @return Une liste de toutes les souscriptions.
     */
    public List<Souscription> getAllSoucriptions() {
        List<Souscription> souscriptions = new ArrayList<>();
        String sql = "SELECT * FROM souscription";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int annee = resultSet.getInt("annee");
                double prix = resultSet.getDouble("prix");
                String idClient = resultSet.getString("idClient");
                int idFormule = resultSet.getInt("idFormule");
                String idBien = resultSet.getString("idBien");

                // Récupère les objets Client, FormuleAssurance et Bien en fonction de leurs ID
                Client client = getClientById(idClient);
                FormuleAssurance formuleAssurance = getFormuleAssuranceById(idFormule);
                Bien bien = getBienById(idBien);

                souscriptions.add(new Souscription(annee, prix, client, formuleAssurance, bien));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return souscriptions;
    }

    // Méthodes pour récupérer les objets Client, FormuleAssurance et Bien par leurs ID respectifs
    private Client getClientById(String idClient) throws SQLException {
        String sql = "SELECT * FROM clients WHERE identifiant = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crée et renvoie un nouvel objet Client
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    LocalDate dateNaissance = resultSet.getDate("date_naissance").toLocalDate();
                    return new Client(idClient, prenom, nom, dateNaissance);
                }
            }
        }
        return null; // Retourne null si aucun client n'est trouvé
    }

    private FormuleAssurance getFormuleAssuranceById(int idFormule) throws SQLException {
        String sql = "SELECT * FROM formules_assurance WHERE id_formule = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFormule);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crée et renvoie un nouvel objet FormuleAssurance
                    String description = resultSet.getString("description");
                    return new FormuleAssurance(idFormule, description);
                }
            }
        }
        return null; // Retourne null si aucune formule d'assurance n'est trouvée
    }

    private Bien getBienById(String idBien) throws SQLException {
        String sql;
        Bien bien = null;

        // Vérifie d'abord si le bien est une voiture
        sql = "SELECT * FROM Voiture WHERE idVoiture = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idBien);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crée un nouvel objet Voiture
                    String typeVoiture = resultSet.getString("typeVoiture");
                    String marque = resultSet.getString("marque");
                    int anneeMiseCirculation = resultSet.getInt("anneeMiseCirculation");
                    String numMatriculeProprietaire = resultSet.getString("numMatriculeProprietaire");
                    String numChassis = resultSet.getString("numChassis");
                    bien = new Voiture(idBien, typeVoiture, marque, anneeMiseCirculation, numMatriculeProprietaire, numChassis);
                }
            }
        }

        // Si le bien n'est pas une voiture, vérifie s'il s'agit d'un bien immobilier
        if (bien == null) {
            sql = "SELECT * FROM Immobilier WHERE idImmobilier = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idBien);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Crée un nouvel objet Immobilier
                        String typeMobilier = resultSet.getString("typeMobilier");
                        double surface = resultSet.getDouble("surface");
                        int nombreEtage = resultSet.getInt("nombreEtage");
                        String adresse = resultSet.getString("adresse");
                        bien = new Immobilier(idBien, typeMobilier, surface, nombreEtage, adresse);
                    }
                }
            }
        }

        return bien; // Retourne le bien trouvé (peut être null)
    }
    
    /**
     * Supprime une souscription de la base de données en fonction de l'identifiant du bien associé.
     * @param idBien L'identifiant du bien associé à la souscription à supprimer.
     */
    public void deleteSouscription(String idBien) throws SQLException{
        String sql = "DELETE FROM souscription WHERE idBien = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idBien);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
