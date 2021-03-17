/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.framework.clients.ClientPostException;
import biz.ei6.interventions.desktop.framework.clients.ClientPutException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionGetException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPutException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Site;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Eixa6
 */
public final class ClientsFormController implements Initializable {

    Interactors interactors;

    DesktopListener desktopListener;

    @FXML
    Label titleLbl;

    @FXML
    ChoiceBox civilityBox;

    @FXML
    CheckBox problematicInput;

    @FXML
    TextField nameInput;

    @FXML
    TextField lastnameInput;

    @FXML
    TextField companyInput;

    @FXML
    ChoiceBox companyStatusBox;

    @FXML
    TextField phoneInput;

    @FXML
    TextField mailInput;

    @FXML
    DatePicker firstVisitDateInput;

    @FXML
    TextArea howInput;

    @FXML
    TextArea whyInput;

    @FXML
    Button deleteBtn;

    @FXML
    Button registerBtn;

    //TableView des adresses
    @FXML
    TableView<Site> siteTableView;
    @FXML
    TableColumn<Site, String> addressCol;
    @FXML
    TableColumn<Site, String> zipCodeCol;
    @FXML
    TableColumn<Site, String> cityCol;
    @FXML
    Button addAddressBtn;
    @FXML
    Button deleteAddressBtn;

    @FXML
    Label linkedInterventionsLbl;
    @FXML
    ListView<Intervention> linkedInterventionsListView;

    ResourceBundle resources;

    /**
     * Intervention éditée par la partie droite de l'interface
     */
    private final SimpleObjectProperty<Client> editedClient;

    ClientsFormController(SimpleObjectProperty<Client> client) {
        editedClient = client;
    }

    ClientsFormController(Client client) {
        editedClient = new SimpleObjectProperty<Client>(client);
    }

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    public void setDesktopListener(DesktopListener desktopListener) {
        this.desktopListener = desktopListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resources = resources;

        // Remplissage des choiceboxs
        String statutEntreprise1 = resources.getString("entreprise.earl");
        String statutEntreprise2 = resources.getString("entreprise.eirl");
        String statutEntreprise3 = resources.getString("entreprise.eurl");
        String statutEntreprise4 = resources.getString("entreprise.micro");
        String statutEntreprise5 = resources.getString("entreprise.sa");
        String statutEntreprise6 = resources.getString("entreprise.sarl");
        String statutEntreprise7 = resources.getString("entreprise.sas");
        String statutEntreprise8 = resources.getString("entreprise.sasu");

        companyStatusBox.getItems().addAll(statutEntreprise1, statutEntreprise2, statutEntreprise3, statutEntreprise4, statutEntreprise5, statutEntreprise6, statutEntreprise7, statutEntreprise8);

        civilityBox.getItems().addAll(resources.getString("m."), resources.getString("mme."));

        //Binding à l'initialisation
        bind();

        // Si un client n'a pas d'id c'est que c'est un nouveau client
        if (getEditedClient().getId() == null) {
            civilityBox.setValue(resources.getString("m."));
            registerBtn.setText(resources.getString("enregistrer"));
            deleteBtn.setDisable(true);
            titleLbl.setText(resources.getString("creer.un.client"));
        } else {
            registerBtn.setText(resources.getString("modifier"));
            deleteBtn.setDisable(false);
        }

        /**
         * Action sur le clic du bouton "Enregistrer" / "Modifier"
         */
        registerBtn.setOnAction((ActionEvent actionEvent) -> {

            // Client renvoyé par le serveur lors de l'ajout d'un client
            Client addedClient = null;

            // Si tous les champs obligatoires sont remplies
            if (validate() == true) {

                // Si le client ne possède pas d'id, il est nouveau, on le crée
                if (getEditedClient().getId() == null) {
                    try {
                        // Ajout du client, le serveur renvoie le client modifié    
                        addedClient = interactors.addClient.invoke(getEditedClient());

                    } catch (ClientPostException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.ajoutClient", e.toString());
                    }
                    // Si il possède un ID, il existe, donc on veut donc le modifier
                } else {
                    //On le modifie
                    try {
                        var id = getEditedClient().getId();

                        interactors.updateClient.invoke(getEditedClient());

                        addedClient = getEditedClient();
                    } catch (ClientPutException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.modificationClient", e.toString());
                    }

                    updateInterventionsWithThisClient();
                }

                // Renvoie le client ( utilisé lors de la création d'un client depuis le formulaire d'intervention )
                desktopListener.returnClient(addedClient);
                // Ferme la fenêtre et met à jour la liste des clients
                desktopListener.close();
            }
        });

        /*
         * Action sur le clic du bouton "Supprimer"
         */
        deleteBtn.setOnAction((ActionEvent actionEvent) -> {
            try {
                interactors.removeClient.invoke(getEditedClient());
            } catch (ClientPutException e) {
                showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.suppressionClient", e.toString());
            }
            // Renvoie un nouveau client ( afin de deselectionné l'ancien client dans le formulaire d'intervention
            desktopListener.returnClient(null);
            desktopListener.close();
        });

        /*
         * Action sur le clic du bouton "Ajouter" pour la tableview des adresses
         */
        addAddressBtn.setOnAction((ActionEvent actionEvent) -> {
            Site site = new Site();
            site.setAddress("");
            site.setZipCode("");
            site.setCity("");
            siteTableView.getItems().add(site);
        });

        /*
         * Action sur le clic du bouton "Supprimer" pour la tableview des adresses
         */
        deleteAddressBtn.setOnAction((ActionEvent actionEvent) -> {
            ObservableList<Site> sites;
            sites = siteTableView.getItems();

            // Renvoie les sites séléctionnés
            Site selectedSite = siteTableView.getSelectionModel().getSelectedItem();

            sites.remove(selectedSite);
        });

        /*
         * Mise en place de la table view des adresses
         */
        addressCol.setCellValueFactory(new PropertyValueFactory<Site, String>("address"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<Site, String>("zipCode"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Site, String>("city"));

        // Autoriser l'édition de la tableview des adresses, ajout des cellfactorys
        siteTableView.setEditable(true);
        addressCol.setCellFactory(column -> new StringEditableCell(column));
        cityCol.setCellFactory(column -> new StringEditableCell(column));
        zipCodeCol.setCellFactory(column -> new NumberEditableCell(column));
    }

    // FBR : j'ai un sushi métaphysique avec ce code : mise à jour de l'intervention dans le contrôleur de clients    
    private void updateInterventionsWithThisClient() {
        ArrayList<Intervention> interventions = new ArrayList<Intervention>();

        // Récupération des interventions pour les mettres à jour si besoin
        try {
            interventions = interactors.getInterventions.invoke();
        } catch (InterventionGetException e) {
            showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.recuperationInterventions", e.toString());
        }

        // Mise à jour des interventions possédant ce client avec les nouvelles informations du client
        try {
            for (var intervention : interventions) {
                if (getEditedClient().getId().equals(intervention.getClient().getId())) {
                    intervention.setClient(getEditedClient());
                    interactors.updateIntervention.invoke(intervention);
                }
            }

        } catch (InterventionPutException e) {
            showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.modificationIntervention", e.toString());
        }
    }

    public void ChangeAddressCellEvent(CellEditEvent editedCell) {
        Site selectedSite = siteTableView.getSelectionModel().getSelectedItem();
        selectedSite.setAddress(editedCell.getNewValue().toString());
    }

    public void ChangeZipCodeCellEvent(CellEditEvent editedCell) {
        Site selectedSite = siteTableView.getSelectionModel().getSelectedItem();
        selectedSite.setZipCode(editedCell.getNewValue().toString());
    }

    public void ChangeCityCellEvent(CellEditEvent editedCell) {
        Site selectedSite = siteTableView.getSelectionModel().getSelectedItem();
        selectedSite.setCity(editedCell.getNewValue().toString());
    }

    /**
     * Methode permettant de valider si les champs obligatoires sont bien
     * remplies
     *
     * @return
     */
    public boolean validate() {

        StringBuilder errors = new StringBuilder();

        // Vérifie qu'au moins un nom de client ou un nom d'entreprise a été renseigné
        if (lastnameInput.getText() == null || "".equals(lastnameInput.getText())) {
            if (companyInput.getText() == null || "".equals(companyInput.getText())) {
                errors.append(resources.getString("warning.nomClient"));
            }
        }
        if (companyInput.getText() == null || "".equals(companyInput.getText())) {
            if (lastnameInput.getText() == null || "".equals(lastnameInput.getText())) {
                errors.append(resources.getString("warning.nomEntreprise"));
            }
        }

        // Si une information est manquante, montre un message d'erreur et renvoie false
        if (errors.length() > 0) {
            showAlert(resources, AlertType.WARNING, "warning.attention", "warning.champsObligatoires", errors.toString());
            return false;
        }
        // Pas d'erreur
        return true;
    }

    /**
     * Mérhode permettant de bind les champs du formulaire et les attributs de
     * notre objet Client
     */
    private void bind() {
        siteTableView.itemsProperty().bindBidirectional(getEditedClient().getAddressesProperty());
        civilityBox.valueProperty().bindBidirectional(getEditedClient().getCivilityProperty());
        problematicInput.selectedProperty().bindBidirectional(getEditedClient().getProblematicProperty());
        nameInput.textProperty().bindBidirectional(getEditedClient().getNameProperty());
        lastnameInput.textProperty().bindBidirectional(getEditedClient().getLastnameProperty());
        companyInput.textProperty().bindBidirectional(getEditedClient().getCompanyProperty());
        companyStatusBox.valueProperty().bindBidirectional(getEditedClient().getCompanyStatusProperty());
        phoneInput.textProperty().bindBidirectional(getEditedClient().getPhoneProperty());
        mailInput.textProperty().bindBidirectional(getEditedClient().getMailProperty());
        firstVisitDateInput.valueProperty().bindBidirectional(getEditedClient().getFirstVisitDateProperty());
        howInput.textProperty().bindBidirectional(getEditedClient().getHowProperty());
        whyInput.textProperty().bindBidirectional(getEditedClient().getWhyProperty());
    }

    /**
     * Méthode permettant de faire apparaitres une alerte
     *
     * @param resources
     * @param alertType
     * @param titleProperty
     * @param exceptionProperty
     * @param contentText
     */
    private void showAlert(ResourceBundle resources, Alert.AlertType alertType, String titleProperty, String exceptionProperty, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(resources.getString(titleProperty));
        alert.setHeaderText(resources.getString(exceptionProperty));
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * @return the editedClient
     */
    public Client getEditedClient() {
        return editedClient.get();
    }

    public void setEditedClient(Client editedClient) {
        this.editedClient.set(editedClient);
    }

    /**
     * @return the editedIntervention
     */
    public SimpleObjectProperty<Client> getEditedClientProperty() {
        return editedClient;
    }
}
