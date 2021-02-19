/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.InterventionsListener;
import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Eixa6
 */
public class InterventionsForm extends AnchorPane {

    public InterventionsForm(Interactors interactors, Intervention intervention, InterventionsListener interventionsListener) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interventionsForm.fxml"));

        InterventionsFormController ctrl = new InterventionsFormController(intervention);

        fxmlLoader.setController(ctrl);
        
        ctrl.setInteractors(interactors);
        
        ctrl.setInterventionsListener(interventionsListener);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
