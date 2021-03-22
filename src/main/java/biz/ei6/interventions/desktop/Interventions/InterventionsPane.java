/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App.Interactors;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;

/**
 *
 * @author Eixa6
 */
public class InterventionsPane extends SplitPane {

    public InterventionsPane(Interactors interactors) {

        ResourceBundle resources = ResourceBundle.getBundle("main");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interventionsPane.fxml"), resources);

        InterventionsController ctrl = new InterventionsController();

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
