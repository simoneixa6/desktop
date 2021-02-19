/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author Eixa6
 */
public class InterventionsFormController implements Initializable {

    @FXML
    TextField inputNom;

    @FXML
    TextArea inputDescription;

    @FXML
    TextField inputKm;

    @FXML
    DatePicker inputDateFacturation;

    @FXML
    TextField inputNumeroFacture;

    @FXML
    DatePicker inputDateReglement;

    @FXML
    TableView<Period> periodTableView;

    @FXML
    TableColumn<String, String> dateCol;

    @FXML
    TableColumn<String, String> startCol;

    @FXML
    TableColumn<String, String> endCol;

    @FXML
    TableColumn<String, String> durationCol;

    @FXML
    ChoiceBox<String> statusBox;

    @FXML
    ChoiceBox<String> paymenttypeBox;

    @FXML
    Button deleteBtn;

    @FXML
    Button registerBtn;

    Interactors interactors;

    DesktopListener desktopListener;

    Boolean isNewIntervention;

    /**
     * Intervention éditée par la partie droite de l'interface
     */
    private SimpleObjectProperty<Intervention> editedIntervention = new SimpleObjectProperty<Intervention>();

    InterventionsFormController(Intervention intervention) {
        setEditedIntervention(intervention);
    }

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    public void setDesktopListener(DesktopListener desktopListener) {
        this.desktopListener = desktopListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TEST
        statusBox.getItems().addAll("Ouverte", "Terminée", "Facturée", "Réglée");
        paymenttypeBox.getItems().addAll("Chèque", "Carte Bancaire", "Espèce");

        // Binding à l'initialisation
        bind();

        // Si une intervention n'a pas d'id c'est que c'est une nouvelle intervention
        if (getEditedIntervention().getId() == null ) {

            // Valeurs pas défault pour une nouvelle intervention
            getEditedIntervention().setStatus("Ouverte");
            getEditedIntervention().setPaymentType("Chèque");
            
            registerBtn.setText("Enregistrer");
            deleteBtn.setDisable(true);
        } else {

            registerBtn.setText("Modifier");
            deleteBtn.setDisable(false);

        }

        /*
         * Action sur le clic du bouton "Enregistrer" / "Modifier"
         */
        registerBtn.setOnAction((ActionEvent actionEvent) -> {

            try {
                switch (registerBtn.getText()) {
                    case "Enregistrer":
                        interactors.addIntervention.invoke(getEditedIntervention());
                        break;
                    case "Modifier":
                        interactors.updateIntervention.invoke(getEditedIntervention());
                        break;
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }

            desktopListener.close();

        });

        /*
         * Action sur le clic du bouton "Supprimer"
         */
        deleteBtn.setOnAction((ActionEvent actionEvent) -> {
            interactors.removeIntervention.invoke(getEditedIntervention().getId());
            desktopListener.close();
        });

        /*
        $ Mise en place de la table view des Périodes
         */
        periodTableView.setEditable(true);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateCol"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startCol"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endCol"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("durationCol"));

        dateCol.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        startCol.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        endCol.setCellFactory(TextFieldTableCell.<String>forTableColumn());
        durationCol.setCellFactory(TextFieldTableCell.<String>forTableColumn());

    }

    @FXML
    private void addPeriodRow() {
        Period period = new Period();
        periodTableView.getItems().add(period);
    }

    private void bind() {
        inputNom.textProperty().bindBidirectional(getEditedIntervention().getTitleProperty());
        inputDescription.textProperty().bindBidirectional(getEditedIntervention().getDescriptionProperty());
        inputKm.textProperty().bindBidirectional(getEditedIntervention().getKmProperty());
        inputDateFacturation.valueProperty().bindBidirectional(getEditedIntervention().getBillDateProperty());
        inputNumeroFacture.textProperty().bindBidirectional(getEditedIntervention().getBillNumberProperty());
        inputDateReglement.valueProperty().bindBidirectional(getEditedIntervention().getPaymentDateProperty());
        statusBox.valueProperty().bindBidirectional(getEditedIntervention().getStatusProperty());
        paymenttypeBox.valueProperty().bindBidirectional(getEditedIntervention().getPaymentTypeProperty());
    }

    private void unbind() {
        inputNom.textProperty().unbindBidirectional(getEditedIntervention().getTitleProperty());
        inputDescription.textProperty().unbindBidirectional(getEditedIntervention().getDescriptionProperty());
        inputKm.textProperty().unbindBidirectional(getEditedIntervention().getKmProperty());
        inputDateFacturation.valueProperty().unbindBidirectional(getEditedIntervention().getBillDateProperty());
        inputNumeroFacture.textProperty().unbindBidirectional(getEditedIntervention().getBillNumberProperty());
        inputDateReglement.valueProperty().unbindBidirectional(getEditedIntervention().getPaymentDateProperty());
        statusBox.valueProperty().unbindBidirectional(getEditedIntervention().getStatusProperty());
        paymenttypeBox.valueProperty().unbindBidirectional(getEditedIntervention().getPaymentTypeProperty());
    }

    /**
     * @return the editedIntervention
     */
    public Intervention getEditedIntervention() {
        return editedIntervention.get();
    }

    /**
     * @param editedIntervention the editedIntervention to set
     */
    public void setEditedIntervention(Intervention editedIntervention) {
        this.editedIntervention.set(editedIntervention);
    }

    /**
     * @return the editedIntervention
     */
    public SimpleObjectProperty<Intervention> getEditedInterventionProperty() {
        return editedIntervention;
    }

}
