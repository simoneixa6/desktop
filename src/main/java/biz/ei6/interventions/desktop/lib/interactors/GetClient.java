package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.framework.clients.ClientPutException;
import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.domain.Client;

/**
 *
 * @author Eixa6
 */
public class GetClient {
    
    private final ClientsRepository clientsRepository;

    public GetClient(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Client invoke(String client_id) throws ClientGetException {
        return clientsRepository.getClient(client_id);
    }
    
}
