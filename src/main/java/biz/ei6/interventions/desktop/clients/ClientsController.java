package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;

/*
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

    ResourceBundle resources;

    Boolean wasNotSaved = false;

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    @Override
    public void close() {
        wasNotSaved = false;
        splitPane.getItems().remove(1);
        clientsListView.getSelectionModel().clearSelection();
        updateClientsListView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resources = resources;

        /*
         * Mise en place de la cell factory de la listview des clients
         */
        clientsListView.setCellFactory(new ClientCellFactory());

        /*
         * Action lors de la selection d'un client dans la listview
         */
        clientsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedClient, newSelectedClient) -> {
            if (newSelectedClient != null) {
                ClientsForm clientsForm = new ClientsForm(interactors, newSelectedClient, this, resources);
                addClientsFormToSplitPane(clientsForm);

                clientsForm.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    wasNotSaved = true;
                });
            }
        });

        /*
         * Action sur le clic du bouton "Nouveau client"
         */
        createBtn.setOnAction((ActionEvent actionEvent) -> {
            clientsListView.getSelectionModel().clearSelection();
            ClientsForm clientsForm = new ClientsForm(interactors, new Client(), this, resources);
            addClientsFormToSplitPane(clientsForm);
        });

        // Muse à jour de la liste des clients au démarrage
        updateClientsListView();
    }

    private void addClientsFormToSplitPane(ClientsForm clientsForm) {
        /*
         * Supprime la partie formulaire si elle est déjà présente
         */
        if (splitPane.getItems().size() > 1) {

            // Affiche une boite de dialogue si l'utilisateur n'a pas enregistré avant de changer de client
            if (wasNotSaved == true) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(resources.getString("warning.attention"));
                alert.setHeaderText(resources.getString("warning.modification.non.enregistre"));
                alert.setContentText(resources.getString("warning.choix.modification.non.enregistre"));
                Optional<ButtonType> result = alert.showAndWait();
                // Si il appuie sur Ok, on ignore les anciens changements, et on met à jour la liste des interventions
                if (result.get() == ButtonType.OK) {
                    wasNotSaved = false;
                    splitPane.getItems().remove(1);
                    splitPane.getItems().add(1, clientsForm);

                    Platform.runLater(() -> {
                        updateClientsListView();
                    });

                    // Sinon on conserve le formulaire et on deselectionne l'élément afin que l'utilisateur continue sa modification
                } else if (result.get() == ButtonType.CANCEL) {
                    
                    Platform.runLater(clientsListView.getSelectionModel()::clearSelection);
                    
                }
                // Si l'utilisateur n'a pas effectué de modification, on remplace le formulaire par le nouveau
            } else {
                splitPane.getItems().remove(1);
                splitPane.getItems().add(1, clientsForm);
            }
        } // Sinon ajoute la partie formulaire
        else {
            splitPane.getItems().add(1, clientsForm);
        }
    }

    public void updateClientsListView() {
        var clientObs = FXCollections.observableArrayList(getClients());
        clientObs.sort(new SortClient());
        clientsListView.setItems(clientObs);
    }

    public ArrayList<Client> getClients() {
        try {
            return interactors.getClients.invoke();
        } catch (ClientGetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(resources.getString("exception.erreur"));
            alert.setHeaderText(resources.getString("exception.recuperationClients"));
            alert.setContentText(e.toString());
            alert.show();
        }
        return new ArrayList<>();
    }

    @Override
    public void returnClient(Client client) {
    }
}
