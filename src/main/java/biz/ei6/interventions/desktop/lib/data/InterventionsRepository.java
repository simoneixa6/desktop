/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 * @author Eixa6
 */
public class InterventionsRepository {
    private  InterventionsDataSource interventionsDataSource;
    
    public InterventionsRepository(InterventionsDataSource interventionsDataSource) { this.interventionsDataSource = interventionsDataSource;}

    public void addIntervention(Intervention intervention) {
        interventionsDataSource.add(intervention);
    }
    
    public void updateIntervention(Intervention intervention){
        interventionsDataSource.update(intervention);
    }
    
    public ArrayList<Intervention> getInterventions() {
        return interventionsDataSource.readAll();
    }
    
    public void removeIntervention(String id) {
        interventionsDataSource.remove(id);
    }
}
