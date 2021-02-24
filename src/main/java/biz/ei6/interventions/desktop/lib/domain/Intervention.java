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
    private final StringProperty client_id = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ListProperty<Period> periods = new SimpleListProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty km = new SimpleStringProperty();
    private final StringProperty billNumber = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> billDate = new SimpleObjectProperty();
    private final StringProperty paymentType = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> paymentDate = new SimpleObjectProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ListProperty<String> medias = new SimpleListProperty();
    private final BooleanProperty deleted = new SimpleBooleanProperty();
    
    
    {

    // Ajout d'une période par défault
//    Period period = new Period();
//    period.setDate(LocalDate.now());
//    List<Period> listperiod = List.of(period) ;
//    
//    this.setPeriod(listperiod);
    
    this.setPeriods(new ArrayList<Period>());
        
    // Ajout du tableau de médias vide par défault
    this.setMedias(new ArrayList<String>());
    
    }
    
    
    /*
     * @return the Id
     */
    public String getId() {
        return _id.get();
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
    public String getClient_id() {
        return client_id.get();
    }

    public StringProperty getClient_idProperty() {
        return client_id;
    }

    /**
     * @param client_id the client_id to set
     */
    public void setClient_id(String client_id) {
        this.client_id.set(client_id);
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
     * @param period the period to set
     */
    public void setPeriods(List<Period> period) {
        this.periods.set(FXCollections.observableArrayList(period));
    }

    /**
     * @return the address
     */
    public StringProperty getAddressProperty() {
        return address;
    }

    public String getAddress() {
        return address.get();
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
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
    public String getBillDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = billDate.get().atStartOfDay();
        String formattedBillDateTime = dateTime.format(formatter);

        return formattedBillDateTime;
    }

    public ObjectProperty<LocalDate> getBillDateProperty() {
        return billDate;
    }

    /**
     * @param billDate the billDate to set
     */
    public void setBillDate(String billDateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDate parsedDate = LocalDate.parse(billDateString, formatter);

        this.billDate.set(parsedDate);
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

    public String getPaymentDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = paymentDate.get().atStartOfDay();
        String formattedPaymentDateTime = dateTime.format(formatter);

        return formattedPaymentDateTime;
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(String paymentDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDate parsedDate = LocalDate.parse(paymentDateString, formatter);

        this.paymentDate.set(parsedDate);
    }

    /**
     * @return the status
     */
    public StringProperty getStatusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status.set(status);
    }

    /**
     * @return the medias
     */
    public ListProperty<String> getMediasProperty() {
        return medias;
    }

    public List<String> getMedias() {
        return medias.get();
    }

    /**
     * @param medias the medias to set
     */
    public void setMedias(List<String> medias) {
        this.medias.set(FXCollections.observableArrayList(medias));
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
