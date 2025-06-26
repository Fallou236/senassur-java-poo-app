package AssuranceApp;

import javax.swing.*;
import AssuranceApp.dao.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.time.format.DateTimeParseException;

/**
 * Cette classe représente l'application Senassur, qui gère les opérations liées à une agence d'assurance.
 * Elle permet d'ajouter des clients, des formules d'assurance, d'effectuer des souscriptions,
 * d'afficher les données existantes et de supprimer des clients, des formules ou des souscriptions. 
 * Elle étend la classe JFrame et fournit l'interface utilisateur graphique
 * pour l'application Senassur.
 */

public class SenassurApp extends JFrame {
	// Déclaration des attributs et méthodes 
	
	private static final long serialVersionUID = 1L;
	
	// Instance de l'implémentation de l'agence d'assurance
	AgenceAssuranceImpl agenceAssurance ;
	// Connexion à la base de données
    private Connection connexion;
    // Objets DAOs pour la gestion des donnees
    private ClientDAO clientDAO;
    private FormuleAssuranceDAO formuleDAO;
    private VoitureDAO voitureDAO;
    private ImmobilierDAO immobilierDAO;
    private SouscriptionDAO souscriptionDAO;
    // Zone de texte pour afficher les messages et les résultats
    private JTextArea textArea;

    /**
     * Constructeur de la classe SenassurApp.
     * Initialise l'interface graphique de l'application et charge les données depuis la base de données.
     */
    public SenassurApp() {
    	// Initialisation de l'interface graphique et chargement des données
        setTitle("Senassur");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

     // Ajout du message de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans l'application Senassur !");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);
        
        textArea = new JTextArea();
        textArea = new JTextArea("Zone de texte pour afficher les messages et les résultats :\n");
        textArea.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3));
        add(buttonPanel, BorderLayout.SOUTH);

        //  Connexion , DAOs et le chargement des donnes
        try {
            agenceAssurance = new AgenceAssuranceImpl();
            connexion = agenceAssurance.getConnection();
            clientDAO = new ClientDAO(connexion);
            formuleDAO = new FormuleAssuranceDAO(connexion); 
            voitureDAO = new VoitureDAO(connexion);
            immobilierDAO = new ImmobilierDAO(connexion);
            souscriptionDAO = new SouscriptionDAO(connexion);
            if (connexion != null) {
	            agenceAssurance.chargerClients(clientDAO.getAllClients());
	            agenceAssurance.chargerFormules(formuleDAO.getAllFormulesAssurance());
	            agenceAssurance.chargerSouscriptions(souscriptionDAO.getAllSoucriptions());
	            agenceAssurance.chargerVoitures(voitureDAO.getAllVoitures());
	            agenceAssurance.chargerImmobilers(immobilierDAO.getAllImmobiliers());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JButton boutonAjouterClient = new JButton("Ajouter un client");
        JButton boutonAjouterFormule = new JButton("Ajouter une formule d'assurance");
        JButton boutonNouvelleSouscription = new JButton("Effectuer une nouvelle souscription");
        JButton boutonAfficherFormules = new JButton("Afficher les formules existantes");
        JButton boutonAfficherSouscriptionsAnnee = new JButton("Afficher les souscriptions pour une année donnée");
        JButton boutonAfficherSouscriptionsClient = new JButton("Afficher les souscriptions faites par un client donné");
        JButton boutonAfficherClientsAvecVoiture = new JButton("Afficher les clients assurant une voiture");
        JButton boutonAfficherClientsAvecImmobilier = new JButton("Afficher les clients assurant un bien immobilier");
        JButton boutonSupprimerClient = new JButton("Supprimer un client");
        JButton boutonSupprimerFormule = new JButton("Supprimer une formule");
        JButton boutonSupprimerSouscription = new JButton("Supprimer un souscription");
        JButton boutonQuitter = new JButton("Quitter");
 //****************************************************************************************************************************************
        //Informe l'utlisateur si la connexion est nulle 
        if (connexion == null) {
        	textArea.append("Erreur lors de la connexion à la base de données : Access denied for user 'root'@'localhost' (using password: NO)");
        }else {
        	textArea.append("Connexion établie avec la base de données.\r\n");
        }
     // Action à effectuer lorsque le bouton d'ajout de client est cliqué
        boutonAjouterClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Création des champs de texte pour saisir les informations du nouveau client
                JTextField identifiantField = new JTextField(10);
                JTextField prenomField = new JTextField(10);
                JTextField nomField = new JTextField(10);
                JTextField dateNaissanceField = new JTextField(10);

                // Création d'un panneau pour organiser les champs de texte
                JPanel panel = new JPanel(new GridLayout(4, 2));
                panel.add(new JLabel("Identifiant :"));
                panel.add(identifiantField);
                panel.add(new JLabel("Prénom :"));
                panel.add(prenomField);
                panel.add(new JLabel("Nom :"));
                panel.add(nomField);
                panel.add(new JLabel("Date de naissance (jj/mm/aaaa) :"));
                panel.add(dateNaissanceField);

                // Affichage de la boîte de dialogue pour saisir les informations du nouveau client
                int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter un client",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Traitement des informations saisies lorsque l'utilisateur clique sur OK
                if (result == JOptionPane.OK_OPTION) {
                    String identifiant = identifiantField.getText();
                    String prenom = prenomField.getText();
                    String nom = nomField.getText();
                    String dateNaissanceStr = dateNaissanceField.getText();

                    // Vérification si tous les champs sont remplis
                    if (!identifiant.isEmpty() && !prenom.isEmpty() && !nom.isEmpty() && !dateNaissanceStr.isEmpty()) {
                        try {
                            // Conversion de la chaîne de date en LocalDate
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr, formatter);

                            // Vérification si le client existe déjà
                            if (agenceAssurance.rechercherClientParIdentifiant(identifiant) != null) {
                                JOptionPane.showMessageDialog(null, "Ce client existe déjà !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Création d'un nouveau client avec les informations saisies
                                Client nouveauClient = new Client(identifiant, prenom, nom, dateNaissance);

                                // Ajout du nouveau client dans la liste des clients et dans la base de données
                                agenceAssurance.enregistrerClient(nouveauClient);
                                if(connexion != null) {
	                                try {
	                                    clientDAO.saveClient(nouveauClient);
	                                } catch (SQLException ex) {
	                                    ex.printStackTrace();
	                                }
                                }
                                //Affiche le resultat dans le textArea
                                textArea.append("Client " + identifiantField.getText() + " a été ajouté avec succès!\n");
                                // Affichage d'un message de confirmation
                                JOptionPane.showMessageDialog(null, "Client ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (DateTimeParseException ex) {
                            // Affichage d'un message d'erreur si le format de la date est incorrect
                            JOptionPane.showMessageDialog(null, "Format de date incorrect ! Utilisez jj/mm/aaaa.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Affichage d'un message d'erreur si tous les champs ne sont pas remplis
                        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        buttonPanel.add(boutonAjouterClient);


 //-----------------------------------------------------------------------------------------------------------------------------------------------

     // Action à effectuer lorsque le bouton d'ajout de formule d'assurance est cliqué
        boutonAjouterFormule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Création des champs de texte pour saisir les informations de la nouvelle formule d'assurance
                JTextField idField = new JTextField(10);
                JTextField descriptionField = new JTextField(20);
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("ID :"));
                panel.add(idField);
                panel.add(new JLabel("Description :"));
                panel.add(descriptionField);
                // Affichage de la boîte de dialogue pour saisir les informations de la nouvelle formule d'assurance
                int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter une formule d'assurance",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                // Traitement des informations saisies lorsque l'utilisateur clique sur OK
                if (result == JOptionPane.OK_OPTION) {
                    String idStr = idField.getText();
                    String description = descriptionField.getText();

                    // Vérification si tous les champs sont remplis
                    if (!idStr.isEmpty() && !description.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idStr);

                            // Vérification si la formule existe déjà
                            if (agenceAssurance.rechercherFormuleParId(id) != null) {
                                JOptionPane.showMessageDialog(null, "Cette formule existe déjà !", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Création d'une nouvelle formule d'assurance avec les informations saisies
                                FormuleAssurance nouvelleFormule = new FormuleAssurance(id, description);
                                // Ajout de la nouvelle formule dans la logique de votre application ou dans la base de données
                                agenceAssurance.enregistrerFormuleAssurance(nouvelleFormule);
                                //Verifie si la connexion n'est pas null pour ajouter la formule a la BD
                                if(connexion != null) {
	                                try {
	                                    formuleDAO.saveFormuleAssurance(nouvelleFormule);
	                                } catch (SQLException ex) {
	                                    ex.printStackTrace();
	                                }
                                }
                                //Affiche le resultat dans le textArea
                                textArea.append("le formule "+idStr+" a ete ajoute avec succes!\n");
                            }
                        } catch (NumberFormatException ex) {
                            // Affichage d'un message d'erreur si l'ID n'est pas un nombre entier
                            JOptionPane.showMessageDialog(null, "L'ID doit être un nombre entier !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Affichage d'un message d'erreur si tous les champs ne sont pas remplis
                        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(boutonAjouterFormule);

 //----------------------------------------------------------------------------------------------------------------------

     // Action à effectuer lorsque le bouton d'ajout d'une nouvelle souscription est cliqué
        boutonNouvelleSouscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Création des champs de texte pour saisir les informations de la nouvelle souscription
                JTextField yearField = new JTextField(10);
                JTextField priceField = new JTextField(10);
                JComboBox<String> clientComboBox = new JComboBox<>();
                JComboBox<String> formulaComboBox = new JComboBox<>();
                JComboBox<String> propertyTypeComboBox = new JComboBox<>(new String[]{"Voiture", "Immobilier"});

                // Remplissage du menu déroulant client avec les clients existants
                for (Client client : agenceAssurance.getClients()) {
                    clientComboBox.addItem(client.getIdentifiant() + ": " + client.getNom() + " " + client.getPrenom());
                }

                // Remplissage du menu déroulant formule avec les formules d'assurance existantes
                for (FormuleAssurance formula : agenceAssurance.getFormules()) {
                    formulaComboBox.addItem(String.valueOf(formula.getIdFormule()));
                }

                // Création du panneau de saisie pour les informations de la souscription
                JPanel panel = new JPanel(new GridLayout(6, 2)); // Ajustez la taille de la grille si nécessaire
                panel.add(new JLabel("Année :"));
                panel.add(yearField);
                panel.add(new JLabel("Prix :"));
                panel.add(priceField);
                panel.add(new JLabel("Client :"));
                panel.add(clientComboBox);
                panel.add(new JLabel("Formule :"));
                panel.add(formulaComboBox);
                panel.add(new JLabel("Type de bien :"));
                panel.add(propertyTypeComboBox);

                // Affichage de la boîte de dialogue pour saisir les informations de la nouvelle souscription
                int result = JOptionPane.showConfirmDialog(null, panel, "Effectuer une nouvelle souscription",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Traitement des informations saisies lorsque l'utilisateur clique sur OK
                if (result == JOptionPane.OK_OPTION) {
                    String yearStr = yearField.getText();
                    String priceStr = priceField.getText();
                    String clientStr = (String) clientComboBox.getSelectedItem();
                    String formulaStr = (String) formulaComboBox.getSelectedItem();
                    String propertyTypeStr = (String) propertyTypeComboBox.getSelectedItem();

                    // Vérification si tous les champs nécessaires sont remplis
                    if (!yearStr.isEmpty() && !priceStr.isEmpty()) {
                        try {
                            int year = Integer.parseInt(yearStr);
                            double price = Double.parseDouble(priceStr);

                            // Récupération du client sélectionné
                            String[] clientParts = clientStr.split(": ");
                            String clientId = clientParts[0];
                            Client client = agenceAssurance.rechercherClientParIdentifiant(clientId);

                            // Récupération de la formule d'assurance sélectionnée
                            int formulaId = Integer.parseInt(formulaStr);
                            FormuleAssurance formule = agenceAssurance.rechercherFormuleParId(formulaId);

                            // Création des champs spécifiques au type de bien en fonction du type sélectionné
                            JPanel propertyPanel = new JPanel(new GridLayout(0, 2)); // Ajustez la taille de la grille si nécessaire
                            if (propertyTypeStr.equalsIgnoreCase("voiture")) {
                            	// Création du menu déroulant pour les propriétaires de voiture
                                JComboBox<String> numProprietaireComboBox = new JComboBox<>();
                                // Remplissage du menu déroulant avec les identifiants des clients existants
                                for (Client proprietaire : agenceAssurance.getClients()) {
                                    numProprietaireComboBox.addItem(proprietaire.getIdentifiant());
                                }
                                // Création des champs spécifiques pour une voiture
                            	 propertyPanel.add(new JLabel("Identifiant voiture :"));
                                 JTextField idVoitureField = new JTextField(10);
                                 propertyPanel.add(idVoitureField);
                                 propertyPanel.add(new JLabel("Type de voiture :"));
                                 JTextField typeVoitureField = new JTextField(10);
                                 propertyPanel.add(typeVoitureField);
                                 propertyPanel.add(new JLabel("Marque de la voiture :"));
                                 JTextField marqueVoitureField = new JTextField(10);
                                 propertyPanel.add(marqueVoitureField);
                                 propertyPanel.add(new JLabel("Numero Matricule du propriétaire :"));
                                 propertyPanel.add(numProprietaireComboBox); // Placer le menu déroulant ici
                                 propertyPanel.add(new JLabel("Année de mise en circulation :"));
                                 JTextField anneeMiseCirculationField = new JTextField(10);
                                 propertyPanel.add(anneeMiseCirculationField);
                                 propertyPanel.add(new JLabel("Numéro de chassis :"));
                                 JTextField numChassisField = new JTextField(10);
                                 propertyPanel.add(numChassisField);
                            } else if (propertyTypeStr.equalsIgnoreCase("immobilier")) {
                                // Création des champs spécifiques pour un bien immobilier
                            	propertyPanel.add(new JLabel("Identifiant immobilier :"));
                                JTextField idImmobilierField = new JTextField(10);
                                propertyPanel.add(idImmobilierField);
                                propertyPanel.add(new JLabel("Type de l'immobilier :"));
                                JTextField typeImmobilierField = new JTextField(10);
                                propertyPanel.add(typeImmobilierField);
                                propertyPanel.add(new JLabel("Surface :"));
                                JTextField surfaceField = new JTextField(10);
                                propertyPanel.add(surfaceField);
                                propertyPanel.add(new JLabel("Nombre d'étages :"));
                                JTextField nombreEtagesField = new JTextField(10);
                                propertyPanel.add(nombreEtagesField);
                                propertyPanel.add(new JLabel("Adresse :"));
                                JTextField adresseField = new JTextField(10);
                                propertyPanel.add(adresseField);
                            }

                            // Affichage de la boîte de dialogue pour saisir les informations spécifiques au bien
                            int propertyResult = JOptionPane.showConfirmDialog(null, propertyPanel,
                                    "Entrez les informations sur le bien", JOptionPane.OK_CANCEL_OPTION);
                            if (propertyResult == JOptionPane.OK_OPTION) {
                            	if (propertyTypeStr.equalsIgnoreCase("voiture")) {
                            		JTextField idVoitureField = (JTextField) propertyPanel.getComponent(1);
                                    JTextField typeVoitureField = (JTextField) propertyPanel.getComponent(3);
                                    JTextField marqueVoitureField = (JTextField) propertyPanel.getComponent(5);
                                    @SuppressWarnings("unchecked")
									JComboBox<String> numProprietaireComboBox = (JComboBox<String>) propertyPanel.getComponent(7); // Récupérer le JComboBox
                                    JTextField anneeMiseCirculationField = (JTextField) propertyPanel.getComponent(9);
                                    JTextField numChassisField = (JTextField) propertyPanel.getComponent(11);
                                    
                                    // Récupérer l'identifiant du client sélectionné
                                    String selectedClientId = (String) numProprietaireComboBox.getSelectedItem();

                                    // Création de la voiture avec les attributs entrés par l'utilisateur
                                    Voiture voiture = new Voiture(idVoitureField.getText(), typeVoitureField.getText(),
                                            marqueVoitureField.getText(), Integer.parseInt(anneeMiseCirculationField.getText()),
                                            selectedClientId, numChassisField.getText()); // Utiliser l'identifiant sélectionné
                                    agenceAssurance.enregistrerVoiture(voiture);
                                    Souscription newSouscription = new Souscription(year, price, client, formule, voiture);
                                    agenceAssurance.nouvelleSouscription(newSouscription);
                                    //Verifie si la connexion n'est pas null pour enregistrer la souscription et la voiture associee
                                    if(connexion != null) {
	                                    try {
	                                        voitureDAO.saveVoiture(voiture);	                                        
	                                        souscriptionDAO.saveSouscription(newSouscription, idVoitureField.getText());
	                                    } catch (SQLException ex) {
	                                        ex.printStackTrace();
	                                    }
                                    }
                                    //Affiche les resultats dans le textArea
                                    textArea.append("La voiture "+idVoitureField.getText()+" a ete ajoutée avec succés!\n");
                                    textArea.append("Nouvelle souscription (pour la voiture "+idVoitureField.getText()+") a ete ajoutée avec succès !\n");
                                    // Creation de la souscription et son ajout
                                } else if (propertyTypeStr.equalsIgnoreCase("immobilier")) {
                                    JTextField idImmobilierField = (JTextField) propertyPanel.getComponent(1);
                                    JTextField typeImmobilierField = (JTextField) propertyPanel.getComponent(3);
                                    JTextField surfaceField = (JTextField) propertyPanel.getComponent(5);
                                    JTextField nombreEtagesField = (JTextField) propertyPanel.getComponent(7);
                                    JTextField adresseField = (JTextField) propertyPanel.getComponent(9);
                                    // Creation du bien immobilier avec les attribut entres par le user
                                    Immobilier immobilier = new Immobilier(idImmobilierField.getText(), typeImmobilierField.getText(),
                                            Double.parseDouble(surfaceField.getText()), Integer.parseInt(nombreEtagesField.getText()),
                                            adresseField.getText());
                                    agenceAssurance.enregistrerImmobilier(immobilier);
                                    Souscription newSouscription = new Souscription(year, price, client, formule, immobilier);
                                    agenceAssurance.nouvelleSouscription(newSouscription);
                                    //Verifie si la connexion n'est pas null pour enregistrer la souscription et le bien immobilier
                                    if(connexion != null) {
	                                    try {
	                                        immobilierDAO.saveImmobilier(immobilier);
	                                        souscriptionDAO.saveSouscription(newSouscription,idImmobilierField.getText());	                                       
	                                    } catch (SQLException ex) {
	                                        ex.printStackTrace();
	                                    }
                                    }
                                    //Affiche les resultat dans le textArea
                                    textArea.append("L'immobilier "+idImmobilierField.getText()+" a ete ajoutée avec succés!\n");
                                    textArea.append("Nouvelle souscription (pour l'immobilier "+idImmobilierField.getText()+") a ete ajoutée avec succès !\n");

                                }
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques valides pour l'année et le prix.",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(boutonNouvelleSouscription);

//--------------------------------------------------------------------------------------------------------------------------------------------
        
     // Action à effectuer lorsque le bouton pour afficher les formules d'assurance est cliqué
        boutonAfficherFormules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupération de la liste des formules d'assurance existantes depuis l'agence d'assurance
                List<FormuleAssurance> formules = agenceAssurance.afficherFormulesExistantes();

                // Construction du message à afficher en utilisant StringBuilder
                StringBuilder message = new StringBuilder();
                message.append("Formules d'assurance existantes :\n");

                // Ajout des détails de chaque formule au message
                for (FormuleAssurance formule : formules) {
                    message.append("ID : ").append(formule.getIdFormule()).append(", Description : ").append(formule.getDescriptionFormule()).append("\n");
                }

                // Affichage du message à l'aide de JOptionPane
                JOptionPane.showMessageDialog(null, message.toString(), "Formules d'assurance existantes", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(boutonAfficherFormules);

 //--------------------------------------------------------------------------------------------------------------------------------------
     // Action à effectuer lorsque le bouton pour afficher les souscriptions d'une année donnée est cliqué
        boutonAfficherSouscriptionsAnnee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Création d'un champ de texte pour saisir l'année
                JTextField yearField = new JTextField(10);
                JPanel panel = new JPanel(new GridLayout(1, 2));
                panel.add(new JLabel("Année :"));
                panel.add(yearField);

                // Affichage de la boîte de dialogue pour saisir l'année
                int result = JOptionPane.showConfirmDialog(null, panel, "Afficher les souscriptions pour une année donnée",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                // Si l'utilisateur clique sur OK
                if (result == JOptionPane.OK_OPTION) {
                    String yearStr = yearField.getText();
                    
                    // Vérification si l'année est non vide
                    if (!yearStr.isEmpty()) {
                        try {
                            // Conversion de l'année en entier
                            int year = Integer.parseInt(yearStr);
                            // Récupération de la liste des souscriptions pour l'année donnée depuis l'agence d'assurance
                            List<Souscription> subscriptions = agenceAssurance.afficherSouscriptionsAnnee(year);
                            
                            // Construction des détails des souscriptions à afficher
                            StringBuilder subscriptionDetails = new StringBuilder();
                            for (Souscription souscription : subscriptions) {
                                subscriptionDetails.append("Annee: ").append(souscription.getAnnee()).append("\n");
                                subscriptionDetails.append("Prix: ").append(souscription.getPrix()).append("\n");
                                // Utilisation de l'identifiant du client, de la formule et du bien au lieu des objets complets
                                subscriptionDetails.append("ID Client: ").append(souscription.getClient().getIdentifiant()).append("\n");
                                subscriptionDetails.append("ID Formule: ").append(souscription.getFormule().getIdFormule()).append("\n");
                                subscriptionDetails.append("ID Bien: ").append(souscription.getBien().getBien()).append("\n");
                                subscriptionDetails.append("---------------------------\n");
                            }
                            
                            // Affichage des détails des souscriptions dans une boîte de dialogue
                            JOptionPane.showMessageDialog(null, subscriptionDetails.toString(), "Souscriptions pour l'année " + year, JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Format d'année incorrect ! Utilisez un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer une année.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(boutonAfficherSouscriptionsAnnee);
  //-------------------------------------------------------------------------------------------------------------------------------------

     // Action à effectuer lorsque le bouton pour afficher les souscriptions d'un client est cliqué
        boutonAfficherSouscriptionsClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Demande à l'utilisateur de saisir l'identifiant du client
                String identifiantClient = JOptionPane.showInputDialog("Entrez l'identifiant du client :");

                // Vérifie si l'identifiant n'est pas vide
                if (identifiantClient != null && !identifiantClient.isEmpty()) {
                    // Recherche le client par identifiant depuis l'agence d'assurance
                    Client client = agenceAssurance.rechercherClientParIdentifiant(identifiantClient);

                    // Vérifie si le client existe
                    if (client != null) {
                        // Récupère la liste des souscriptions pour le client
                        List<Souscription> souscriptions = agenceAssurance.afficherSouscriptionsClient(client);

                        // Vérifie s'il y a des souscriptions pour ce client
                        if (!souscriptions.isEmpty()) {
                            // Construit le message pour afficher les souscriptions
                            StringBuilder message = new StringBuilder();
                            message.append("Souscriptions du client ").append(client.getNom()).append(" ").append(client.getPrenom()).append(" :").append("\n");
                            for (Souscription souscription : souscriptions) {
                                message.append(souscription.toString()).append("\n");
                            }

                            // Affiche la boîte de dialogue avec les souscriptions
                            JOptionPane.showMessageDialog(null, message.toString(), "Souscriptions du client", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Aucune souscription trouvée pour ce client.", "Aucune souscription", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Client non trouvé.", "Client non trouvé", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(boutonAfficherSouscriptionsClient);
 //---------------------------------------------------------------------------------------------------------------------------------------------------

     // Action à effectuer lorsque le bouton pour afficher les clients assurant une voiture est cliqué
        boutonAfficherClientsAvecVoiture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupère la liste des clients assurant une voiture depuis l'agence d'assurance
                List<Client> clientsAvecVoiture = agenceAssurance.afficherClientsAssurantVoiture();
                
                // Vérifie si aucun client n'assure une voiture
                if (clientsAvecVoiture.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun client n'assure une voiture.", "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Construit le message contenant la liste des clients assurant une voiture
                    StringBuilder message = new StringBuilder();
                    message.append("Liste des clients assurant une voiture :\n");
                    for (Client client : clientsAvecVoiture) {
                        message.append(client.toString()).append("\n");
                    }
                    // Affiche la boîte de dialogue avec la liste des clients assurant une voiture
                    JOptionPane.showMessageDialog(null, message.toString(), "Clients assurant une voiture", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonPanel.add(boutonAfficherClientsAvecVoiture);
 //-----------------------------------------------------------------------------------------------------------------------------------------------

     // Action à effectuer lorsque le bouton pour afficher les clients assurant un bien immobilier est cliqué
        boutonAfficherClientsAvecImmobilier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupère la liste des clients assurant un bien immobilier depuis l'agence d'assurance
                List<Client> clientsAvecImmobilier = agenceAssurance.afficherClientsAssurantImmobilier();

                // Vérifie s'il y a des clients avec des biens immobiliers
                if (!clientsAvecImmobilier.isEmpty()) {
                    // Construit le message contenant la liste des clients assurant un bien immobilier
                    StringBuilder message = new StringBuilder("Clients assurant un bien immobilier :\n");
                    for (Client client : clientsAvecImmobilier) {
                        message.append(client.toString()).append("\n");
                    }
                    // Affiche la boîte de dialogue avec la liste des clients assurant un bien immobilier
                    JOptionPane.showMessageDialog(null, message.toString(), "Clients assurant un bien immobilier", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun client n'assure un bien immobilier.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonPanel.add(boutonAfficherClientsAvecImmobilier);

//-----------------------------------------------------------------------------------------------------------------------------------------------
     // Action à effectuer lorsque le bouton pour supprimer un client est cliqué
        boutonSupprimerClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<Client> clients = agenceAssurance.getClients();

                // Vérifions si la liste des clients est vide
                if (clients.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun client trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Créons un tableau de chaînes pour stocker les identifiants des clients
                String[] clientIdentifiers = new String[clients.size()];
                for (int i = 0; i < clients.size(); i++) {
                    Client client = clients.get(i);
                    clientIdentifiers[i] = client.getIdentifiant() + ": " + client.getNom() + " " + client.getPrenom();
                }

                // Affichons une boîte de dialogue avec la liste des clients et demander à l'utilisateur de sélectionner l'ID du client à supprimer
                String selectedClientIdentifier = (String) JOptionPane.showInputDialog(null, "Sélectionnez l'identifiant du client à supprimer :",
                        "Supprimer un client", JOptionPane.QUESTION_MESSAGE, null, clientIdentifiers, clientIdentifiers[0]);

                // Vérifions si l'utilisateur a annulé l'opération
                if (selectedClientIdentifier == null) {
                    return; 
                }

                // On extrait l'identifiant du client sélectionné
                String identifiant = selectedClientIdentifier.split(":")[0].trim();

                // Recuperons le client à supprimer dans la liste des clients
                Client clientASupprimer = agenceAssurance.rechercherClientParIdentifiant(identifiant);

                // Verifions si le client existe
                if (clientASupprimer != null) {
      
                		int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce client et toutes ses souscriptions ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            // Récupérons les souscriptions associées au client
                            List<Souscription> souscriptionsClient = agenceAssurance.afficherSouscriptionsClient(clientASupprimer);
                            // Supprimons chaque souscription associée au client et les bien associes
                            for (Souscription souscription : souscriptionsClient) {
                            	
                            	//Pour la voiture
                                agenceAssurance.deleteSouscription(souscription);
                                if (souscription.getBien() instanceof Voiture) {
                                    Voiture voitureAssociee = (Voiture) souscription.getBien();
                                    agenceAssurance.deleteVoiture(voitureAssociee);
                                    agenceAssurance.deleteSouscription(souscription);
                                  //Verifie si la connexion n'est pas nulle pour supprimer la souscription associe au client et la voiture associee de la BD
                                    if(connexion != null) {
    	                                try {
    	                                	voitureDAO.deleteVoiture(voitureAssociee.getBien());
    	                                    souscriptionDAO.deleteSouscription(souscription.getBien().getBien());
    	                                } catch (SQLException ex) {
    	                                    ex.printStackTrace();
    	                                }
                                    }   
                                    //Pour limmobilier
                                } else if (souscription.getBien() instanceof Immobilier) {
                                    Immobilier immobilierAssocie = (Immobilier) souscription.getBien();
                                    agenceAssurance.deleteImmobilier(immobilierAssocie);
                                    agenceAssurance.deleteSouscription(souscription);
                                    //Verifie si la connexion n'est null pour supprimer la souscription associe au client et l'immobilier associee de la BD
                                      if(connexion != null) {
      	                                try {
      	                                	immobilierDAO.deleteImmobilier(immobilierAssocie.getBien());
      	                                    souscriptionDAO.deleteSouscription(souscription.getBien().getBien());
      	                                } catch (SQLException ex) {
      	                                    ex.printStackTrace();
      	                                }
                                      }       
                                }
                            }
                            // Supprimons le client lui-même
                            agenceAssurance.deleteClient(clientASupprimer);
                          //Verifie si la connexion n'est null pour supprimer le client et l'immobilier associee de la BD
                            if(connexion != null) {
                                try {
                                	clientDAO.deleteClient(identifiant);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }     
                            JOptionPane.showMessageDialog(null, "Client et toutes ses souscriptions supprimés avec succès avec les biens concernes.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                            textArea.append("Client"+selectedClientIdentifier+ " et les souscriptions associees(avec les bien associes) ont ete supprimer avec succes!\n");
                        }
                	
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Client introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(boutonSupprimerClient);
 //-------------------------------------------------------------------------------------------------------------------------------------
        
     // Action à effectuer lorsque le bouton pour supprimer une formule est cliqué
        boutonSupprimerFormule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JTextField idFormulaField = new JTextField(10);

                JPanel panel = new JPanel(new GridLayout(1, 2));
                panel.add(new JLabel("ID de la formule à supprimer :"));
                panel.add(idFormulaField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Supprimer une formule",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String idFormulaStr = idFormulaField.getText();

                    if (!idFormulaStr.isEmpty()) {
                        try {
                            int idFormula = Integer.parseInt(idFormulaStr);
                            

                            	// Verifions si le formule existe
                            	FormuleAssurance formule = agenceAssurance.rechercherFormuleParId(idFormula);
                            	if (formule == null) {
                                    JOptionPane.showMessageDialog(null, "Cette formule n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // SUPPRESSION DE LA SOUSCRIPTION ASSOCIEE A LA FORMULE (AVEC LE BIEN ASSOCIE SOIT VOITURE OU IMMOBILIER)
                                	Iterator<Souscription> iterator = agenceAssurance.getSouscriptions().iterator();
                            	    while (iterator.hasNext()) {
                            	        Souscription souscription = iterator.next();
                            	        if (souscription.getFormule().getIdFormule() == idFormula) {
                            	            iterator.remove(); //Supprime la souscription si elle est pour la formule a supprimer
                            	            if (souscription.getBien() instanceof Voiture) {
                                                Voiture voitureAssociee = (Voiture) souscription.getBien();
                                                agenceAssurance.deleteVoiture(voitureAssociee); 
                                                //Verifie si la connexion n'est pas null pour supprimer la souscription associe a la formule et la voiture associee de la BD
                                                if(connexion != null) {
                	                                try {
                	                                	souscriptionDAO.deleteSouscription(voitureAssociee.getBien());
                	                                	voitureDAO.deleteVoiture(voitureAssociee.getBien());
                	                                } catch (SQLException ex) {
                	                                    ex.printStackTrace();
                	                                }
                                                }
                                                    
                                            } else if (souscription.getBien() instanceof Immobilier) {
                                                Immobilier immobilierAssocie = (Immobilier) souscription.getBien();
                                                agenceAssurance.deleteImmobilier(immobilierAssocie);
                                                
                                             // Verifier si la connexion n'est nulle pas pour supprimer la souscription associe a la formule et l'immobilier associe dans la BD
                                                if(connexion != null) {
                	                                try {
                	                                	souscriptionDAO.deleteSouscription(immobilierAssocie.getBien());
                	                                	immobilierDAO.deleteImmobilier(immobilierAssocie.getBien());
                	                                } catch (SQLException ex) {
                	                                    ex.printStackTrace();
                	                                }
                                                }
                                                
                                            }
                            	           
                            	        }
                            	    }
                            	         
                                    // On supprime le formule de la liste des formules
                                    agenceAssurance.deleteFormuleAssurance(formule);
                                    
                                 // Verifier si la connexion n'est pas null pour supprimer la formule dans la BD
                                    if(connexion != null) {
    	                                try {
    	                                	formuleDAO.deleteFormuleAssurance(idFormula);
    	                                } catch (SQLException ex) {
    	                                    ex.printStackTrace();
    	                                }
                                    }
                                    
                                    JOptionPane.showMessageDialog(null, "La formule et la souscription associee ont ete supprimées avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                    textArea.append("Formule assurance "+idFormula+" et la souscriptions associee(avec le bien associe) ont ete supprimer avec succes!\n");
                                }
                            
                            
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer un ID de formule valide (entier).", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer l'ID de la formule.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.add(boutonSupprimerFormule);
//-----------------------------------------------------------------------------------------------------------------------------------   
        
     // Action à effectuer lorsque le bouton pour supprimer un supprimer est cliqué
        boutonSupprimerSouscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// On recupere la liste des souscriptions
                List<Souscription> souscriptions = agenceAssurance.getSouscriptions();

                // Vérifions si la liste des souscriptions est vide
                if (souscriptions.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune souscription trouvée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // On cree un tableau de chaînes pour stocker les identifiants des souscriptions
                String[] subscriptionIds = new String[souscriptions.size()];
                for (int i = 0; i < souscriptions.size(); i++) {
                    Souscription souscription = souscriptions.get(i);
                    subscriptionIds[i] = souscription.getBien().getBien(); 
                }

                // Affichage du boîte de dialogue avec la liste des souscriptions et on demande à l'utilisateur de sélectionner l'identifiant du bien associé à la souscription à supprimer
                String selectedSubscriptionId = (String) JOptionPane.showInputDialog(null, "Sélectionnez l'identifiant du bien associé à la souscription à supprimer :",
                        "Supprimer une souscription", JOptionPane.QUESTION_MESSAGE, null, subscriptionIds, subscriptionIds[0]);

                // Vérifions si l'utilisateur a annulé l'opération
                if (selectedSubscriptionId == null) {
                    return; 
                }

                // Rechercher la souscription à supprimer en fonction de l'identifiant du bien associé
                Souscription souscriptionAdelete = agenceAssurance.rechercherSouscriptionParIdBien(selectedSubscriptionId);
                // Vérifier si la souscription a été trouvée
                if (souscriptionAdelete != null) {
                    int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette souscription ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // Supprimons la souscription de la liste 
                        agenceAssurance.deleteSouscription(souscriptionAdelete);
                        
                     // Verifier si la connexion n'est pas null pour supprimer la souscription dans la BD
                        	if(connexion != null) {
                                try {
                                	souscriptionDAO.deleteSouscription(selectedSubscriptionId);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        	
                        	 // Supprimons le bien associé (voiture ou immobilier)
                            if (souscriptionAdelete.getBien() instanceof Voiture) {
                                Voiture voitureAssociee = (Voiture) souscriptionAdelete.getBien();
                                agenceAssurance.deleteVoiture(voitureAssociee);
                                
                                // Verifier si la connexion pour supprimer la voiture dans la BD
                                if(connexion != null) {
	                                try {
	                                	voitureDAO.deleteVoiture(selectedSubscriptionId);
	                                } catch (SQLException ex) {
	                                    ex.printStackTrace();
	                                }
                                 }
                                    
                             } else if (souscriptionAdelete.getBien() instanceof Immobilier) {
                                Immobilier immobilierAssocie = (Immobilier) souscriptionAdelete.getBien();
                                agenceAssurance.deleteImmobilier(immobilierAssocie);
                                
                             // Verifier si la connexion pour supprimer l'immobilier dans la BD
                                if(connexion != null) {
	                                try {
	                                	immobilierDAO.deleteImmobilier(selectedSubscriptionId);
	                                } catch (SQLException ex) {
	                                    ex.printStackTrace();
	                                }
                                }
                            }

                             
                        // Affichons un message indiquant que la souscription a été supprimée
                        JOptionPane.showMessageDialog(null, "Souscription supprimée et le bien associe ont ete supprimer avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        textArea.append("Souscription pour le bien "+selectedSubscriptionId+" et le bien associee ont ete supprimer avec succes!\n");
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Souscription introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }   
            }
        });
        buttonPanel.add(boutonSupprimerSouscription);
//-------------------------------------------------------------------------------------------------------------------------------------
        
        
        //Action a effectuer lorsque le Button quitter est cliqué
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose(); // On ferme la fenêtre principale de l'application
            }
        });
        buttonPanel.add(boutonQuitter);

        setVisible(true);
    }
 //--------------------------------------------------------------------------------------------------------------------------------------
    
    //Principale
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SenassurApp(); // Démarrage de l'interface graphique
            }
        });
    }
}
