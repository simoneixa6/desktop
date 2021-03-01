package biz.ei6.interventions.desktop.lib.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Eixa6
 */
public class Site {

    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty zipCode = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();

    public StringProperty getAddressProperty() {
        return address;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty getZipCodeProperty() {
        return zipCode;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode.get();
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }

    public StringProperty getCityProperty() {
        return city;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city.get();
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city.set(city);
    }
}
