package biz.ei6.interventions.desktop.lib.data;


import biz.ei6.interventions.desktop.framework.clients.ClientGetException;
import biz.ei6.interventions.desktop.framework.clients.ClientPostException;
import biz.ei6.interventions.desktop.framework.clients.ClientPutException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.util.ArrayList;

/*
 * @author Eixa6
 */
public interface ClientsDataSource {
    Client add(Client client) throws ClientPostException;
    ArrayList<Client> readAll() throws ClientGetException;
    Client readOne( String client_id ) throws ClientGetException;
    void update( Client client ) throws ClientPutException;
    void remove( Client client ) throws ClientPutException; 
}
