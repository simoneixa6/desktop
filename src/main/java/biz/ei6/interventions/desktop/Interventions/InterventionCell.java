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

/**
 *
 * @author Eixa6
 */
public class InterventionCell  extends ListCell<Intervention> {
       
        @FXML
        Label lblIntervention;
        
        @FXML
        ImageView status;
        
        public InterventionCell() {
            loadFXML();
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
                
                lblIntervention.setText(intervention.getTitle() + " - " + intervention.getKm());
                
                Image redDot = new Image("file://"+getClass().getResource("red.png").getPath());
                Image yellowDot = new Image("file://"+getClass().getResource("yellow.png").getPath());
                Image orangeDot = new Image("file://"+getClass().getResource("orange.png").getPath());
                Image greenDot = new Image("file://"+getClass().getResource("green.png").getPath());
                
                        
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
