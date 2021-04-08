package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.Comparator;

/**
 *
 * @author Eixa6
 */
public class SortInterventionsByClientsAndDates implements Comparator<Intervention> {

    @Override
    public int compare(Intervention intervention1, Intervention intervention2) {

        StringBuilder client1 = new StringBuilder();
        StringBuilder client2 = new StringBuilder();

        // Concaténation du client de la première intervention
        if (intervention1.getClient().getName() != null) {
            client1.append(intervention1.getClient().getName());
        }
        if (intervention1.getClient().getLastname() != null) {
            client1.append(intervention1.getClient().getLastname());
        }
        if (intervention1.getClient().getCompany() != null) {
            client1.append(intervention1.getClient().getCompany());
        }

        // Concaténation du client de la seconde intervention
        if (intervention2.getClient().getName() != null) {
            client2.append(intervention2.getClient().getName());
        }
        if (intervention2.getClient().getLastname() != null) {
            client2.append(intervention2.getClient().getLastname());
        }
        if (intervention2.getClient().getCompany() != null) {
            client2.append(intervention2.getClient().getCompany());
        }

        int value = client1.toString().compareTo(client2.toString());

        if (value == 0) {
            return (intervention1.getPeriods().get(0).getDate().compareTo(intervention2.getPeriods().get(0).getDate()) < 0) ? 1 : -1;
        } else {
            return value;
        }
    }
}
