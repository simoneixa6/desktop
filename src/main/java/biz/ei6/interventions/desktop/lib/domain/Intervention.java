/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author Eixa6
 *
 */
public class Intervention {

    private final StringProperty _id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty user_id = new SimpleStringProperty();
    private final ObjectProperty<Client> client = new SimpleObjectProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ListProperty<Period> periods = new SimpleListProperty();
    private final ObjectProperty<Site> address = new SimpleObjectProperty();
    private final StringProperty km = new SimpleStringProperty();
    private final StringProperty goKm = new SimpleStringProperty();
    private final StringProperty backKm = new SimpleStringProperty();
    private final StringProperty billNumber = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> billDate = new SimpleObjectProperty();
    private final StringProperty paymentType = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> paymentDate = new SimpleObjectProperty();
    private final ObjectProperty<Status> status = new SimpleObjectProperty();
    private final BooleanProperty deleted = new SimpleBooleanProperty();

    {
        // Ajout d'une période et d'une heure de début par défault
        Period period = new Period();
        period.setDateString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        period.setStartString(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        List<Period> periodlist = List.of(period);
        this.setPeriods(periodlist);
    }

    /*
     * @return the Id
     */
    public String getId() {
        return _id.get();
    }

    /*
     * @return the Id
     */
    public StringProperty getIdProperty() {
        return _id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this._id.set(Id);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title.get();
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * @return the client_id
     */
    public Client getClient() {
        return client.get();
    }

    public ObjectProperty<Client> getClientProperty() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client.set(client);
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id.get();
    }

    public StringProperty getUser_idProperty() {
        return user_id;
    }

    /**
     * @param user_id the client_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id.set(user_id);
    }

    /**
     * @return the description
     */
    public StringProperty getDescriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * @return the period
     */
    public ListProperty<Period> getPeriodsProperty() {
        return periods;
    }

    public List<Period> getPeriods() {
        return periods.get();
    }

    /**
     * @param periods the period to set
     */
    public void setPeriods(List<Period> periods) {
        this.periods.set(FXCollections.observableArrayList(periods));
    }

    /**
     * @return the address
     */
    public ObjectProperty<Site> getAddressProperty() {
        return address;
    }

    public Site getAddress() {
        return address.get();
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Site address) {
        this.address.set(address);
    }

    /**
     * @return the km
     */
    public String getKm() {
        return km.get();
    }

    public StringProperty getKmProperty() {
        return km;
    }

    /**
     * @param km the km to set
     */
    public void setKm(String km) {
        this.km.set(km);
    }

    /**
     * @return the km
     */
    public String getGoKm() {
        return goKm.get();
    }

    public StringProperty getGoKmProperty() {
        return goKm;
    }

    /**
     * @param goKm the km to set
     */
    public void setGoKm(String goKm) {
        this.goKm.set(goKm);
    }

    /**
     * @return the backKm
     */
    public String getBackKm() {
        return backKm.get();
    }

    public StringProperty getBackKmProperty() {
        return backKm;
    }

    /**
     * @param backKm the km to set
     */
    public void setBackKm(String backKm) {
        this.backKm.set(backKm);
    }

    /**
     * @return the billNumber
     */
    public StringProperty getBillNumberProperty() {
        return billNumber;
    }

    public String getBillNumber() {
        return billNumber.get();
    }

    /**
     * @param billNumber the billNumber to set
     */
    public void setBillNumber(String billNumber) {
        this.billNumber.set(billNumber);
    }

    /**
     * @return the billDate
     */
    public String getBillDateString() {

        String formattedBillDateTime;

        if (billDate.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = billDate.get().atStartOfDay();
            formattedBillDateTime = dateTime.format(formatter);
        } else {
            formattedBillDateTime = "";
        }

        return formattedBillDateTime;
    }

    public LocalDate getBillDate() {
        return billDate.get();
    }

    public ObjectProperty<LocalDate> getBillDateProperty() {
        return billDate;
    }

    /**
     * @param billDate the billDate to set
     */
    public void setBillDate(String billDate) {

        if (billDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDate parsedDate = LocalDate.parse(billDate, formatter);

            this.billDate.set(parsedDate);
        } else {
            this.billDate.set(null);
        }
    }

    /**
     * @return the paymentType
     */
    public StringProperty getPaymentTypeProperty() {
        return paymentType;
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    /**
     * @return the paymentDate
     */
    public ObjectProperty<LocalDate> getPaymentDateProperty() {
        return paymentDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate.get();
    }

    public String getPaymentDateString() {

        String formattedPaymentDateTime;

        if (paymentDate.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = paymentDate.get().atStartOfDay();
            formattedPaymentDateTime = dateTime.format(formatter);
        } else {
            formattedPaymentDateTime = "";
        }

        return formattedPaymentDateTime;
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(String paymentDate) {

        if (paymentDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDate parsedDate = LocalDate.parse(paymentDate, formatter);

            this.paymentDate.set(parsedDate);
        } else {
            this.paymentDate.set(null);
        }
    }

    /**
     * @return the status
     */
    public ObjectProperty getStatusProperty() {
        return status;
    }

    public Status getStatus() {
        return status.get();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status.set(status);
    }

    /**
     * @return the deleted
     */
    public BooleanProperty getDeletedProperty() {
        return deleted;
    }

    public Boolean getDeleted() {
        return deleted.get();
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(Boolean deleted) {
        this.deleted.set(deleted);
    }
}
