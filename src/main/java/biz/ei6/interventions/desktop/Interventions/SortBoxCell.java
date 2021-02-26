package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * @author Eixa6
 */
public class SortBoxCell extends ListCell<String> {

    @FXML
    Label lblStatus;

    @FXML
    ImageView status;

    public SortBoxCell() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sortBoxCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(String string, boolean empty) {
        super.updateItem(string, empty);

        if (empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            lblStatus.setText(string);

            Image redDot = new Image("file://" + getClass().getResource("red.png").getPath());
            Image yellowDot = new Image("file://" + getClass().getResource("yellow.png").getPath());
            Image orangeDot = new Image("file://" + getClass().getResource("orange.png").getPath());
            Image greenDot = new Image("file://" + getClass().getResource("green.png").getPath());

            switch (string) {
                case "Ouverte":
                    status.setImage(redDot);
                    break;
                case "Terminée":
                    status.setImage(yellowDot);
                    break;
                case "Facturée":
                    status.setImage(orangeDot);
                    break;
                case "Réglée":
                    status.setImage(greenDot);
                    break;
            }
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

}
