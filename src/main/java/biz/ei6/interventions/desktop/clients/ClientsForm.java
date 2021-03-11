/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.lib.domain.Client;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author Eixa6
 */
public class ClientsForm extends AnchorPane {

    public ClientsForm(Interactors interactors, Client client, DesktopListener desktopListener, ResourceBundle resources) {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsForm.fxml"), resources);

        ClientsFormController ctrl = new ClientsFormController(client);

        fxmlLoader.setController(ctrl);
        
        ctrl.setInteractors(interactors);
        
        ctrl.setDesktopListener(desktopListener);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
           new Alert(Alert.AlertType.ERROR, resources.getString("exception.ajoutFormulaireClient") + e.toString()).show();
        }
    }    
}
