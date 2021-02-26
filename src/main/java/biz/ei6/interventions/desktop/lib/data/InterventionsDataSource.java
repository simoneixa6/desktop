package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.framework.interventions.InterventionGetException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPostException;
import biz.ei6.interventions.desktop.framework.interventions.InterventionPutException;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 * @author Eixa6
 */
public interface InterventionsDataSource {
    void add(Intervention intervention) throws InterventionPostException;
    ArrayList<Intervention> readAll() throws InterventionGetException;
    void update( Intervention intervention ) throws InterventionPutException;
    void remove( Intervention intervention ) throws InterventionPutException; 
}
