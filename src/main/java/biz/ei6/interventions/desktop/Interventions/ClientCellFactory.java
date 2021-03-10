package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Client;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
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

        @Override
        protected void updateItem(Client client, boolean empty) {
            super.updateItem(client, empty);

            // Ajout du style pour une cell
            Background mainGreyBackground = new Background(new BackgroundFill(Color.web("F2F2F2"), null, null));
            Background hoverGreyBackground = new Background(new BackgroundFill(Color.web("EAEAEA"), null, null));
            setBackground(mainGreyBackground);
            setTextFill(Color.BLACK);
            setOnMouseEntered(event -> {
                setBackground(hoverGreyBackground);
            });
            setOnMouseExited(event -> {
                setBackground(mainGreyBackground);
            });

            if (client == null || empty) {
                setGraphic(null);
            } else {

                StringBuilder clientString = new StringBuilder();

                // Si il a un prénom
                if (client.getName() != null) {
                    clientString.append(client.getName()).append(" ");
                }

                // Si il a un nom
                if (client.getLastname() != null) {
                    clientString.append(client.getLastname());
                }

                // Si il a une entreprise
                if (client.getCompany() != null) {
                    clientString.append(" (").append(client.getCompany()).append(")");
                }

                setText(clientString.toString());
            }
        }
    }
}
