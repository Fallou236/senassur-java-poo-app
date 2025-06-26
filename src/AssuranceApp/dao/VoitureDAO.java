package AssuranceApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import AssuranceApp.Voiture;

/**
 * Cette classe gère les opérations liées à la table des voitures dans la base de données.
 */
public class VoitureDAO {
    private Connection connection;

    /**
     * Constructeur de la classe VoitureDAO.
     * @param connection La connexion à la base de données.
     */
    public VoitureDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Enregistre une nouvelle voiture dans la base de données, si elle n'existe pas déjà.
     * @param voiture La voiture à enregistrer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void saveVoiture(Voiture voiture) throws SQLException {
        if (voitureExists(voiture.getBien())) {
            return;
        } else {
            String sql = "INSERT INTO Voiture (idVoiture, typeVoiture, marque, anneeMiseCirculation, numMatriculeProprietaire, numChassis) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, voiture.getBien());
                statement.setString(2, voiture.getTypeVoiture());
                statement.setString(3, voiture.getMarque());
                statement.setInt(4, voiture.getAnneeMiseCirculation());
                statement.setString(5, voiture.getNumMatriculeProprietaire());
                statement.setString(6, voiture.getNumChassis());
                statement.executeUpdate();
            }
        }
    }
    
    /**
     * Vérifie si une voiture avec le même ID existe déjà dans la base de données.
     * @param id L'ID de la voiture à vérifier.
     * @return true si la voiture existe déjà, sinon false.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    private boolean voitureExists(String id) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Voiture WHERE idVoiture = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }

    /**
     * Récupère toutes les voitures de la base de données.
     * @return Une liste de toutes les voitures de la base de données.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public List<Voiture> getAllVoitures() throws SQLException {
        List<Voiture> voitures = new ArrayList<>();
        String query = "SELECT idVoiture, typeVoiture, marque, anneeMiseCirculation, numMatriculeProprietaire, numChassis FROM Voiture";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String idVoiture = resultSet.getString("idVoiture");
                String type = resultSet.getString("typeVoiture");
                String marque = resultSet.getString("marque");
                int anneeMiseCirculation = resultSet.getInt("anneeMiseCirculation");
                String numMatriculeProprietaire = resultSet.getString("numMatriculeProprietaire");
                String numChassis = resultSet.getString("numChassis");
                voitures.add(new Voiture(idVoiture, type, marque, anneeMiseCirculation, numMatriculeProprietaire, numChassis));
            }
        }
        return voitures;
    }

    /**
     * Met à jour une voiture dans la base de données.
     * @param voiture La voiture à mettre à jour.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void update(Voiture voiture) throws SQLException {
        String sql = "UPDATE Voiture SET typeVoiture = ?, marque = ?, anneeMiseCirculation = ?, numMatriculeProprietaire = ?, numChassis = ? WHERE idVoiture = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, voiture.getTypeVoiture());
            statement.setString(2, voiture.getMarque());
            statement.setInt(3, voiture.getAnneeMiseCirculation());
            statement.setString(4, voiture.getNumMatriculeProprietaire());
            statement.setString(5, voiture.getNumChassis());
            statement.setString(6, voiture.getBien());
            statement.executeUpdate();
        }
    }

    /**
     * Supprime une voiture de la base de données en fonction de son ID.
     * @param idVoiture L'ID de la voiture à supprimer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void deleteVoiture(String idVoiture) throws SQLException {
        String sql = "DELETE FROM Voiture WHERE idVoiture = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idVoiture);
            statement.executeUpdate();
        }
    }
}
