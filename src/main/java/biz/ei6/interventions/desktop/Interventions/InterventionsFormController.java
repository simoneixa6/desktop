package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.clients.ClientsForm;
import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPostException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPutException;
import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.framework.medias.MediaPutException;
import biz.ei6.interventions.desktop.lib.domain.Site;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Media;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;
import biz.ei6.interventions.desktop.lib.domain.Status;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    ListView<Media> mediasListView;
    @FXML
    TextField kmInput;
    @FXML
    TextField goKmInput;
    @FXML
    TextField backKmInput;
    @FXML
    TextField multiplePeriodBegin;
    @FXML
    TextField multiplePeriodEnd;
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
    ComboBox<Status> statusBox;
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
    Button multiplePeriodBtn;
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

    Boolean isNewIntervention = false;

    Status status1;
    Status status2;
    Status status3;
    Status status4;

    Stage clientStage;

    ResourceBundle resources;

    InterventionsFormController(Intervention intervention) {
        setEditedIntervention(intervention);
        if (intervention.getId() == null) {
            isNewIntervention = true;
        }
    }

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    public void setDesktopListener(DesktopListener desktopListener) {
        this.desktopListener = desktopListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Fichier ressources pour les chaines de caractères
        this.resources = resources;

        // Création des statuts d'intervention
        this.status1 = new Status("1", resources.getString("status.ouverte"));
        this.status2 = new Status("2", resources.getString("status.terminee"));
        this.status3 = new Status("3", resources.getString("status.facturee"));
        this.status4 = new Status("4", resources.getString("status.reglee"));

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

        // Remplissage des choiceboxs
        statusBox.getItems().addAll(status1, status2, status3, status4);

        statusBox.setCellFactory(new StatusCellFactory());

        paymenttypeBox.getItems().addAll(resources.getString("paiement.cheque"), resources.getString("paiement.cb"),
                resources.getString("paiement.espece"), resources.getString("paiement.virement"));

        statusBox.setConverter(new StringConverter<Status>() {
            @Override
            public String toString(Status status) {
                if (status != null) {
                    return status.getName();
                } else {
                    return resources.getString("exception.erreur");
                }
            }

            @Override
            public Status fromString(String arg0) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });

        // Mise en place de la cellFactory de la combobox des clients
        clientBox.setCellFactory(new ClientCellFactory());
        clientBox.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                if (client != null && client.getId() != null) {
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
                            clientString.append(" (").append(client.getCompany()).append(")");
                        } else {
                            clientString.append(client.getCompany());
                        }
                    }
                    return clientString.toString();
                } else {
                    return resources.getString("info.choisir.client");
                }
            }

            @Override
            public Client fromString(String arg0) {
                throw new UnsupportedOperationException("Not supported.");
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
            clientStage.initModality(Modality.APPLICATION_MODAL);
            clientStage.showAndWait();
        });

        /**
         * Action sur le clic du bouton "Modificer le client"
         */
        updateClientBtn.setOnAction((ActionEvent event) -> {
            // Si un client est selectionné
            if (clientBox.getValue().getId() != null) {
                ClientsForm clientsForm = new ClientsForm(interactors, selectedClient, InterventionsFormController.this, resources);
                clientStage = new Stage();
                clientStage.getIcons().add(new Image(whiteIcon));
                clientStage.setScene(new Scene(clientsForm, 650, 450));
                clientStage.initModality(Modality.APPLICATION_MODAL);
                clientStage.showAndWait();
            }
        });

        // Mise en place de la cellFactory sur la combobox des adresses
        addressBox.setCellFactory(new SiteCellFactory());
        addressBox.setConverter(new StringConverter<Site>() {
            @Override
            public String toString(Site site) {
                if (site != null) {

                    StringBuilder address = new StringBuilder();

                    if (site.getAddress() != null) {
                        address.append(site.getAddress());
                    }
                    if (site.getZipCode() != null) {
                        address.append(", ").append(site.getZipCode());
                    }
                    if (site.getCity() != null) {
                        address.append(" ").append(site.getCity());
                    }

                    return address.toString();
                } else {
                    return "";
                }
            }

            @Override
            public Site fromString(String arg0) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });

        // Initialisation de la combobox des adresses
        var addresses = FXCollections.observableArrayList(getSelectedClient().getAddresses());
        addressBox.setItems(addresses);

        /*
         * Listener sur la selection d'un client dans la combobox des clients
         */
        clientBox.valueProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue ov, Client oldClient, Client newClient) {
                if (newClient != null) {
                    getEditedIntervention().setClient(newClient);

                    // Chargement des addresses dans la combobox des adresses
                    var addresses = FXCollections.observableArrayList(newClient.getAddresses());
                    addressBox.setItems(addresses);
                }
            }
        });

        /*
         * Initialisation des champs si c'est une nouvelle intervention ( Si une intervention n'a pas d'id, c'est que c'est une nouvelle intervention )
         */
        if (isNewIntervention) {
            // Valeurs pas défault pour une nouvelle intervention
            getEditedIntervention().setStatus(status1);
            paymenttypeBox.setValue(resources.getString("paiement.cheque"));
            registerBtn.setText(resources.getString("enregistrer"));
            deleteBtn.setDisable(true);
            titleLbl.setText(resources.getString("creer.une.intervention"));
            addMediaBtn.setDisable(true);
            deleteMediaBtn.setDisable(true);

            //TEMPORAIRE
            getEditedIntervention().setUser_id("Slad");

        } else {
            updateMediasListView();
            registerBtn.setText(resources.getString("modifier"));
            deleteBtn.setDisable(false);
        }

        /**
         * Action sur le clic du bouton "Enregistrer" / "Modifier"
         */
        registerBtn.setOnAction((ActionEvent actionEvent) -> {

            // Si tous les champs obligatoires sont remplies
            if (validate() == true) {
                // Si l'intervention est nouvelle
                if (isNewIntervention) {
                    try {
                        interactors.addIntervention.invoke(getEditedIntervention());
                    } catch (InterventionPostException e) {
                        showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.ajoutIntervention", e.toString());
                    }
                    // Si l'intervention existe déjà
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

        multiplePeriodBtn.setOnAction((ActionEvent actionEvent) -> {
            Period period = new Period();
            period.setDateString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
            period.setStartString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
            periodTableView.getItems().add(period);
        });

        // Mise en place de la cell factory des médias
        mediasListView.setCellFactory(new MediaCellFactory());

        mediasListView.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2 && mediasListView.getSelectionModel().getSelectedItem() != null) {

                MediaFile mediaFile = null;

                try {
                    mediaFile = interactors.getMediaFile.invoke(mediasListView.getSelectionModel().getSelectedItem().getId());
                } catch (MediaGetException e) {
                    showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.recuperationMedia", e.toString());
                }

                if (mediaFile != null) {

                    File filePath = new File(System.getProperty("user.home") + "/Downloads/"+ mediaFile.getFileName());

                    if (filePath != null) {
                        byte[] data = Base64.getDecoder().decode(mediaFile.fileData);
                        try ( OutputStream stream = new FileOutputStream(filePath)) {
                            stream.write(data);
                        } catch (Exception e) {
                            showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.creationFichier", e.toString());
                        }
                        
                        try {
                            Desktop.getDesktop().open(filePath);
                        } catch (IOException e) {
                           showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.ouvertureFichier", e.toString());
                        }
                    }
                }
            }
        });

        addMediaBtn.setOnAction((ActionEvent actionEvent) -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selectionnez un fichier à joindre à l'intervention");

            File selectedFile = fileChooser.showOpenDialog(addMediaBtn.getScene().getWindow());

            int BUFFER_SIZE = 3 * 1024;

            if (selectedFile != null) {
                try ( FileInputStream input = new FileInputStream(selectedFile);  BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);) {

                    Base64.Encoder encoder = Base64.getEncoder();
                    StringBuilder result = new StringBuilder();
                    byte[] chunk = new byte[BUFFER_SIZE];
                    int len = 0;
                    while ((len = in.read(chunk)) == BUFFER_SIZE) {
                        result.append(encoder.encodeToString(chunk));
                    }
                    if (len > 0) {
                        chunk = Arrays.copyOf(chunk, len);
                        result.append(encoder.encodeToString(chunk));
                    }

                    // Création de l'objet média
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setIntervention_id(getEditedIntervention().getId());
                    mediaFile.setFileName(selectedFile.getName());
                    mediaFile.setFileData(result.toString());

                    interactors.addMediaFile.invoke(mediaFile);

                    updateMediasListView();

                } catch (Exception e) {
                    showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.ajoutMedia", e.toString());
                }
            }
        });

        deleteMediaBtn.setOnAction((ActionEvent actionEvent) -> {
            if (mediasListView.getSelectionModel().getSelectedItem() != null) {
                try {
                    interactors.removeMedia.invoke(mediasListView.getSelectionModel().getSelectedItem());
                    updateMediasListView();
                } catch (MediaPutException e) {
                    showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.suppressionMedia", e.toString());
                }
            }
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

            if (newVal != null && !"".equals(newVal)) {
                a = Double.parseDouble(newVal);
            } else {
                a = 0;
            }

            if (backKmInput.getText() == null || "".equals(backKmInput.getText())) {
                b = 0;
            } else {
                b = Double.parseDouble(backKmInput.getText());
            }
            kmInput.setText(String.valueOf(a + b));
        });

        backKmInput.textProperty().addListener((observable, oldVal, newVal) -> {
            double a;
            double b;

            if (newVal != null && !"".equals(newVal)) {
                a = Double.parseDouble(newVal);
            } else {
                a = 0;
            }

            if (goKmInput.getText() == null || "".equals(goKmInput.getText())) {
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
                            Duration duration = Duration.between(start, end);
                            LocalTime time = LocalTime.MIN.plus(duration);
                            return time.format(DateTimeFormatter.ofPattern("HH!mm"));
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

        // Binding à l'initialisation
        bind();
    }

    /**
     * Mise à jour de la combobox des clients
     */
    private void updateClientsComboBox() throws ClientGetException {
        // Appelé 3 fois FBR lors de l'ouverture d'une intervention ?
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
        nameInput.textProperty().bindBidirectional(getEditedIntervention().getTitleProperty());
        userBox.valueProperty().bindBidirectional(getEditedIntervention().getUser_idProperty());
        descriptionInput.textProperty().bindBidirectional(getEditedIntervention().getDescriptionProperty());
        kmInput.textProperty().bindBidirectional(getEditedIntervention().getKmProperty());
        goKmInput.textProperty().bindBidirectional(getEditedIntervention().getGoKmProperty());
        backKmInput.textProperty().bindBidirectional(getEditedIntervention().getBackKmProperty());
        billDateInput.valueProperty().bindBidirectional(getEditedIntervention().getBillDateProperty());
        billNumberInput.textProperty().bindBidirectional(getEditedIntervention().getBillNumberProperty());
        paymentDateInput.valueProperty().bindBidirectional(getEditedIntervention().getPaymentDateProperty());
        paymenttypeBox.valueProperty().bindBidirectional(getEditedIntervention().getPaymentTypeProperty());
        clientBox.valueProperty().bindBidirectional(getSelectedClientProperty());
        addressBox.valueProperty().bindBidirectional(getEditedIntervention().getAddressProperty());
        statusBox.valueProperty().bindBidirectional(getEditedIntervention().getStatusProperty());
    }

    /**
     * Methode permettant de valider si les champs obligatoires sont bien
     * remplies
     *
     * @return
     */
    public boolean validate() {

        StringBuilder errors = new StringBuilder();

        // Vérifie que les champs obligatoires soient bien remplies
        if (nameInput.getText() == null || "".equals(nameInput.getText())) {
            errors.append(resources.getString("warning.titreIntervention"));
        }

        if (periodTableView.getItems().size() < 1) {
            errors.append(resources.getString("warning.periode"));
        }

        if (clientBox.getSelectionModel().getSelectedItem() == null || clientBox.getSelectionModel().getSelectedItem().getId() == null) {
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

        // Mise à jour de la combobox des clients
        try {
            updateClientsComboBox();
        } catch (ClientGetException e) {
            showAlert(resources, AlertType.ERROR, "exception.erreur", "exception.recuperationClients", e.toString());
        }

        // Si un client est renvoyé, on le sélectionne dans la combobox
        if (client != null) {
            var clientsList = clientBox.getItems();
            // A revoir lors de la suppression d'un client
            setSelectedClient(clientsList.stream().filter(c -> c.getId().equals(client.getId())).findFirst().get());
        } // Si null est renvoyé, le client a été supprimé, on place la valeur de la combobox a null
        else {
            clientBox.setValue(null);
        }
    }

    private void updateMediasListView() {
        var medias = FXCollections.observableArrayList(getMedias());
        mediasListView.setItems(medias);
    }

    public ArrayList<Media> getMedias() {
        try {
            return interactors.getInterventionMedias.invoke(getEditedIntervention().getId());
        } catch (MediaGetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("erreur recup medias");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        // Si erreur lors de la récupération, on renvoie une liste d'interventions vide
        return new ArrayList<>();
    }

}
