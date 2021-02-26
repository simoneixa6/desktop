package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.InterventionPostException;
import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.domain.Intervention;

/**
 *
 * @author Eixa6
 */
public class AddIntervention {
    private final InterventionsRepository interventionRepository;
    
    public AddIntervention(InterventionsRepository interventionsRepository) { this.interventionRepository=interventionsRepository;}
    
    public void invoke(Intervention intervention) throws InterventionPostException {
        interventionRepository.addIntervention(intervention);
    }
}
