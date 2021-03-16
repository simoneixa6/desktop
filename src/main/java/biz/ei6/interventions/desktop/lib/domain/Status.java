package biz.ei6.interventions.desktop.lib.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Eixa6
 */
public class Status {
    
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();

    public Status(String id, String name) {
        this.id.set(id);
        this.name.set(name);
    }

    public StringProperty getIdProperty() {
        return id;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty getNameProperty() {
        return name;
    }

    /**
     * @return the name of the status
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the name of status to set
     */
    public void setName(String name) {
        this.name.set(name);
    }
}
