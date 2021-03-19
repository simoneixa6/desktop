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
 */
public class Client {

    private final StringProperty _id = new SimpleStringProperty();
    private final StringProperty civility = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty lastname = new SimpleStringProperty();
    private final StringProperty company = new SimpleStringProperty();
    private final StringProperty companyStatus = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty mail = new SimpleStringProperty();
    private final ListProperty<Site> addresses = new SimpleListProperty();
    private final ObjectProperty<LocalDate> firstVisitDate = new SimpleObjectProperty();
    private final StringProperty how = new SimpleStringProperty();
    private final StringProperty why = new SimpleStringProperty();
    private final BooleanProperty problematic = new SimpleBooleanProperty();
    private final BooleanProperty deleted = new SimpleBooleanProperty();

    {
        // Ajout du tableau d'adresses vide par défault
        ArrayList<Site> sitesList = new ArrayList<>();
        this.setAddresses(sitesList);
        this.setLastname("");
    }

    public StringProperty getIdProperty() {
        return _id;
    }

    /**
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

    public StringProperty getCivilityProperty() {
        return civility;
    }

    /**
     * @return the civility
     */
    public String getCivility() {
        return civility.get();
    }

    /**
     * @param civility the civility to set
     */
    public void setCivility(String civility) {
        this.civility.set(civility);
    }

    public StringProperty getNameProperty() {
        return name;
    }

    /**
     * @return the name of the client
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the civility to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getLastnameProperty() {
        return lastname;
    }

    /**
     * @return the lastname of the client
     */
    public String getLastname() {
        return lastname.get();
    }

    /**
     * @param lastname the civility to set
     */
    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public StringProperty getCompanyProperty() {
        return company;
    }

    /**
     * @return the company of the client
     */
    public String getCompany() {
        return company.get();
    }

    /**
     * @param company the civility to set
     */
    public void setCompany(String company) {
        this.company.set(company);
    }

    public StringProperty getCompanyStatusProperty() {
        return companyStatus;
    }

    /**
     * @return the company status of the client
     */
    public String getCompanyStatus() {
        return companyStatus.get();
    }

    /**
     * @param companyStatus the company status to set
     */
    public void setCompanyStatus(String companyStatus) {
        this.companyStatus.set(companyStatus);
    }

    public StringProperty getPhoneProperty() {
        return phone;
    }

    /**
     * @return the phone of the client
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty getMailProperty() {
        return mail;
    }

    /**
     * @return the mail of the client
     */
    public String getMail() {
        return mail.get();
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public ListProperty<Site> getAddressesProperty() {
        return addresses;
    }

    public List<Site> getAddresses() {
        return addresses.get();
    }

    /**
     * @param site the addresses to set
     */
    public void setAddresses(List<Site> site) {
        this.addresses.set(FXCollections.observableArrayList(site));
    }

    public ObjectProperty<LocalDate> getFirstVisitDateProperty() {
        return firstVisitDate;
    }

    /**
     * @return the firstVisitDate
     */
    public String getFirstVisitDate() {

        String formattedFirstVisitDateTime;

        if (firstVisitDate.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = firstVisitDate.get().atStartOfDay();
            formattedFirstVisitDateTime = dateTime.format(formatter);
        } else {
            formattedFirstVisitDateTime = "";
        }

        return formattedFirstVisitDateTime;
    }

    /**
     * @param firstVisitDateString the billDate to set
     */
    public void setFirstVisitDate(String firstVisitDateString) {

        if (firstVisitDateString != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDate parsedDate = LocalDate.parse(firstVisitDateString, formatter);

            this.firstVisitDate.set(parsedDate);
        } else {
            this.firstVisitDate.set(null);
        }
    }

    public StringProperty getHowProperty() {
        return how;
    }

    /**
     * @return How the client found us
     */
    public String getHow() {
        return how.get();
    }

    /**
     * @param how the how to set
     */
    public void setHow(String how) {
        this.how.set(how);
    }

    public StringProperty getWhyProperty() {
        return why;
    }

    /**
     * @return Why the client came
     */
    public String getWhy() {
        return why.get();
    }

    /**
     * @param why the why to set
     */
    public void setWhy(String why) {
        this.why.set(why);
    }

    public BooleanProperty getProblematicProperty() {
        return problematic;
    }

    /**
     * @return the problematic boolean
     */
    public Boolean getProblematic() {
        return problematic.get();
    }

    /**
     * @param problematic the boolean to set if the client is problematic
     */
    public void setProblematic(Boolean problematic) {
        this.problematic.set(problematic);
    }

    public BooleanProperty getDeletedProperty() {
        return deleted;
    }

    /**
     * @return the deleted boolean
     */
    public Boolean getDeleted() {
        return deleted.get();
    }

    /**
     * @param deleted the boolean to set if the client is deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted.set(deleted);
    }
}
