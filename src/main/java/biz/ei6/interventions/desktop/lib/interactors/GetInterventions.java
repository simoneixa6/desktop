package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.interventions.InterventionGetException;
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
    
    public ArrayList<Intervention> invoke() throws InterventionGetException {
       return interventionRepository.getInterventions();
    }
}
