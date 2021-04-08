package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Eixa6
 */
public class SortInterventionsByDates implements Comparator<Intervention> {


    @Override
    public int compare(Intervention intervention1, Intervention intervention2) {
        List<Period> intervention1Periods = intervention1.getPeriods();
        List<Period> intervention2Periods = intervention2.getPeriods();
        
        int value = intervention1Periods.get(0).getDate().compareTo(intervention2Periods.get(0).getDate());

        return -value;
    }
    
}
