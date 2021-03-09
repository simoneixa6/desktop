package biz.ei6.interventions.desktop.interventions;

import java.io.IOException;
import java.io.InputStream;
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

            InputStream redImageStream = getClass().getResourceAsStream("red.png");
            InputStream yellowImageStream = getClass().getResourceAsStream("yellow.png");
            InputStream orangeImageStream = getClass().getResourceAsStream("orange.png");
            InputStream greenImageStream = getClass().getResourceAsStream("green.png");

            Image redDot = new Image(redImageStream);
            Image yellowDot = new Image(yellowImageStream);
            Image orangeDot = new Image(orangeImageStream);
            Image greenDot = new Image(greenImageStream);

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
