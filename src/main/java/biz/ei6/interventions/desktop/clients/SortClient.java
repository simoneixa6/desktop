package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.lib.domain.Client;
import java.util.Comparator;

/**
 *
 * @author Eixa6
 */
public class SortClient implements Comparator<Client> {

    @Override
    public int compare(Client o1, Client o2) {

        StringBuilder client1 = new StringBuilder();
        StringBuilder client2 = new StringBuilder();

        // Concaténation du premier client
        if (o1.getName() != null) {
            client1.append(o1.getName()).append(" ");
        }
        if (o1.getLastname() != null) {
            client1.append(o1.getLastname()).append(" ");
        }
        if (o1.getCompany() != null) {
            client1.append(o1.getCompany()).append(" ");
        }

        // Concaténation du premier client
        if (o2.getName() != null) {
            client2.append(o2.getName()).append(" ");
        }
        if (o2.getLastname() != null) {
            client2.append(o2.getLastname()).append(" ");
        }
        if (o2.getCompany() != null) {
            client2.append(o2.getCompany()).append(" ");
        }

        int value = client1.toString().compareTo(client2.toString());

        return value;
    }
}
