package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.clients.ClientPutException;
import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.domain.Client;

/**
 *
 * @author Eixa6
 */
public class RemoveClient {

    private final ClientsRepository clientsRepository;

    public RemoveClient(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public void invoke(Client client) throws ClientPutException {
        clientsRepository.removeClient(client);
    }
}
