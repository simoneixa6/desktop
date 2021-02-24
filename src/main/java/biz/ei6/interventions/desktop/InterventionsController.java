package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
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

public class InterventionsController implements Initializable, DesktopListener {

    @FXML
    SplitPane splitPane;

    @FXML
    ListView<Intervention> interventionsListView;

    @FXML
    Button createBtn;

    Interactors interactors;

    public void setInteractors(Interactors interactors) {
        this.interactors = interactors;
    }

    @Override
    public void close() {
        splitPane.getItems().remove(1);
        interventionsListView.getSelectionModel().clearSelection();
        updateInterventionsListView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
         * Mise en place de la cell factory de la listview des interventions
         */
        interventionsListView.setCellFactory(new InterventionCellFactory());

        // Muse à jour de la liste des interventions au démarrage
        //updateInterventionsListView();
        /*
         * Action lors de la selection d'une intervention dans la listview
         */
        interventionsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedInterventino, newSelectedIntervention) -> {
            if (newSelectedIntervention != null) {
                InterventionsForm interventionsForm = new InterventionsForm(interactors, newSelectedIntervention, this);
                addInterventionFormToSplitPane(interventionsForm);
            }
        });

        /*
         * Action sur le clic du bouton "Nouvelle Intervention"
         */
        createBtn.setOnAction((ActionEvent actionEvent) -> {
            InterventionsForm interventionsForm = new InterventionsForm(interactors, new Intervention(), this);
            addInterventionFormToSplitPane(interventionsForm);
        });
        
        
        updateInterventionsListView();
        
        
    }

    private void addInterventionFormToSplitPane(InterventionsForm interventionsForm) {

        /*
         * Supprime la partie formulaire d'intervention si elle est déjà présente
         */
        try {
            if (splitPane.getItems().size() > 1) {
                splitPane.getItems().remove(1);
                splitPane.getItems().add(1, interventionsForm);
            } else {
                splitPane.getItems().add(1, interventionsForm);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du formulaire d'intervention : " + e.toString()).show();
        }
    }

    public void updateInterventionsListView() {
        try {
            var data = interactors.getInterventions.invoke();
            var dataobs = FXCollections.observableArrayList(data);
            interventionsListView.setItems(dataobs);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de la récupération des interventions et la mise à jour de la liste : " + e.toString()).show();
        }

    }
}
