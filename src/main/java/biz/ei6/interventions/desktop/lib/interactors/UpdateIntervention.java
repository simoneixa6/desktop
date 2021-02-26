package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.InterventionPutException;
import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.domain.Intervention;

/**
 *
 * @author Eixa6
 */
public class UpdateIntervention {
    private final InterventionsRepository interventionRepository;
    
    public UpdateIntervention(InterventionsRepository interventionsRepository) { this.interventionRepository=interventionsRepository;}
    
    public void invoke(Intervention intervention) throws InterventionPutException {
        interventionRepository.updateIntervention(intervention);
    }
}
