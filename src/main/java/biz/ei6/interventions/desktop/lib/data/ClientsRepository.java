/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.framework.ClientGetException;
import biz.ei6.interventions.desktop.framework.ClientPostException;
import biz.ei6.interventions.desktop.framework.ClientPutException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.util.ArrayList;

/*
 * @author Eixa6
 */
public class ClientsRepository {

    private final ClientsDataSource clientsDataSource;

    public ClientsRepository(ClientsDataSource clientsDataSource) {
        this.clientsDataSource = clientsDataSource;
    }

    public void addClient(Client client) throws ClientPostException {
        clientsDataSource.add(client);
    }

    public ArrayList<Client> getClients() throws ClientGetException {
        return clientsDataSource.readAll();
    }

    public void updateClient(Client client) throws ClientPutException {
        clientsDataSource.update(client);
    }

    public void removeClient(Client client) throws ClientPutException {
        clientsDataSource.remove(client);
    }

}
