/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.beans.binding.ObjectBinding;
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
    TextField NameInput;

    @FXML
    TextArea DescriptionInput;

    @FXML
    TextField KmInput;

    @FXML
    DatePicker BillDateInput;

    @FXML
    TextField BillNumberInput;

    @FXML
    DatePicker PaymentDateInput;

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
        if (getEditedIntervention().getId() == null) {

            // Valeurs pas défault pour une nouvelle intervention
            statusBox.setValue("Ouverte");
            paymenttypeBox.setValue("Chèque");

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

            switch (registerBtn.getText()) {
                case "Enregistrer":
                    try {
                    interactors.addIntervention.invoke(getEditedIntervention());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de l'intervention :" + e.toString()).show();
                }
                break;
                case "Modifier":
                    try {
                    interactors.updateIntervention.invoke(getEditedIntervention());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification de l'intervention :" + e.toString()).show();
                }
                break;
            }

            desktopListener.close();

        });

        /*
         * Action sur le clic du bouton "Supprimer"
         */
        deleteBtn.setOnAction((ActionEvent actionEvent) -> {
            try {
                interactors.removeIntervention.invoke(getEditedIntervention());
            } catch (Exception e) {

                new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression de l'intervention : " + e.toString()).show();
            }

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
        NameInput.textProperty().bindBidirectional(getEditedIntervention().getTitleProperty());
        DescriptionInput.textProperty().bindBidirectional(getEditedIntervention().getDescriptionProperty());
        KmInput.textProperty().bindBidirectional(getEditedIntervention().getKmProperty());
        BillDateInput.valueProperty().bindBidirectional(getEditedIntervention().getBillDateProperty());
        BillNumberInput.textProperty().bindBidirectional(getEditedIntervention().getBillNumberProperty());
        PaymentDateInput.valueProperty().bindBidirectional(getEditedIntervention().getPaymentDateProperty());
        statusBox.valueProperty().bindBidirectional(getEditedIntervention().getStatusProperty());
        paymenttypeBox.valueProperty().bindBidirectional(getEditedIntervention().getPaymentTypeProperty());
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
