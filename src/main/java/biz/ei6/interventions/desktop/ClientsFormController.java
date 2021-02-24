/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Eixa6
 */
public class ClientsFormController {

    Interactors interactors;

    DesktopListener desktopListener;

    /**
     * Intervention éditée par la partie droite de l'interface
     */
    private SimpleObjectProperty<Client> editedClient = new SimpleObjectProperty<Client>();

    ClientsFormController(Client client) {
        setEditedClient(client);
    }

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    public void setDesktopListener(DesktopListener desktopListener) {
        this.desktopListener = desktopListener;
    }

    /**
     * @return the editedIntervention
     */
    public Client getEditedClient() {
        return editedClient.get();
    }

    /**
     * @param editedIntervention the editedIntervention to set
     */
    public void setEditedClient(Client editedClient) {
        this.editedClient.set(editedClient);
    }

    /**
     * @return the editedIntervention
     */
    public SimpleObjectProperty<Client> getEditedClientProperty() {
        return editedClient;
    }

}
