/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.framework.interventions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodDTO {

    String date;
    String start;
    String end;
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
