package biz.ei6.interventions.desktop.framework;

import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 *
 * @author Eixa6
 */
public class MemoryDataSource implements InterventionsDataSource {

    ArrayList<Intervention> interventions = new ArrayList<>();
    int interventionId = 0;

    @Override
    public void add(Intervention intervention) {
        interventionId++;
        intervention.setId(String.valueOf(interventionId));
        interventions.add(intervention);
    }

    @Override
    public ArrayList<Intervention> readAll() {
        return interventions;
    }

    @Override
    public void remove(Intervention objIntervention) {
        interventions.removeIf(intervention -> (intervention.getId() == null ? objIntervention.getId() == null : intervention.getId().equals(objIntervention.getId())));
    }

    @Override
    public void update(Intervention intervention) {

        int i = 0;
        int interventionIndex = 0;

        for (Intervention item : interventions) {
            if (item.getId().equals(intervention.getId())) {
                interventionIndex = i;
            }
            i++;
        }

        interventions.get(interventionIndex).setId(intervention.getId());
        interventions.get(interventionIndex).setTitle(intervention.getTitle());
        interventions.get(interventionIndex).setDescription(intervention.getDescription());
        interventions.get(interventionIndex).setBillDate(intervention.getBillDate());
        interventions.get(interventionIndex).setPaymentDate(intervention.getPaymentDate());
        interventions.get(interventionIndex).setStatus(intervention.getStatus());
    }

}
