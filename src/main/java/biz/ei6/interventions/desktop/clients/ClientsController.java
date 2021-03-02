package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

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
                ClientsForm interventionsForm = new ClientsForm(interactors, newSelectedClient, this, resources);
                addClientsFormToSplitPane(interventionsForm);
            }
        });
        
        /*
         * Action sur le clic du bouton "Nouveau client"
         */
        createBtn.setOnAction((ActionEvent actionEvent) -> {
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
            splitPane.getItems().remove(1);
            splitPane.getItems().add(1, clientsForm);
        } // Sinon ajoute la partie formulaire
        else {
            splitPane.getItems().add(1, clientsForm);
        }
    }

    public void updateClientsListView() {
            var dataobs = FXCollections.observableArrayList(getClients());
            clientsListView.setItems(dataobs);
    }

    public ArrayList<Client> getClients() {
        try {
            return interactors.getClients.invoke();
        } catch (ClientGetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la récupération des clients (GET) :");
            alert.setContentText(e.toString());
            alert.show();
        }
        return new ArrayList<>();
    }
}
