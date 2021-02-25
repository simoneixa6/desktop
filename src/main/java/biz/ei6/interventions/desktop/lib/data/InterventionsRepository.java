package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.framework.InterventionGetException;
import biz.ei6.interventions.desktop.framework.InterventionPostException;
import biz.ei6.interventions.desktop.framework.InterventionPutException;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 * @author Eixa6
 */
public class InterventionsRepository {
    private final  InterventionsDataSource interventionsDataSource;
    
    public InterventionsRepository(InterventionsDataSource interventionsDataSource) { this.interventionsDataSource = interventionsDataSource;}

    public void addIntervention(Intervention intervention) throws InterventionPostException {
        interventionsDataSource.add(intervention);
    }
    
    public void updateIntervention(Intervention intervention) throws InterventionPutException {
        interventionsDataSource.update(intervention);
    }
    
    public ArrayList<Intervention> getInterventions() throws InterventionGetException {
        return interventionsDataSource.readAll();
    }
    
    public void removeIntervention(Intervention intervention) throws InterventionPutException {
        interventionsDataSource.remove(intervention);
    }
}
