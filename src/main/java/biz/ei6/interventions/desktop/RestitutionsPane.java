package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import biz.ei6.interventions.desktop.lib.domain.Site;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Eixa6
 */
public class RestitutionsPane extends AnchorPane {

    public RestitutionsPane(Interactors interactors) {
        
        ResourceBundle resources = ResourceBundle.getBundle("main");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("restitutionsPane.fxml"), resources);

        RestitutionsController ctrl = new RestitutionsController();

        fxmlLoader.setController(ctrl);
        ctrl.setInteractors(interactors);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resources.getString("exception.erreur"));
            // CHANGER
            alert.setHeaderText(resources.getString("exception.erreurChargementInterventionsPane"));
            
            alert.setContentText(e.toString());
            alert.show();
        }
    }
}
