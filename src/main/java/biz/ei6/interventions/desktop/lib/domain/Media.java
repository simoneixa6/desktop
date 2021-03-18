package biz.ei6.interventions.desktop.lib.domain;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Eixa6
 */
public class Media {

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty();
    private final StringProperty url = new SimpleStringProperty();
    private final StringProperty fileName = new SimpleStringProperty();
    private final StringProperty tempName = new SimpleStringProperty();

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
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

    public StringProperty getTempNameProperty() {
        return tempName;
    }

    public String getTempName() {
        return tempName.get();
    }

    public void setTempName(String tempName) {
        this.tempName.set(tempName);
    }
}
