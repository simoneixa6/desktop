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

        ResourceBundle clientsBundle = ResourceBundle.getBundle("clients");
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsPane.fxml"), clientsBundle);

        ClientsController ctrl = new ClientsController();

        fxmlLoader.setController(ctrl);
        ctrl.setInteractors(interactors);

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(clientsBundle.getString("exception.erreur"));
            alert.setHeaderText(clientsBundle.getString("exception.erreurChargementClientsPane"));
            alert.setContentText(e.toString());
            alert.show();
        }
    }
}
