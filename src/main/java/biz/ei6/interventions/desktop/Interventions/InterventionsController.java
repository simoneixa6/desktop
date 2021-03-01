package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.framework.interventions.InterventionGetException;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

public class InterventionsController implements Initializable, DesktopListener {

    @FXML
    SplitPane splitPane;

    @FXML
    ListView<Intervention> interventionsListView;

    @FXML
    ComboBox sortBox;

    @FXML
    Button createBtn;

    Interactors interactors;

    ResourceBundle resources;

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

        this.resources = resources;

        // Remplissage de la choicebox de trie
        sortBox.setCellFactory(new SortBoxCellFactory());
        sortBox.getItems().addAll(resources.getString("tous.les.etats"), resources.getString("status.ouverte"), resources.getString("status.terminee"), resources.getString("status.facturee"), resources.getString("status.reglee"));
        sortBox.setValue(resources.getString("tous.les.etats"));

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
                addInterventionsFormToSplitPane(interventionsForm);
            }
        });

        /*
         * Action sur le clic du bouton "Nouvelle Intervention"
         */
        createBtn.setOnAction((ActionEvent actionEvent) -> {
            interventionsListView.getSelectionModel().clearSelection();
            InterventionsForm interventionsForm = new InterventionsForm(interactors, new Intervention(), this, resources);
            addInterventionsFormToSplitPane(interventionsForm);
        });

        // Muse à jour de la liste des interventions au démarrage
        updateInterventionsListView();

    }

    private void addInterventionsFormToSplitPane(InterventionsForm interventionsForm) {

        /*
         * Supprime la partie formulaire d'intervention si elle est déjà présente
         */
        if (splitPane.getItems().size() > 1) {
            splitPane.getItems().remove(1);
            splitPane.getItems().add(1, interventionsForm);
        } // Sinon la partie formulaire est ajouté
        else {
            splitPane.getItems().add(1, interventionsForm);
        }
    }

    public void updateInterventionsListView() {
            var dataobs = FXCollections.observableArrayList(getInteventions());
            interventionsListView.setItems(dataobs);
    }

    public ArrayList<Intervention> getInteventions() {
        try {
            return interactors.getInterventions.invoke();
        } catch (InterventionGetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(resources.getString("exception.erreur"));
            alert.setHeaderText(resources.getString("exception.recuperationInterventions"));
            alert.setContentText(e.toString());
            alert.show();
        }
        // Si erreur lors de la récupération, on renvoie une liste d'interventions vide
        return new ArrayList<>();
    }
}
