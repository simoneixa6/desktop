/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eixa6
 */
public class Period {
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Duration duration;
    private String user_id;
    
    public String getDate() { 
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = date.atStartOfDay();
        String formattedDate = dateTime.format(formatter);
        
        return formattedDate;
    
    }
    
    public void setDate( LocalDate date ) { this.date=date; }
   
    public LocalTime getStart() { return this.start;}
    public void setStart( LocalTime start ) { this.start=start; }
    
    public LocalTime getEnd() { return this.end;}
    public void setEnd( LocalTime end ) { this.end=end; }
   
    public Duration getDuration() { return this.duration;}
    public void setDuration( Duration duration ) { this.duration=duration; }
    
    public String getUserId() { return this.user_id;}
    public void setUserId( String user_id ) { this.user_id=user_id; }
    
    public void period(){
    }
}
