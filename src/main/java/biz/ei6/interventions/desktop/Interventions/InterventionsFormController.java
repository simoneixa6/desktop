package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPostException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPutException;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/*
 * @author Eixa6
 */
public final class InterventionsFormController implements Initializable {

    @FXML
    TextField nameInput;

    @FXML
    TextArea descriptionInput;

    @FXML
    TextField kmInput;

    @FXML
    DatePicker billDateInput;

    @FXML
    TextField billNumberInput;

    @FXML
    DatePicker paymentDateInput;

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

    /**
     * Intervention éditée par la partie droite de l'interface
     */
    private final SimpleObjectProperty<Intervention> editedIntervention = new SimpleObjectProperty<Intervention>();

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

        //Remplissage des choiceboxs
        statusBox.getItems().addAll(resources.getString("status.ouverte"), resources.getString("status.terminee"), resources.getString("status.facturee"), resources.getString("status.reglee"));
        paymenttypeBox.getItems().addAll(resources.getString("paiement.cheque"), resources.getString("paiement.cb"), resources.getString("paiement.espece"));

        // Binding à l'initialisation
        bind();

        // Si une intervention n'a pas d'id c'est que c'est une nouvelle intervention
        if (getEditedIntervention().getId() == null) {
            // Valeurs pas défault pour une nouvelle intervention
            statusBox.setValue(resources.getString("status.ouverte"));
            paymenttypeBox.setValue(resources.getString("paiement.cheque"));
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

            // Si l'intervention ne possède pas d'id, elle est nouvelle, on enregistre
            if (getEditedIntervention().getId() == null) {
                try {
                    interactors.addIntervention.invoke(getEditedIntervention());
                } catch (InterventionPostException e) {                    
                    showAlert(resources,"exception.ajoutIntervention",e);
                }
                // Si elle possède un ID, elle existe, on veut donc la modifier
            } else {
                try {
                    interactors.updateIntervention.invoke(getEditedIntervention());
                } catch (InterventionPutException e) {
                    showAlert(resources, "exception.modificationIntervention", e);
                }
            }

            desktopListener.close();

        });

        /*
         * Action sur le clic du bouton "Supprimer"
         */
        deleteBtn.setOnAction((ActionEvent actionEvent) -> {
            try {
                interactors.removeIntervention.invoke(getEditedIntervention());
            } catch (InterventionPutException e) {
                showAlert(resources, "exception.suppressionIntervention", e);
            }
            desktopListener.close();
        });

        /*
         * Text formatter sur le champ des kms pour accepter que des entiers ou double
         */
        Pattern pattern = Pattern.compile("\\d*|\\d+\\.+\\d*");
        TextFormatter onlyIntDoubleFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        kmInput.setTextFormatter(onlyIntDoubleFormatter);

        /*
         * Mise en place de la table view des Périodes
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

    private void bind() {
        //medias
        //periods
        nameInput.textProperty().bindBidirectional(getEditedIntervention().getTitleProperty());
        descriptionInput.textProperty().bindBidirectional(getEditedIntervention().getDescriptionProperty());
        kmInput.textProperty().bindBidirectional(getEditedIntervention().getKmProperty());
        billDateInput.valueProperty().bindBidirectional(getEditedIntervention().getBillDateProperty());
        billNumberInput.textProperty().bindBidirectional(getEditedIntervention().getBillNumberProperty());
        paymentDateInput.valueProperty().bindBidirectional(getEditedIntervention().getPaymentDateProperty());
        statusBox.valueProperty().bindBidirectional(getEditedIntervention().getStatusProperty());
        paymenttypeBox.valueProperty().bindBidirectional(getEditedIntervention().getPaymentTypeProperty());
    }

    private void showAlert(ResourceBundle resources, String exceptionProperty, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(resources.getString("exception.erreur"));
        alert.setHeaderText(resources.getString(exceptionProperty));
        alert.setContentText(e.toString());
        alert.show();
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
