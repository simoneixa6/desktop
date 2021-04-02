package biz.ei6.interventions.desktop.restitutions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.framework.interventions.InterventionGetException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Status;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * @author Eixa6
 */
class RestitutionsController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @FXML
    ChoiceBox<Status> statusChoiceBox;

    @FXML
    TextField nbrOfSelectedInterventions;

    @FXML
    TextField nbrOfKmOfSelectedInterventions;

    @FXML
    TextField keyWordInput;

    @FXML
    Button keyWordBtn;

    @FXML
    Button clearKeyWordInputBtn;

    @FXML
    TableView<Intervention> interventionsTableView;

    @FXML
    TableColumn<Intervention, Status> statusCol;
    @FXML
    TableColumn<Intervention, String> titleCol;
    @FXML
    TableColumn<Intervention, Client> clientCol;
    @FXML
    TableColumn<Intervention, String> descriptionCol;
    @FXML
    TableColumn<Intervention, String> goKmCol;
    @FXML
    TableColumn<Intervention, String> backKmCol;
    @FXML
    TableColumn<Intervention, String> kmCol;
    @FXML
    TableColumn<Intervention, String> billNumberCol;
    @FXML
    TableColumn<Intervention, LocalDate> billDateCol;
    @FXML
    TableColumn<Intervention, String> paymentTypeCol;
    @FXML
    TableColumn<Intervention, LocalDate> paymentDateCol;

    Interactors interactors;

    ResourceBundle resources;

    // Statuts de la combobox de filtrage
    Status status0;
    Status status1;
    Status status2;
    Status status3;
    Status status4;

    void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        // Création des statuts d'intervention
        this.status0 = new Status("0", resources.getString("tous.les.etats"));
        this.status1 = new Status("1", resources.getString("status.ouverte"));
        this.status2 = new Status("2", resources.getString("status.terminee"));
        this.status3 = new Status("3", resources.getString("status.facturee"));
        this.status4 = new Status("4", resources.getString("status.reglee"));

        statusChoiceBox.getItems().addAll(status0, status1, status2, status3, status4);
        statusChoiceBox.setValue(status0);

        statusChoiceBox.setConverter(new StringConverter<Status>() {
            @Override
            public String toString(Status status) {
                return status.getName();
            }

            @Override
            public Status fromString(String string) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });

        /*
         * Listener sur la selection d'un type de tri
         */
        statusChoiceBox.valueProperty().addListener(new ChangeListener<Status>() {
            @Override
            public void changed(ObservableValue ov, Status oldStatus, Status newStatus) {
                if (newStatus != null) {
                    updateInterventionsListView();
                }
            }
        });

        /*
         * Mise en place de la table view des interventions
         */
        interventionsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        statusCol.setCellValueFactory(new PropertyValueFactory<Intervention, Status>("status"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("title"));
        clientCol.setCellValueFactory(new PropertyValueFactory<Intervention, Client>("client"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("description"));
        kmCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("km"));
        goKmCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("goKm"));
        backKmCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("backKm"));
        billNumberCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("billNumber"));
        billDateCol.setCellValueFactory(new PropertyValueFactory<Intervention, LocalDate>("billDate"));
        paymentTypeCol.setCellValueFactory(new PropertyValueFactory<Intervention, String>("paymentType"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<Intervention, LocalDate>("paymentDate"));

        billDateCol.setCellFactory(column -> new DateCell(column));
        paymentDateCol.setCellFactory(column -> new DateCell(column));

        statusCol.setCellFactory(column -> new TableCell<Intervention, Status>() {
            @Override
            protected void updateItem(Status status, boolean empty) {
                super.updateItem(status, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(status.getName());
                }
            }
        });

        clientCol.setCellFactory(column -> new TableCell<Intervention, Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty) {
                    setText("");
                } else {
                    StringBuilder clientString = new StringBuilder();

                    // Si il a prénom
                    if (client.getName() != null) {
                        clientString.append(client.getName()).append(" ");
                    }

                    // Si il a nom
                    if (client.getLastname() != null) {
                        clientString.append(client.getLastname());
                    }

                    // Si il a une entreprise
                    if (client.getCompany() != null) {
                        if (client.getName() != null || client.getLastname() != null) {
                            clientString.append(" - ").append(client.getCompany());
                        } else {
                            clientString.append(client.getCompany());
                        }
                    }
                    setText(clientString.toString());
                }
            }
        });

        /**
         * Permet de wrapper le texte dans la colonne description
         */
        descriptionCol.setCellFactory(tc -> {
            TableCell<Intervention, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(descriptionCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        keyWordInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sortInterventions();
            }
        });

        /**
         * Action sur le clic du bouton "Rechercher"
         */
        keyWordBtn.setOnAction((ActionEvent event) -> {
            sortInterventions();
        });

        /**
         * Action sur le clic du bouton "x"
         */
        clearKeyWordInputBtn.setOnAction((ActionEvent event) -> {
            keyWordInput.clear();
            sortInterventions();
        });

        /**
         * Calcul du nombre d'interventions selectionnées et du nombre de km
         * pour ces interventions
         */
        interventionsTableView.getSelectionModel().selectedItemProperty().addListener((var obs, var oldSelection, var newSelection) -> {
            if (newSelection != null) {
                // Nombre d'interventions selectionné
                nbrOfSelectedInterventions.setText(String.valueOf(interventionsTableView.getSelectionModel().getSelectedIndices().size()));

                // Calcul de la somme des km pour les interventions selectionné
                var interventions = interventionsTableView.getSelectionModel().getSelectedItems();
                var somme = interventions.stream().filter(intervention -> Objects.nonNull(intervention.getKm())).map(intervention -> Double.parseDouble(intervention.getKm())).reduce(0.0, (Double arg0, Double arg1) -> arg0 + arg1);
                nbrOfKmOfSelectedInterventions.setText(String.valueOf(somme));
            }
        });

        // Mise à jour de la table des interventions a l'initialisation
        updateInterventionsListView();
    }

    private void sortInterventions() {
        var interventions = FXCollections.observableArrayList(getInterventions());

        var filteredInterventions = interventions.stream().filter(intervention -> Objects.nonNull(intervention.getDescription())).filter(intervention -> intervention.getDescription().contains(keyWordInput.getText())).collect(Collectors.toList());

        var filteredInterventionsObs = FXCollections.observableArrayList(filteredInterventions);

        interventionsTableView.setItems(filteredInterventionsObs);
    }

    public void updateInterventionsListView() {

        var interventions = FXCollections.observableArrayList(getInterventions());

        // Supprime les interventions possédant le statut selectionné dans la combobox de filtre, si l'id vaut 0 (valeur par défaut), on ne filtre pas
        if (!statusChoiceBox.getValue().getId().equals("0")) {
            interventions.removeIf(intervention -> !intervention.getStatus().getId().equals(statusChoiceBox.getValue().getId()));
        }
        interventionsTableView.setItems(interventions);
    }

    public ArrayList<Intervention> getInterventions() {
        try {
            return interactors.getInterventions.invoke();
        } catch (InterventionGetException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resources.getString("exception.erreur"));
            alert.setHeaderText(resources.getString("exception.recuperationInterventions"));
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        // Si erreur lors de la récupération, on renvoie une liste d'interventions vide
        return new ArrayList<>();
    }
}
