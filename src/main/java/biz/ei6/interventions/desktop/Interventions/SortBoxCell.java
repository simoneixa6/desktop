package biz.ei6.interventions.desktop.interventions;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sortboxCell.fxml"));
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

            // Ajout du style pour une cell
            Background mainGreyBackground = new Background(new BackgroundFill(Color.web("F2F2F2"), null, null));
            Background hoverGreyBackground = new Background(new BackgroundFill(Color.web("EAEAEA"), null, null));
            setBackground(mainGreyBackground);
            lblStatus.setTextFill(Color.BLACK);
            setOnMouseEntered(event -> {
                setBackground(hoverGreyBackground);
            });
            setOnMouseExited(event -> {
                setBackground(mainGreyBackground);
            });

            // Affectation de la valeur
            lblStatus.setText(string);

            Image redDot = new Image("file://" + getClass().getResource("red.png").getPath());
            Image yellowDot = new Image("file://" + getClass().getResource("yellow.png").getPath());
            Image orangeDot = new Image("file://" + getClass().getResource("orange.png").getPath());
            Image greenDot = new Image("file://" + getClass().getResource("green.png").getPath());

            // Affectation de la couleur de pastille pour le type de status
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
