/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;

/**
 *
 * @author Eixa6
 */
public class RemoveClient {
     private final ClientsRepository clientsRepository;
    
    public RemoveClient(ClientsRepository clientsRepository) { this.clientsRepository=clientsRepository;}
    
    public void invoke(Client client) {
//        clientsRepository.removeClient(client);
    }
}
