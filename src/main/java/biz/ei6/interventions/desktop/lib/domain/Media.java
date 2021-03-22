package biz.ei6.interventions.desktop.lib.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Eixa6
 */
public class Media {

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty intervention_id = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> date = new SimpleObjectProperty();
    private final StringProperty url = new SimpleStringProperty();
    private final StringProperty fileName = new SimpleStringProperty();
    private final BooleanProperty deleted = new SimpleBooleanProperty();

    public StringProperty getIdProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty getInterventionIdProperty() {
        return intervention_id;
    }

    public String getInterventionId() {
        return intervention_id.get();
    }

    public void setInterventionId(String intervention_id) {
        this.intervention_id.set(intervention_id);
    }

    public ObjectProperty<LocalDateTime> getDateProperty() {
        return date;
    }

    public LocalDateTime getDate() {
        return date.get();
    }

    public String getDateString() {

        String dateString;

        if (date.getValue() != null) {
            LocalDateTime dateTime = date.get();
            dateString = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            dateString = "";
        }

        return dateString;
    }

    public void setDate(String date) {
        if (date != null) {       
            LocalDateTime parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            this.date.set(parsedDate);
        } else {
            this.date.set(null);
        }
    }

    public StringProperty getUrlProperty() {
        return url;
    }

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public StringProperty getFileNameProperty() {
        return fileName;
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public BooleanProperty getDeletedProperty() {
        return deleted;
    }

    public Boolean getDeleted() {
        return deleted.get();
    }

    public void setDeleted(Boolean deleted) {
        this.deleted.set(deleted);
    }

}
