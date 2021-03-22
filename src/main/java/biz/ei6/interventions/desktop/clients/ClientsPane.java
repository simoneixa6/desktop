package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.App.Interactors;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;

/*
 * @author Eixa6
 */
public class ClientsPane extends SplitPane {

    public ClientsPane(Interactors interactors) {

        ResourceBundle resources = ResourceBundle.getBundle("main");
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsPane.fxml"), resources);

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
