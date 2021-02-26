package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.ClientPostException;
import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.domain.Client;

/**
 *
 * @author Eixa6
 */
public class AddClient {
    private final ClientsRepository clientsRepository;
    
    public AddClient(ClientsRepository clientsRepository) { this.clientsRepository=clientsRepository;}
    
    public void invoke(Client client) throws ClientPostException {
        clientsRepository.addClient(client);
    }
}
