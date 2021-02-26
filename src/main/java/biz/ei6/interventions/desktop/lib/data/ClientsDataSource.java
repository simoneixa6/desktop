package biz.ei6.interventions.desktop.lib.data;


import biz.ei6.interventions.desktop.framework.ClientGetException;
import biz.ei6.interventions.desktop.framework.ClientPostException;
import biz.ei6.interventions.desktop.framework.ClientPutException;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.util.ArrayList;

/*
 * @author Eixa6
 */
public interface ClientsDataSource {
    void add(Client client) throws ClientPostException;
    ArrayList<Client> readAll() throws ClientGetException;
    void update( Client client ) throws ClientPutException;
    void remove( Client client ) throws ClientPutException; 
}
