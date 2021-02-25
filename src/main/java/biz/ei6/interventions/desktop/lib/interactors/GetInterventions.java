/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 *
 * @author Eixa6
 */
public class GetInterventions {
    private final InterventionsRepository interventionRepository;
    
    public GetInterventions(InterventionsRepository interventionsRepository) { this.interventionRepository=interventionsRepository;}
    
    public ArrayList<Intervention> invoke() {
       return interventionRepository.getInterventions();
    }
}
