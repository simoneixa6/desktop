package biz.ei6.interventions.desktop.lib.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Period {

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty();
    private final ObjectProperty<LocalTime> start = new SimpleObjectProperty();
    private final ObjectProperty<LocalTime> end = new SimpleObjectProperty();

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public String getDate() {

        String formattedDate;

        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = date.get().atStartOfDay();
            formattedDate = dateTime.format(formatter);
        } else {
            formattedDate = "";
        }
        return formattedDate;
    }

    public void setDate(String date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDate parsedDate = LocalDate.parse(date, formatter);

            this.date.set(parsedDate);
        } else {
            this.date.set(null);
        }
    }

    public ObjectProperty<LocalTime> getStartProperty() {
        return start;
    }

    public String getStart() {
        String formattedStart;

        if (start != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = start.get().atDate(date.get());
            formattedStart = dateTime.format(formatter);
        } else {
            formattedStart = null;
        }
        return formattedStart;
    }

    public void setStart(String start) {
        if (start != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalTime parsedStart = LocalTime.parse(start, formatter);

            this.start.set(parsedStart);
        } else {
            this.start.set(null);
        }
    }

    public ObjectProperty<LocalTime> getEndProperty() {
        return end;
    }

    public String getEnd() {
        String formattedEnd;

        if (end != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = end.get().atDate(date.get());
            formattedEnd = dateTime.format(formatter);
        } else {
            formattedEnd = null;
        }
        return formattedEnd;
    }

    public void setEnd(String end) {
        if (end != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalTime parsedEnd = LocalTime.parse(end, formatter);

            this.start.set(parsedEnd);
        } else {
            this.start.set(null);
        }
    }
}
