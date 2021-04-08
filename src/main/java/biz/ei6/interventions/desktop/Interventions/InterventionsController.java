package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.framework.interventions.InterventionGetException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Status;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class InterventionsController implements Initializable, DesktopListener {

    @FXML
    SplitPane splitPane;

    @FXML
    ListView<Intervention> interventionsListView;

    @FXML
    ComboBox<Status> sortBox;

    @FXML
    ToggleButton sortByDatesBtn;

    @FXML
    ToggleButton sortByClientsBtn;

    @FXML
    Button createBtn;

    Interactors interactors;

    ResourceBundle resources;

    Boolean wasNotSaved = false;

    // Statuts de la combobox de filtrage
    Status status0; // Valeur par défaut, pas de filtrage
    Status status1;
    Status status2;
    Status status3;
    Status status4;

    public void setInteractors(Interactors interactors) {
        this.interactors = interactors;
    }

    @Override
    public void close() {
        wasNotSaved = false;
        splitPane.getItems().remove(1);
        interventionsListView.getSelectionModel().clearSelection();
        updateInterventionsListView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resources = resources;

        // Création des statuts d'intervention
        this.status0 = new Status("0", resources.getString("tous.les.etats")); // Valeur par défaut, pas de filtrage
        this.status1 = new Status("1", resources.getString("status.ouverte"));
        this.status2 = new Status("2", resources.getString("status.terminee"));
        this.status3 = new Status("3", resources.getString("status.facturee"));
        this.status4 = new Status("4", resources.getString("status.reglee"));

        // Remplissage de la choicebox de filtrage
        sortBox.setCellFactory(new SortBoxCellFactory());
        sortBox.getItems().addAll(status0, status1, status2, status3, status4);
        sortBox.setValue(status0);

        sortBox.setConverter(new StringConverter<Status>() {
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
        sortBox.valueProperty().addListener(new ChangeListener<Status>() {
            @Override
            public void changed(ObservableValue ov, Status oldStatus, Status newStatus) {
                if (newStatus != null) {
                    updateInterventionsListView();
                }
            }
        });

        sortByDatesBtn.setOnAction((ActionEvent event) -> {
            updateInterventionsListView();
        });

        sortByClientsBtn.setOnAction((ActionEvent event) -> {
            updateInterventionsListView();
        });

        /*
         * Mise en place de la cell factory de la listview des interventions
         */
        interventionsListView.setCellFactory(new InterventionCellFactory());

        /*
         * Action lors de la selection d'une intervention dans la listview
         */
        interventionsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedIntervention, newSelectedIntervention) -> {
            if (newSelectedIntervention != null) {
                InterventionsForm interventionsForm = new InterventionsForm(interactors, newSelectedIntervention, this, resources);
                addInterventionsFormToSplitPane(interventionsForm, oldSelectedIntervention);

                interventionsForm.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    wasNotSaved = true;
                });
            }
        });

        /*
         * Action sur le clic du bouton "Nouvelle Intervention"
         */
        createBtn.setOnAction((ActionEvent actionEvent) -> {
            interventionsListView.getSelectionModel().clearSelection();
            InterventionsForm interventionsForm = new InterventionsForm(interactors, new Intervention(), this, resources);
            addInterventionsFormToSplitPane(interventionsForm, null);
        });

        // Mise à jour de la liste des interventions au démarrage
        updateInterventionsListView();
    }

    private void addInterventionsFormToSplitPane(InterventionsForm interventionsForm, Intervention oldSelectedIntervention) {
        /*
         * Supprime la partie formulaire d'intervention si elle est déjà présente
         */
        if (splitPane.getItems().size() > 1) {

            // Affiche une boite de dialogue si l'utilisateur n'a pas enregistré avant de changer d'intervention
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
                    splitPane.getItems().add(1, interventionsForm);

                    Platform.runLater(() -> {
                        updateInterventionsListView();
                    });

                    // Sinon on conserve le formulaire et on deselectionne l'élément afin que l'utilisateur continue sa modification
                } else if (result.get() == ButtonType.CANCEL) {
                    Platform.runLater(interventionsListView.getSelectionModel()::clearSelection);
                }
                // Si l'utilisateur n'a pas effectué de modification, on remplace le formulaire par le nouveau
            } else {
                splitPane.getItems().remove(1);
                splitPane.getItems().add(1, interventionsForm);
            }

        } // Sinon la partie formulaire est ajouté
        else {
            splitPane.getItems().add(1, interventionsForm);
        }
    }

    /**
     * Méthode de mise à jour de la listview
     */
    public void updateInterventionsListView() {
        var interventions = FXCollections.observableArrayList(getInterventions());

        // Supprime les interventions possédant le statut selectionné dans la combobox de tri, si l'id vaut 0 (valeur par défaut), on ne filtre pas
        if (!sortBox.getValue().getId().equals("0")) {
            interventions.removeIf(intervention -> !intervention.getStatus().getId().equals(sortBox.getValue().getId()));
        }

        // Les nouvelles interventions sont affichées en haut de la liste et pas en bas
        Collections.reverse(interventions);

        // Tri des interventions par clients
        if (sortByClientsBtn.isSelected() && !sortByDatesBtn.isSelected()) {
            interventions.sort(new SortInterventionsByClients());
        }

        // Tri des interventions par date ( la première date de l'intervention )
        if (sortByDatesBtn.isSelected() && !sortByClientsBtn.isSelected()) {
            interventions.sort(new SortInterventionsByDates());
        }

        // Tri des interventions par client et par date
        if (sortByDatesBtn.isSelected() && sortByClientsBtn.isSelected()) {
            interventions.sort(new SortInterventionsByClientsAndDates());
        }

        interventionsListView.setItems(interventions);
    }

    public ArrayList<Intervention> getInterventions() {
        try {
            return interactors.getInterventions.invoke();
        } catch (InterventionGetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(resources.getString("exception.erreur"));
            alert.setHeaderText(resources.getString("exception.serveur.injoignable") + resources.getString("exception.recuperationInterventions"));
            alert.setContentText(resources.getString("exception.serveur.injoignable.detail") + "\n" + "\n" + e.toString());
            alert.showAndWait();
        }
        // Si erreur lors de la récupération, on renvoie une liste d'interventions vide
        return new ArrayList<>();
    }

    @Override
    public void returnClient(Client client) {
    }
}
