package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 *
 * @author Eixa6
 */
public class GetClients {

    private final ClientsRepository clientsRepository;

    public GetClients(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public ArrayList<Client> invoke() throws ClientGetException {
        return clientsRepository.getClients();
    }
}
