/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.lib.domain.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author Eixa6
 */
class ClientsForm extends AnchorPane {

    public ClientsForm(Interactors interactors, Client client, DesktopListener desktopListener) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsForm.fxml"));

        ClientsFormController ctrl = new ClientsFormController(client);

        fxmlLoader.setController(ctrl);
        
        ctrl.setInteractors(interactors);
        
        ctrl.setDesktopListener(desktopListener);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
           new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de la partie formulaire d'un client : " + e.toString()).show();
        }
        
    }
    
}
