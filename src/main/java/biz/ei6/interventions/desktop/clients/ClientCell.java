package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.lib.domain.Client;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

/**
 *
 * @author Eixa6
 */
public class ClientCell extends ListCell<Client> {

    @FXML
    Label lblName;
    
    public ClientCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

            @Override
        protected void updateItem(Client client, boolean empty) {
            super.updateItem(client, empty);

            if(empty) {
                setText(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
            else {
                
                lblName.setText(client.getName() + " " + client.getLastName());
                
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    
}
