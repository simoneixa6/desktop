package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author Eixa6
 */
public class InterventionCell  extends ListCell<Intervention> {
       
    @FXML
    Label lblIntervention;

    @FXML
    ImageView status;

    //Temporaire
    App.Interactors interactors;

    public InterventionCell(App.Interactors interactors) {
        loadFXML();
        this.interactors = interactors;
    }


     private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interventionCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void updateItem(Intervention intervention, boolean empty) {
        super.updateItem(intervention, empty);

        if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            StringBuilder interventionString = new StringBuilder();

            // Si il y a un titre d'intervention
            if ( intervention.getTitle() != null ){
                interventionString.append(intervention.getTitle() + " - ");
            }
            // Si il y a un prénom de client
            if ( intervention.getClient().getName() != null ){
                interventionString.append(intervention.getClient().getName() + " ");
            }
            // Si il y a un nom de client
            if ( intervention.getClient().getLastname() != null ){
                interventionString.append(intervention.getClient().getLastname() + " ");
            } 
            // Si il y a un nom d'entreprise pour ce client
            if (intervention.getClient().getCompany() != null) {
                if (intervention.getClient().getName() != null || intervention.getClient().getLastname() != null) {
                    interventionString.append(" (" + intervention.getClient().getCompany()+")");
                } else {
                    interventionString.append(intervention.getClient().getCompany());
                }
            }

            lblIntervention.setText(interventionString.toString());

            InputStream redImageStream = getClass().getResourceAsStream("red.png");
            InputStream yellowImageStream = getClass().getResourceAsStream("yellow.png");
            InputStream orangeImageStream = getClass().getResourceAsStream("orange.png");
            InputStream greenImageStream = getClass().getResourceAsStream("green.png");

            Image redDot = new Image(redImageStream);
            Image yellowDot = new Image(yellowImageStream);
            Image orangeDot = new Image(orangeImageStream);
            Image greenDot = new Image(greenImageStream);

                switch(intervention.getStatus()){
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
                    default:
                        throw new RuntimeException("L'intervention ne possède pas de status, méthode updateItem dans la classe InterventionCell");
                }          
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
