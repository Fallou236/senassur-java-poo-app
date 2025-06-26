package AssuranceApp.dao;

import AssuranceApp.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe gère les opérations liées à la table des clients dans la base de données.
 */
public class ClientDAO {
    private Connection connection;

    /**
     * Constructeur de la classe ClientDAO.
     * @param connection La connexion à la base de données.
     */
    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Enregistre un nouveau client dans la base de données, s'il n'existe pas déjà.
     * @param client Le client à enregistrer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void saveClient(Client client) throws SQLException {
        // Vérifier si le client existe déjà
        if (clientExists(client.getIdentifiant())) {
            return;
        } else {
            // Si le client n'existe pas encore, insérer le nouveau client
            String query = "INSERT INTO clients (identifiant, nom, prenom, date_naissance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, client.getIdentifiant());
                statement.setString(2, client.getNom());
                statement.setString(3, client.getPrenom());
                statement.setObject(4, client.getDateNaissance());
                statement.executeUpdate();
            }
        }
    }

    /**
     * Vérifie si un client avec le même identifiant existe déjà dans la base de données.
     * @param identifiant L'identifiant du client à vérifier.
     * @return true si le client existe déjà, sinon false.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    private boolean clientExists(String identifiant) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM clients WHERE identifiant = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, identifiant);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }

    /**
     * Récupère tous les clients de la base de données.
     * @return Une liste de tous les clients de la base de données.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT identifiant, nom, prenom, date_naissance FROM clients";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String identifiant = resultSet.getString("identifiant");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                LocalDate dateNaissance = resultSet.getObject("date_naissance", LocalDate.class);
                clients.add(new Client(identifiant, nom, prenom, dateNaissance));
            }
        }
        return clients;
    }
    
    //------------------Pour les mises à jour et les suppressions----------------------//

    /**
     * Met à jour les informations d'un client dans la base de données.
     * @param client Le client à mettre à jour.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE clients SET nom = ?, prenom = ?, date_naissance = ? WHERE identifiant = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setObject(3, client.getDateNaissance());
            statement.setString(4, client.getIdentifiant());
            statement.executeUpdate();
        }
    }

    /**
     * Supprime un client de la base de données en fonction de son identifiant.
     * @param identifiant L'identifiant du client à supprimer.
     * @throws SQLException En cas d'erreur SQL lors de l'exécution de la requête.
     */
    public void deleteClient(String identifiant) throws SQLException {
        String query = "DELETE FROM clients WHERE identifiant = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, identifiant);
            statement.executeUpdate();
        }
    }

}
