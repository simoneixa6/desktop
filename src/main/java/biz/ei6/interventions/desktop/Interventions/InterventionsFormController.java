package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.clients.ClientsForm;
import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPostException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPutException;
import biz.ei6.interventions.desktop.lib.domain.Site;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/*
 * @author Eixa6
 */
public final class InterventionsFormController implements Initializable, DesktopListener {

    @FXML
    Label titleLbl;

    @FXML
    TextField nameInput;

    @FXML
    TextArea descriptionInput;

    @FXML
    ChoiceBox userBox;

    @FXML
    ListView<String> mediasListView;

    @FXML
    TextField kmInput;

    @FXML
    TextField goKmInput;

    @FXML
    TextField backKmInput;

    @FXML
    DatePicker billDateInput;

    @FXML
    TextField billNumberInput;

    @FXML
    DatePicker paymentDateInput;

    @FXML
    ComboBox<Client> clientBox;

    @FXML
    ComboBox<Site> addressBox;

    @FXML
    ChoiceBox<String> statusBox;

    @FXML
    ChoiceBox<String> paymenttypeBox;

    @FXML
    Button deleteBtn;

    @FXML
    Button registerBtn;

    // TableView des périodes
    @FXML
    TableView<Period> periodTableView;
    @FXML
    TableColumn<Period, LocalDate> dateCol;
    @FXML
    TableColumn<Period, LocalTime> startCol;
    @FXML
    TableColumn<Period, LocalTime> endCol;
    @FXML
    TableColumn<Period, String> durationCol;
    @FXML
    Button addPeriodBtn;
    @FXML
    Button deletePeriodBtn;
    @FXML
    Button addMediaBtn;
    @FXML
    Button deleteMediaBtn;
    @FXML
    Button createClientBtn;
    @FXML
    Button updateClientBtn;

    Label linkedInterventionsLbl;
    ListView<Intervention> linkedInterventionsListView;

    Interactors interactors;

    DesktopListener desktopListener;

    /**
     * Intervention éditée par la partie droite de l'interface
     */
    private final SimpleObjectProperty<Intervention> editedIntervention = new SimpleObjectProperty<Intervention>();

    private final SimpleObjectProperty<Client> selectedClient = new SimpleObjectProperty<Client>();
    StringProperty address = new SimpleStringProperty();
    StringProperty zipCode = new SimpleStringProperty();
    StringProperty city = new SimpleStringProperty();
    StringProperty selectedAddress = new SimpleStringProperty();

    Stage clientStage;

    ResourceBundle resources;

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

        this.resources = resources;

        /**
         * On récupère le client lié à l'intervention si il y en a un, et on le
         * place en tant que selectedClient
         */
        if (getEditedIntervention().getClient() != null) {
            setSelectedClient(getEditedIntervention().getClient());
        } else {
            setSelectedClient(new Client());
        }

        // TEMPORAIRE
        userBox.getItems().addAll("Slad", "Fabien");

        //Remplissage des choiceboxs
        statusBox.getItems().addAll(resources.getString("status.ouverte"), resources.getString("status.terminee"), resources.getString("status.facturee"), resources.getString("status.reglee"));
        paymenttypeBox.getItems().addAll(resources.getString("paiement.cheque"), resources.getString("paiement.cb"), resources.getString("paiement.espece"));

        // Mise en place de la cellFactory de la combobox des clients
        clientBox.setCellFactory(new ClientCellFactory());
        clientBox.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                if (client != null) {
                    StringBuilder clientString = new StringBuilder();
                    // Si il a un prénom
                    if (client.getName() != null) {
                        clientString.append(client.getName()).append(" ");
                    }
                    // Si il a un nom
                    if (client.getLastname() != null) {
                        clientString.append(client.getLastname());
                    }
                    // Si il a une entreprise
                    if (client.getCompany() != null) {
                        if (client.getName() != null || client.getLastname() != null) {
                            clientString.append("(" + client.getCompany() + ")");
                        } else {
                            clientString.append(client.getCompany());
                        }
                    }
                    return clientString.toString();
                } else {
                    return "Erreur";
                }
            }

            @Override
            public Client fromString(String arg0) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        try {
            // Remplissage de la combobox des clients
            updateClientsComboBox();
        } catch (ClientGetException e) {
            showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.recuperationClients", e.toString());
        }

        // Image utilisé pour les icônes des fenêtres "Créer un client" et "Modifier un client"
        InputStream whiteIcon = getClass().getResourceAsStream("white.png");

        /**
         * Action sur le clic du bouton "Créer un client"
         */
        createClientBtn.setOnAction((ActionEvent event) -> {
            ClientsForm clientsForm = new ClientsForm(interactors, new Client(), InterventionsFormController.this, resources);
            clientStage = new Stage();
            clientStage.getIcons().add(new Image(whiteIcon));
            clientStage.setScene(new Scene(clientsForm, 650, 450));
            clientStage.show();
        });

        /**
         * Action sur le clic du bouton "Modificer le client"
         */
        updateClientBtn.setOnAction((ActionEvent event) -> {
            // Si un client est selectionné
            if (clientBox.getValue().getId() != null) {
                ClientsForm clientsForm = new ClientsForm(interactors, getSelectedClient(), InterventionsFormController.this, resources);
                clientStage = new Stage();
                clientStage.getIcons().add(new Image(whiteIcon));
                clientStage.setScene(new Scene(clientsForm, 650, 450));
                clientStage.show();
            }
        });

        // Mise en place de la cellFactory sur la combobox des adresses
        addressBox.setCellFactory(new SiteCellFactory());
        addressBox.setConverter(new StringConverter<Site>() {
            @Override
            public String toString(Site site) {
                if (site != null) {
                    return site.getAddress() + ", " + site.getZipCode() + " " + site.getCity();
                } else {
                    return "";
                }
            }

            @Override
            public Site fromString(String arg0) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        // Binding à l'initialisation
        bind();

        /*
         * Listener sur la selection d'un client dans la combobox des clients
         */
        clientBox.valueProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue ov, Client oldClient, Client newClient) {
                if (newClient != null) {
                    getEditedIntervention().setClient(newClient);
                    addressBox.itemsProperty().bind(getSelectedClient().getAddressesProperty());
                }
            }
        });

        /*
         * Initialisation des champs si c'est une nouvelle intervention ( Si une intervention n'a pas d'id, c'est que c'est une nouvelle intervention )
         */
        if (getEditedIntervention().getId() == null) {
            // Valeurs pas défault pour une nouvelle intervention
            statusBox.setValue(resources.getString("status.ouverte"));
            paymenttypeBox.setValue(resources.getString("paiement.cheque"));
            registerBtn.setText(resources.getString("enregistrer"));
            deleteBtn.setDisable(true);
            titleLbl.setText(resources.getString("creer.une.intervention"));

            //TEMPORAIRE
            userBox.setValue("Slad");

        } else {
            registerBtn.setText(resources.getString("modifier"));
            deleteBtn.setDisable(false);
        }

        /**
         * Action sur le clic du bouton "Enregistrer" / "Modifier"
         */
        registerBtn.setOnAction((ActionEvent actionEvent) -> {

            // Si tous les champs obligatoires sont remplies
            if (validate(resources) == true) {
                // Si l'intervention ne possède pas d'id, elle est nouvelle, on enregistre
                if (getEditedIntervention().getId() == null) {
                    try {
                        interactors.addIntervention.invoke(getEditedIntervention());
                    } catch (InterventionPostException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.ajoutIntervention", e.toString());
                    }
                    // Si elle possède un ID, elle existe, on veut donc la modifier
                } else {
                    try {
                        interactors.updateIntervention.invoke(getEditedIntervention());
                    } catch (InterventionPutException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.modificationIntervention", e.toString());
                    }
                }
                desktopListener.close();
            }
        });

        /**
         * Action sur le clic du bouton "Supprimer"
         */
        deleteBtn.setOnAction((ActionEvent actionEvent) -> {
            try {
                interactors.removeIntervention.invoke(getEditedIntervention());
            } catch (InterventionPutException e) {
                showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.suppressionIntervention", e.toString());
            }
            desktopListener.close();
        });

        /**
         * Action sur le clic du bouton "Ajouter" pour la tableview des périodes
         */
        addPeriodBtn.setOnAction((ActionEvent actionEvent) -> {
            Period period = new Period();
            period.setDateString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
            period.setStartString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
            periodTableView.getItems().add(period);
        });

        /**
         * Action sur le clic du bouton "Supprimer" pour la tableview des
         * périodes
         */
        deletePeriodBtn.setOnAction((ActionEvent actionEvent) -> {
            ObservableList<Period> periods;
            periods = periodTableView.getItems();

            // Renvoie le site selectionné
            Period selectedPeriod = periodTableView.getSelectionModel().getSelectedItem();

            periods.remove(selectedPeriod);

        });

        /*
         * Text formatter sur les champ des kms pour accepter que des entiers ou double
         */
        Pattern pattern = Pattern.compile("\\d*|\\d+\\.+\\d*");
        TextFormatter onlyIntDoubleFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        TextFormatter onlyIntDoubleFormatter1 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        TextFormatter onlyIntDoubleFormatter2 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        kmInput.setTextFormatter(onlyIntDoubleFormatter);
        goKmInput.setTextFormatter(onlyIntDoubleFormatter1);
        backKmInput.setTextFormatter(onlyIntDoubleFormatter2);

        goKmInput.textProperty().addListener((observable, oldVal, newVal) -> {
            double a;
            double b;

            if (newVal != null) {
                a = Double.parseDouble(newVal);
            } else {
                a = 0;
            }

            if (backKmInput.getText() == null) {
                b = 0;
            } else {
                b = Double.parseDouble(backKmInput.getText());
            }
            kmInput.setText(String.valueOf(a + b));
        });

        backKmInput.textProperty().addListener((observable, oldVal, newVal) -> {
            double a;
            double b;

            if (newVal != null) {
                a = Double.parseDouble(newVal);
            } else {
                a = 0;
            }

            if (backKmInput.getText() == null) {
                b = 0;
            } else {
                b = Double.parseDouble(goKmInput.getText());
            }
            kmInput.setText(String.valueOf(a + b));
        });

        /*
         * Mise en place de la table view des Périodes
         */
        dateCol.setCellValueFactory(new PropertyValueFactory<Period, LocalDate>("date"));
        startCol.setCellValueFactory(new PropertyValueFactory<Period, LocalTime>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Period, LocalTime>("end"));

        // Cell value factory permettant de calculer la duréé entre l'heure de début et l'heure de fin
        durationCol.setCellValueFactory(cellData -> {
            Period period = cellData.getValue();
            return Bindings.createStringBinding(
                    () -> {
                        try {
                            LocalTime start = period.getStart();
                            LocalTime end = period.getEnd();
                            int time = (int) start.until(end, ChronoUnit.MINUTES) + 1;
                            int hours = time / 60;
                            int minutes = time % 60;

                            StringBuilder hour = new StringBuilder();

                            // Si le résultat est plus petit que 10, on ajoute un 0 devant pour respecter le format 00:00
                            if (hours < 10) {
                                hour.append("0").append(hours).append(":");
                            } else {
                                hour.append(hour).append(":");
                            }

                            // Si le résultat est plus petit que 10, on ajoute un 0 devant pour respecter le format 00:00
                            if (minutes < 10) {
                                hour.append("0").append(minutes);
                            } else {
                                hour.append(minutes);
                            }

                            return hour.toString();
                        } catch (Exception e) {
                            return "";
                        }
                    },
                    period.getStartProperty(),
                    period.getEndProperty()
            );
        });

        periodTableView.setEditable(true);
        dateCol.setCellFactory(column -> new DateEditableCell(column));
        startCol.setCellFactory(column -> new TimeEditableCell(column));
        endCol.setCellFactory(column -> new TimeEditableCell(column));

    }

    /**
     * Mise à jour de la combobox des clients
     */
    private void updateClientsComboBox() throws ClientGetException {
        var clients = FXCollections.observableArrayList(interactors.getClients.invoke());
        clientBox.setItems(clients);
    }

    /**
     * Event appellé lors de la modification d'une cell date
     *
     * @param editedCell
     */
    public void changeDateCellEvent(CellEditEvent editedCell) {
        Period selectedPeriod = periodTableView.getSelectionModel().getSelectedItem();
        selectedPeriod.setDate((LocalDate) editedCell.getNewValue());
    }

    /**
     * Event appellé lors de la modification d'une cell heure de début
     *
     * @param editedCell
     */
    public void changeStartCellEvent(CellEditEvent editedCell) {
        Period selectedPeriod = periodTableView.getSelectionModel().getSelectedItem();
        selectedPeriod.setStart((LocalTime) editedCell.getNewValue());
    }

    /**
     * Event appellé lors de la modification d'une cell Heure de gin
     *
     * @param editedCell
     */
    public void changeEndCellEvent(CellEditEvent editedCell) {
        Period selectedPeriod = periodTableView.getSelectionModel().getSelectedItem();
        selectedPeriod.setEnd((LocalTime) editedCell.getNewValue());
    }

    /**
     * Mérhode permettant de bind les champs du formulaire et les attributs de
     * notre objet Intervention
     */
    private void bind() {
        periodTableView.itemsProperty().bindBidirectional(getEditedIntervention().getPeriodsProperty());
        mediasListView.itemsProperty().bindBidirectional(getEditedIntervention().getMediasProperty());
        nameInput.textProperty().bindBidirectional(getEditedIntervention().getTitleProperty());
        userBox.valueProperty().bindBidirectional(getEditedIntervention().getUser_idProperty());
        descriptionInput.textProperty().bindBidirectional(getEditedIntervention().getDescriptionProperty());
        kmInput.textProperty().bindBidirectional(getEditedIntervention().getKmProperty());
        goKmInput.textProperty().bindBidirectional(getEditedIntervention().getGoKmProperty());
        backKmInput.textProperty().bindBidirectional(getEditedIntervention().getBackKmProperty());
        billDateInput.valueProperty().bindBidirectional(getEditedIntervention().getBillDateProperty());
        billNumberInput.textProperty().bindBidirectional(getEditedIntervention().getBillNumberProperty());
        paymentDateInput.valueProperty().bindBidirectional(getEditedIntervention().getPaymentDateProperty());
        statusBox.valueProperty().bindBidirectional(getEditedIntervention().getStatusProperty());
        paymenttypeBox.valueProperty().bindBidirectional(getEditedIntervention().getPaymentTypeProperty());
        clientBox.valueProperty().bindBidirectional(getSelectedClientProperty());
        addressBox.valueProperty().bindBidirectional(getEditedIntervention().getAddressProperty());
        addressBox.itemsProperty().bind(getSelectedClient().getAddressesProperty());
    }

    /**
     * Methode permettant de valider si les champs obligatoires sont bien
     * remplies
     *
     * @param resources
     * @return
     */
    public boolean validate(ResourceBundle resources) {

        StringBuilder errors = new StringBuilder();

        // Vérifie que les champs obligatoires soient bien remplies
        if (nameInput.getText() == null || "".equals(nameInput.getText())) {
            errors.append(resources.getString("warning.titreIntervention"));
        }

        if (periodTableView.getItems().size() < 1) {
            errors.append(resources.getString("warning.periode"));
        }

        if (clientBox.getSelectionModel().getSelectedItem().getId() == null) {
            errors.append(resources.getString("warning.choisirClient"));
        }

        // Si une information est manquante, montre un message d'erreur et renvoie false
        if (errors.length() > 0) {
            showAlert(resources, AlertType.WARNING, "warning.attention", "warning.champsObligatoires", errors.toString());
            return false;
        }

        // Pas d'erreur, tous les champs sont remplies correctement
        return true;

    }

    /**
     * Méthode permettant de faire apparaitre une alerte
     *
     * @param resources
     * @param alertType
     * @param titleProperty
     * @param exceptionProperty
     * @param contentText
     */
    private void showAlert(ResourceBundle resources, AlertType alertType, String titleProperty, String exceptionProperty, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(resources.getString(titleProperty));
        alert.setHeaderText(resources.getString(exceptionProperty));
        alert.setContentText(contentText);
        alert.showAndWait();
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

    /**
     * @return the selectedClient
     */
    public Client getSelectedClient() {
        return selectedClient.get();
    }

    /**
     * @param client the client to set
     */
    public void setSelectedClient(Client client) {
        this.selectedClient.set(client);
    }

    /**
     * @return the selectedClient
     */
    public SimpleObjectProperty<Client> getSelectedClientProperty() {
        return selectedClient;
    }

    @Override
    public void close() {
        clientStage.close();
    }

    @Override
    public void returnClient(Client client) {

        try {
            updateClientsComboBox();
        } catch (ClientGetException e) {
            showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.recuperationClients", e.toString());
        }

        // On doit mettre à jour la valeur de la clientBox a null avant de reselectionné un client sinon l'affichage de la 
        // combobox ne se met pas à jour ( même si la valeur du client est bien présente, c'est juste l'affichage qui ne se fait pas )
        clientBox.setValue(null);

        // On assigne le nouveau client en client selectionné
        setSelectedClient(client);
    }
}
