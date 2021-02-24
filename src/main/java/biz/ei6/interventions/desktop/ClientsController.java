/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

/**
 *
 * @author Eixa6
 */
class ClientsController implements Initializable, DesktopListener {

    @FXML
    SplitPane splitPane;

    @FXML
    ListView<Client> clientsListView;

    @FXML
    Button createBtn;

    Interactors interactors;

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    @Override
    public void close() {
        splitPane.getItems().remove(1);
        clientsListView.getSelectionModel().clearSelection();
        updateClientsListView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
         * Action sur le clic du bouton "Nouvelle Intervention"
         */
        createBtn.setOnAction((ActionEvent actionEvent) -> {
            ClientsForm clientsForm = new ClientsForm(interactors, new Client(), this);
            addClientsFormToSplitPane(clientsForm);
        });

    }

    private void addClientsFormToSplitPane(ClientsForm clientsForm) {

        /*
         * Supprime la partie formulaire d'intervention si elle est déjà présente
         */
        try {
            if (splitPane.getItems().size() > 1) {
                splitPane.getItems().remove(1);
                splitPane.getItems().add(1, clientsForm);
            } else {
                splitPane.getItems().add(1, clientsForm);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du formulaire d'intervention : " + e.toString()).show();
        }
    }

    public void updateClientsListView() {
        try {
            //       var data = interactors.getClients.invoke();
            //      var dataobs = FXCollections.observableArrayList(data);
            //    clientsListView.setItems(dataobs);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour de la liste des clients : " + e.toString()).show();
        }

    }
}
