package biz.ei6.interventions.desktop.framework.clients;

import biz.ei6.interventions.desktop.framework.clients.ClientPutException;
import biz.ei6.interventions.desktop.framework.clients.ClientPostException;
import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.lib.data.ClientsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.util.ArrayList;

/*
 * @author Eixa6
 */
public class WSClientsDataSource implements ClientsDataSource {

    @Override
    public void add(Client client) throws ClientPostException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Client> readAll() throws ClientGetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Client client) throws ClientPutException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Client client) throws ClientPutException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
