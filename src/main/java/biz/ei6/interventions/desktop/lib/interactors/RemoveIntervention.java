package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.domain.Intervention;

/*
 * @author Eixa6
 */
public class RemoveIntervention {
    private final InterventionsRepository interventionRepository;
    
    public RemoveIntervention(InterventionsRepository interventionsRepository) { this.interventionRepository=interventionsRepository;}
    
    public void invoke(Intervention intervention) {
        interventionRepository.removeIntervention(intervention);
    }
}
