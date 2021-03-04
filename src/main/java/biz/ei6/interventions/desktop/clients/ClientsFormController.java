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
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Site;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author Eixa6
 */
public final class ClientsFormController implements Initializable {

    Interactors interactors;

    DesktopListener desktopListener;

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

    /**
     * Intervention éditée par la partie droite de l'interface
     */
    private final SimpleObjectProperty<Client> editedClient = new SimpleObjectProperty<Client>();

    ClientsFormController(Client client) {
        setEditedClient(client);
    }

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    public void setDesktopListener(DesktopListener desktopListener) {
        this.desktopListener = desktopListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Remplissage des choiceboxs
        companyStatusBox.getItems().addAll("SARL/EURL", "SAS/SASU", "SCI");
        civilityBox.getItems().addAll(resources.getString("m."), resources.getString("mme."));

        //Binding à l'initialisation
        bind();

        // Si un client n'a pas d'id c'est que c'est un nouveau client
        if (getEditedClient().getId() == null) {
            civilityBox.setValue(resources.getString("m."));
            registerBtn.setText(resources.getString("enregistrer"));
            deleteBtn.setDisable(true);
        } else {
            registerBtn.setText(resources.getString("modifier"));
            deleteBtn.setDisable(false);
        }

        /**
         * Action sur le clic du bouton "Enregistrer" / "Modifier"
         */
        registerBtn.setOnAction((ActionEvent actionEvent) -> {
            // Si tous les champs obligatoires sont remplies
            if (validate(resources) == true) {
                // Si le client ne possède pas d'id, il est nouveau, on enregistre
                if (getEditedClient().getId() == null) {
                    try {
                        interactors.addClient.invoke(getEditedClient());
                    } catch (ClientPostException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.ajoutClient", e.toString());
                    }
                    // Si il possède un ID, il existe, donc on veut donc le modifier
                } else {
                    try {
                        interactors.updateClient.invoke(getEditedClient());
                    } catch (ClientPutException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.modificationClient", e.toString());
                    }
                }
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
            ObservableList<Site> selectedSites, sites;
            sites = siteTableView.getItems();

            // Renvoie les sites séléctionnés
            selectedSites = siteTableView.getSelectionModel().getSelectedItems();

            selectedSites.forEach(site -> {
                sites.remove(site);
            });
        });

        /**
         * Text formatter sur le champ du numéro de téléphone pour accepter que
         * des entiers
         */
        Pattern pattern = Pattern.compile("\\d*");
        TextFormatter onlyNumbersformatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        phoneInput.setTextFormatter(onlyNumbersformatter);

        /*
         * Mise en place de la table view des adresses
         */
        addressCol.setCellValueFactory(new PropertyValueFactory<Site, String>("address"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<Site, String>("zipCode"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Site, String>("city"));

        //Allow the table to be editable
        siteTableView.setEditable(true);
        siteTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addressCol.setCellFactory(column -> new StringEditableCell(column));
        cityCol.setCellFactory(column -> new StringEditableCell(column));        
        zipCodeCol.setCellFactory(column -> new NumberEditableCell(column));
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
     * @param resources
     * @return
     */
    public boolean validate(ResourceBundle resources) {

        StringBuilder errors = new StringBuilder();

        // Vérifie que les champs obligatoires soient bien remplies
        if (nameInput.getText() == null || "".equals(nameInput.getText())) {
            errors.append(resources.getString("warning.nom"));
        }
        if (phoneInput.getText() == null || "".equals(phoneInput.getText())) {
            errors.append(resources.getString("warning.telephone"));
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
