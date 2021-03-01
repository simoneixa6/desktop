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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Eixa6
 */
public final class ClientsFormController implements Initializable {

    Interactors interactors;

    DesktopListener desktopListener;

    @FXML
    ToggleGroup civilityInput;

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

        companyStatusBox.getItems().addAll("SARL/EURL", "SAS/SASU", "SCI");

        //Binding à l'initialisation
        bind();

        // Si un client n'a pas d'id c'est que c'est un nouveau client
        if (getEditedClient().getId() == null) {
            registerBtn.setText(resources.getString("enregistrer"));
            deleteBtn.setDisable(true);
        } else {
            registerBtn.setText(resources.getString("modifier"));
            deleteBtn.setDisable(false);
        }

        /*
         * Action sur le clic du bouton "Enregistrer" / "Modifier"
         */
        registerBtn.setOnAction((ActionEvent actionEvent) -> {

            // Si le client ne possède pas d'id, il est nouveau, on enregistre
            if (getEditedClient().getId() == null) {
                try {
                    interactors.addClient.invoke(getEditedClient());
                } catch (ClientPostException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resources.getString("exception.erreur"));
                    alert.setHeaderText(resources.getString("exception.ajoutClient"));
                    alert.setContentText(e.toString());
                    alert.show();
                }
                // Si il possède un ID, il existe, donc on veut donc le modifier
            } else {
                try {
                    interactors.updateClient.invoke(getEditedClient());
                } catch (ClientPutException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resources.getString("exception.erreur"));
                    alert.setHeaderText(resources.getString("exception.modificationClient"));
                    alert.setContentText(e.toString());
                    alert.show();
                }
            }

            desktopListener.close();

        });

        /*
         * Action sur le clic du bouton "Supprimer"
         */
        deleteBtn.setOnAction((ActionEvent actionEvent) -> {
            try {
                interactors.removeClient.invoke(getEditedClient());
            } catch (ClientPutException e) {

                new Alert(Alert.AlertType.ERROR, resources.getString("exception.suppressionClient") + e.toString()).show();
            }
            desktopListener.close();
        });
    }

    private void bind() {

        //civility
        //problematicInput
        //addresses
        nameInput.textProperty().bindBidirectional(getEditedClient().getNameProperty());
        lastnameInput.textProperty().bindBidirectional(getEditedClient().getLastNameProperty());
        companyInput.textProperty().bindBidirectional(getEditedClient().getCompanyProperty());
        companyStatusBox.valueProperty().bindBidirectional(getEditedClient().getCompanyStatusProperty());
        phoneInput.textProperty().bindBidirectional(getEditedClient().getPhoneProperty());
        mailInput.textProperty().bindBidirectional(getEditedClient().getMailProperty());
        firstVisitDateInput.valueProperty().bindBidirectional(getEditedClient().getFirstVisitDateProperty());
        howInput.textProperty().bindBidirectional(getEditedClient().getHowProperty());
        whyInput.textProperty().bindBidirectional(getEditedClient().getWhyProperty());
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
