/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Eixa6
 */
public class Period {
    private Date date = new Date();
    private LocalDateTime start;
    private LocalDateTime end;
    private Duration duration;
    private String user_id;
    
    public Date getDate( Date date) { return this.date;}
    public void setDate( Date date ) { this.date=date; }
   
    public LocalDateTime getStart( LocalDateTime start) { return this.start;}
    public void setStart( LocalDateTime start ) { this.start=start; }
    
    public LocalDateTime getEnd() { return this.end;}
    public void setEnd( LocalDateTime end ) { this.end=end; }
   
    public Duration getDuration() { return this.duration;}
    public void setDuration( Duration duration ) { this.duration=duration; }
    
    public String getUserId() { return this.user_id;}
    public void setUserId( String user_id ) { this.user_id=user_id; }
    
    public void period(){
    }
}
