package AssuranceApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import AssuranceApp.Immobilier;

/**
 * Cette classe gère les opérations liées à la table des biens immobiliers dans la base de données.
 */
public class ImmobilierDAO {
    private Connection connection;

    /**
     * Constructeur de la classe ImmobilierDAO.
     * @param connection La connexion à la base de données.
     */
    public ImmobilierDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Enregistre un nouveau bien immobilier dans la base de données, s'il n'existe pas déjà.
     * @param immobilier Le bien immobilier à enregistrer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void saveImmobilier(Immobilier immobilier) throws SQLException {
        if (immobilierExists(immobilier.getBien())) {
            return;
        }
        
        String sql = "INSERT INTO immobilier (idImmobilier, typeMobilier, surface, nombreEtage, adresse) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, immobilier.getBien());
            statement.setString(2, immobilier.getTypeMobilier());
            statement.setDouble(3, immobilier.getSurface());
            statement.setInt(4, immobilier.getNombreEtage());
            statement.setString(5, immobilier.getAdresse());

            statement.executeUpdate();
        }
    }

    /**
     * Vérifie si un bien immobilier avec le même ID existe déjà dans la base de données.
     * @param idBien L'ID du bien immobilier à vérifier.
     * @return true si le bien immobilier existe déjà, sinon false.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public boolean immobilierExists(String idBien) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM immobilier WHERE idImmobilier = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, idBien);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Récupère tous les biens immobiliers de la base de données.
     * @return Une liste de tous les biens immobiliers de la base de données.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public List<Immobilier> getAllImmobiliers() throws SQLException {
        List<Immobilier> immobiliers = new ArrayList<>();
        String query = "SELECT idImmobilier, typeMobilier, surface, nombreEtage, adresse FROM immobilier";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("idImmobilier");
                String type = resultSet.getString("typeMobilier");
                Double surface = resultSet.getDouble("surface");
                int etages = resultSet.getInt("nombreEtage");
                String adresse = resultSet.getString("adresse");
                immobiliers.add(new Immobilier(id, type, surface, etages, adresse));
            }
        }
        return immobiliers;
    }
    
    /**
     * Supprime un bien immobilier de la base de données en fonction de son ID.
     * @param idBien L'ID du bien immobilier à supprimer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void deleteImmobilier(String idBien) throws SQLException {
        if (!immobilierExists(idBien)) {
            return; // L'immobilier n'existe pas, donc nous ne pouvons pas le supprimer
        }
        
        String sql = "DELETE FROM immobilier WHERE idImmobilier = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idBien);
            statement.executeUpdate();
        }
    }
}
