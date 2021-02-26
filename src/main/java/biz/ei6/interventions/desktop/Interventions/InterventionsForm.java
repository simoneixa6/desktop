/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.DesktopListener;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Eixa6
 */
public class InterventionsForm extends AnchorPane {

    public InterventionsForm(Interactors interactors, Intervention intervention, DesktopListener desktopListener, ResourceBundle resources) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interventionsForm.fxml"), resources);

        InterventionsFormController ctrl = new InterventionsFormController(intervention);

        fxmlLoader.setController(ctrl);
        
        ctrl.setInteractors(interactors);
        
        ctrl.setDesktopListener(desktopListener);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
           new Alert(Alert.AlertType.ERROR, resources.getString("exception.ajoutFormulaireIntervention") + e.toString()).show();
        }
    }
}
