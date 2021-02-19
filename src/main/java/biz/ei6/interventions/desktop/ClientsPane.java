/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;

/**
 *
 * @author Eixa6
 */
public class ClientsPane extends SplitPane {

    public ClientsPane(Interactors interactors) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsPane.fxml"));

        ClientsController ctrl = new ClientsController();

        fxmlLoader.setController(ctrl);
        ctrl.setInteractors(interactors);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
