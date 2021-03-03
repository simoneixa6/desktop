/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Period {

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    public String getDate() {

        String formattedDate;

        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = date.atStartOfDay();
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

            this.date = parsedDate;
        } else {
            this.date = null;
        }
    }

    public String getStart() {
        String formattedStart;

        if (start != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = start.atDate(date);
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

            this.start = parsedStart;
        } else {
            this.start = null;
        }
    }

    public String getEnd() {
        String formattedEnd;

        if (end != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = end.atDate(date);
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

            this.start = parsedEnd;
        } else {
            this.start = null;
        }
    }
}
