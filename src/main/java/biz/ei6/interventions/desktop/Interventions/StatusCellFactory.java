package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Status;
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
public class StatusCellFactory implements Callback<ListView<Status>, ListCell<Status>> {

    @Override
    public ListCell<Status> call(ListView<Status> arg0) {
        return new StatusCell();
    }
    
        public class StatusCell extends ListCell<Status> {

        @Override
        protected void updateItem(Status status, boolean empty) {
            super.updateItem(status, empty);

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

            if (status == null || empty) {
                setGraphic(null);
            } else {

                StringBuilder statusString = new StringBuilder();

                // Si il a un prénom
                if (status.getName() != null) {
                    statusString.append(status.getName()).append(" ");
                }

                setText(statusString.toString());
            }
        }
    }
    
    
}
