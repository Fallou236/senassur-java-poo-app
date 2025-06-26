package AssuranceApp.dao;

import AssuranceApp.FormuleAssurance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe gère les opérations liées à la table des formules d'assurance dans la base de données.
 */
public class FormuleAssuranceDAO {
    private Connection connection;

    /**
     * Constructeur de la classe FormuleAssuranceDAO.
     * @param connection La connexion à la base de données.
     */
    public FormuleAssuranceDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Enregistre une nouvelle formule d'assurance dans la base de données, si elle n'existe pas déjà.
     * @param formuleAssurance La formule d'assurance à enregistrer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void saveFormuleAssurance(FormuleAssurance formuleAssurance) throws SQLException {
        // Vérification si l'ID existe déjà
        if (formuleAssuranceExists(formuleAssurance.getIdFormule())) {
            return;
        }
        
        String query = "INSERT INTO formules_assurance (id_formule, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, formuleAssurance.getIdFormule());
            statement.setString(2, formuleAssurance.getDescriptionFormule());
            statement.executeUpdate();
        }
    }

    /**
     * Récupère toutes les formules d'assurance de la base de données.
     * @return Une liste de toutes les formules d'assurance de la base de données.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public List<FormuleAssurance> getAllFormulesAssurance() throws SQLException {
        List<FormuleAssurance> formulesAssurance = new ArrayList<>();
        String query = "SELECT id_formule, description FROM formules_assurance";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idFormule = resultSet.getInt("id_formule");
                String description = resultSet.getString("description");
                formulesAssurance.add(new FormuleAssurance(idFormule, description));
            }
        }
        return formulesAssurance;
    }

    /**
     * Vérifie si une formule d'assurance avec le même ID existe déjà dans la base de données.
     * @param idFormule L'ID de la formule d'assurance à vérifier.
     * @return true si la formule d'assurance existe déjà, sinon false.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    private boolean formuleAssuranceExists(int idFormule) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM formules_assurance WHERE id_formule = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFormule);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }
    
    //------------------Pour les mises à jour et les suppressions----------------------//

    /**
     * Met à jour les informations d'une formule d'assurance dans la base de données.
     * @param formuleAssurance La formule d'assurance à mettre à jour.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void updateFormuleAssurance(FormuleAssurance formuleAssurance) throws SQLException {
        String query = "UPDATE formules_assurance SET description = ? WHERE id_formule = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, formuleAssurance.getDescriptionFormule());
            statement.setInt(2, formuleAssurance.getIdFormule());
            statement.executeUpdate();
        }
    }

    /**
     * Supprime une formule d'assurance de la base de données en fonction de son ID.
     * @param idFormule L'ID de la formule d'assurance à supprimer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void deleteFormuleAssurance(int idFormule) throws SQLException {
        String query = "DELETE FROM formules_assurance WHERE id_formule = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFormule);
            statement.executeUpdate();
        }
    }
}
