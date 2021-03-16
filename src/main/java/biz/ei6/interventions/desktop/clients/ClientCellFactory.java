package biz.ei6.interventions.desktop.clients;

import biz.ei6.interventions.desktop.lib.domain.Client;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author Eixa6
 */
public class ClientCellFactory implements Callback<ListView<Client>, ListCell<Client>> {

    @Override
    public ListCell<Client> call(ListView<Client> arg0) {
        return new ClientCell();
    }

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

            if (empty) {
                setText(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            } else {

                // Affiche le nom du client en rouge si il est problématique
                if (client.getProblematic()) {
                    lblName.setStyle("-fx-text-fill: #CD0000;");
                } else {
                    lblName.setStyle("-fx-text-fill: black;");
                }

                StringBuilder clientString = new StringBuilder();

                // Si il a prénom
                if (client.getName() != null) {
                    clientString.append(client.getName() + " ");
                }

                // Si il a nom
                if (client.getLastname() != null) {
                    clientString.append(client.getLastname());
                }

                // Si il a une entreprise
                if (client.getCompany() != null) {
                    if (client.getName() != null || client.getLastname() != null) {
                        clientString.append(" - " + client.getCompany());
                    } else {
                        clientString.append(client.getCompany());
                    }
                }

                lblName.setText(clientString.toString());
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

}
