package AssuranceApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Implémentation de l'interface IAgenceAssurance
public class AgenceAssuranceImpl implements IAgenceAssurance {

    // Listes pour stocker les clients, formules, souscriptions, voitures et biens immobiliers
    private List<Client> clients;
    private List<FormuleAssurance> formules;
    private List<Souscription> souscriptions;
    private List<Voiture> voitures;
    private List<Immobilier> immobiliers;

    // Constructeur initialisant les listes
    public AgenceAssuranceImpl() {
        clients = new ArrayList<>();
        formules = new ArrayList<>();
        souscriptions = new ArrayList<>();
        immobiliers = new ArrayList<>();
        voitures = new ArrayList<>();
    }

    // Méthode pour établir une connexion à la base de données
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Senassur";
        String utilisateur = "root";
        String motDePasse = "";
        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion établie avec la base de données.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        return connexion;
    }

    // Méthodes d'accès aux listes
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<FormuleAssurance> getFormules() {
        return formules;
    }

    public void setFormules(List<FormuleAssurance> formules) {
        this.formules = formules;
    }

    public List<Souscription> getSouscriptions() {
        return souscriptions;
    }

    public void setSouscriptions(List<Souscription> souscriptions) {
        this.souscriptions = souscriptions;
    }

    public List<Voiture> getVoitures(){
        return voitures;
    }

    public void setVoitures(List<Voiture> voitures) {
        this.voitures = voitures;
    }
    
    public List<Immobilier> getImmobiliers(){
        return immobiliers;
    }

    public void setImmobilier(List<Immobilier> immobiliers) {
        this.immobiliers = immobiliers;
    }
    
    // Méthode pour charger les clients dans la liste
    public void chargerClients(List<Client> client){
        for(Client cl : client) {
            clients.add(cl);
        }
    }
    
    // Méthode pour charger les formules dans la liste
    public void chargerFormules(List<FormuleAssurance> formule){
        for(FormuleAssurance form : formule) {
            formules.add(form);
        }
    }
    
    // Méthode pour charger les souscriptions dans la liste
    public void chargerSouscriptions(List<Souscription> souscription){
        for(Souscription scrption : souscription) {
            souscriptions.add(scrption);
        }
    }
    
    // Méthode pour charger les voitures dans la liste
    public void chargerVoitures(List<Voiture> voitureAcharger){
        for(Voiture voiture : voitureAcharger) {
            voitures.add(voiture);
        }
    }
    
    // Méthode pour charger les biens immobiliers dans la liste
    public void chargerImmobilers(List<Immobilier> immobilierAcharger){
        for(Immobilier immobilier : immobilierAcharger) {
            immobiliers.add(immobilier);
        }
    }
    

    // Méthodes d'enregistrement
    @Override
    public void enregistrerClient(Client client) {
        clients.add(client);
    }

    @Override
    public void enregistrerFormuleAssurance(FormuleAssurance formuleAssurance) {
        formules.add(formuleAssurance);
    }

    @Override
    public void nouvelleSouscription(Souscription souscription) {
        souscriptions.add(souscription);
    }
    
    @Override
    public void enregistrerVoiture(Voiture voiture) {
        voitures.add(voiture);
    }
    
    @Override
    public void enregistrerImmobilier(Immobilier immobilier) {
        immobiliers.add(immobilier);
    }

    // Méthodes d'affichage
    @Override
    public List<FormuleAssurance> afficherFormulesExistantes() {
        return new ArrayList<>(formules);
    }

    @Override
    public List<Souscription> afficherSouscriptionsAnnee(int annee) {
        List<Souscription> souscriptionsAnnee = new ArrayList<>();
        for (Souscription souscription : souscriptions) {
            if (souscription.getAnnee() == annee) {
                souscriptionsAnnee.add(souscription);
            }
        }
        return souscriptionsAnnee;
    }

    @Override
    public List<Souscription> afficherSouscriptionsClient(Client client) {
        List<Souscription> souscriptionsClient = new ArrayList<>();
        for (Souscription souscription : souscriptions) {
            if (souscription.getClient().getIdentifiant().equals(client.getIdentifiant())) {
                souscriptionsClient.add(souscription);
            }
        }
        return souscriptionsClient;
    }

    @Override
    public List<Client> afficherClientsAssurantVoiture() {
        List<Client> clientsVoiture = new ArrayList<>();
        for (Souscription souscription : souscriptions) {
            if (souscription.getBien() instanceof Voiture) {
                clientsVoiture.add(souscription.getClient());
            }
        }
        return clientsVoiture;
    }

    @Override
    public List<Client> afficherClientsAssurantImmobilier() {
        List<Client> clientsImmobilier = new ArrayList<>();
        for (Souscription souscription : souscriptions) {
            if (souscription.getBien() instanceof Immobilier) {
                clientsImmobilier.add(souscription.getClient());
            }
        }
        return clientsImmobilier;
    }
    
    @Override
    public List<Voiture> afficherVoitures() {
        return new ArrayList<>(voitures);
    }
    
    @Override
    public List<Immobilier> afficherImmobiliers() {
        return new ArrayList<>(immobiliers);
    }
    
    // Méthodes de suppression
    @Override
    public void deleteClient(Client client) {
        clients.remove(client);
    }

    @Override
    public void deleteFormuleAssurance(FormuleAssurance formuleAssurance) {
        formules.remove(formuleAssurance);
    }

    @Override
    public void deleteSouscription(Souscription souscription) {
            	souscriptions.remove(souscription);
    }
    
    @Override
    public void deleteVoiture(Voiture voiture) {
        voitures.remove(voiture);
    }

    @Override
    public void deleteImmobilier(Immobilier immobilier) {
        immobiliers.remove(immobilier);
    }
    
    // Méthodes de recherche
    public Client rechercherClientParIdentifiant(String identifiant) {
        for (Client client : clients) {
            if (client.getIdentifiant().equals(identifiant)) {
                return client;
            }
        }
        return null; // Retourne null si aucun client correspondant n'est trouvé
    }

    public FormuleAssurance rechercherFormuleParId(int id) {
        for (FormuleAssurance formule : formules) {
            if (formule.getIdFormule() == id) {
                return formule;
            }
        }
        return null; // Retourne null si aucune formule correspondante n'est trouvée
    }

    public Souscription rechercherSouscriptionParIdBien(String idBien) {
        for (Souscription souscription : souscriptions) {
            if (souscription.getBien().getBien().equals(idBien)) {
                return souscription;
            }
        }
        return null; // Retourne null si aucune souscription correspondante n'est trouvée
    }


    // Méthodes de recherche pour les voitures et les biens immobiliers
    public Voiture rechercherVoitureParIdentifiant(String identifiant) {
        for (Voiture voiture : voitures) {
            if (voiture.getBien().equals(identifiant)) {
                return voiture;
            }
        }
        return null; // Retourne null si aucune voiture correspondante n'est trouvée
    }

    public Immobilier rechercherImmobilierParIdentifiant(String identifiant) {
        for (Immobilier immobilier : immobiliers) {
            if (immobilier.getBien().equals(identifiant)) {
                return immobilier;
            }
        }
        return null; // Retourne null si aucun bien immobilier correspondant n'est trouvé
    }
}
